package com.really.good.sir.jpa.simulation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.sql.*;

import static com.really.good.sir.jpa.simulation.QueryService.convertToSQL;
import static com.really.good.sir.jpa.simulation.QueryService.getColumnType;

public class EntityManagerImpl {

    private final Connection connection;
    private final Configuration configuration;

    public EntityManagerImpl(final Configuration configuration) throws SQLException {
        this.configuration = configuration;
        String url = configuration.getPropertyValue("fluffy.connection.url");
        String user = configuration.getPropertyValue("fluffy.connection.username");
        String password = configuration.getPropertyValue("fluffy.connection.password");
        this.connection = DriverManager.getConnection(url, user, password);

        String ddl = configuration.getPropertyValue("fluffy.hbm2ddl.auto");
        if ("create".equalsIgnoreCase(ddl)) {
            generateSchema();
        }
    }

    public void persist(Object entity) {
        Class<?> clazz = entity.getClass();

        if (!clazz.isAnnotationPresent(Entity.class))
            throw new RuntimeException("\"" + clazz.getSimpleName() + "\" is not an actual entity");

        Field[] declaredFields = clazz.getDeclaredFields();

        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Field id = null;
        GenerationType idStrategy = null;
        String seqTable;
        Object idValue = null; // holds the table strategy id

        String tableName = clazz.getSimpleName().toLowerCase();
        if (clazz.isAnnotationPresent(Table.class)) {
            tableName = clazz.getAnnotation(Table.class).name();
        }

        for (Field field : declaredFields) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Id.class)) {
                id = field;

                if (field.isAnnotationPresent(GeneratedValue.class)) {
                    idStrategy = field.getAnnotation(GeneratedValue.class).strategy();
                    if (idStrategy == GenerationType.TABLE) {
                        seqTable = tableName + "_seq";

                        try (Statement st = connection.createStatement()) {
                            ResultSet rs = st.executeQuery("SELECT next_val FROM " + seqTable + " FOR UPDATE");
                            long nextId;
                            if (rs.next()) nextId = rs.getLong(1);
                            else throw new RuntimeException("Sequence table '" + seqTable + "' is empty");

                            st.executeUpdate("UPDATE " + seqTable + " SET next_val = next_val + 1");

                            idValue = nextId;
                            id.set(entity, nextId);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                continue;
            }

            if (field.isAnnotationPresent(Column.class)) {
                Column col = field.getAnnotation(Column.class);
                if (field.isAnnotationPresent(NotNull.class)) {
                    try {
                        Object v = field.get(entity);
                        if (v == null) throw new RuntimeException("Column '" + col.name() + "' cannot be null");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                names.append(col.name()).append(",");
                values.append("?").append(",");
            }
        }

        if (id != null && idStrategy == GenerationType.TABLE) {
            names.insert(0, id.getName() + ",");
            values.insert(0, "?,");
        }

        names.setLength(names.length() - 1);
        values.setLength(values.length() - 1);

        String sql = "INSERT INTO " + tableName + " (" + names + ") VALUES (" + values + ")";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int idx = 1;

            if (id != null && idStrategy == GenerationType.TABLE) {
                ps.setObject(idx++, idValue);
            }

            for (Field f : declaredFields) {
                if (f.isAnnotationPresent(Id.class)) continue;
                if (f.isAnnotationPresent(Column.class)) {
                    ps.setObject(idx++, f.get(entity));
                }
            }
            System.out.println(ps);
            ps.executeUpdate();

            if (id != null && (idStrategy != GenerationType.TABLE)) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Object rsId = rs.getObject(1);
                    id.set(entity, ((Number) rsId).longValue());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void close() throws SQLException {
        connection.close();
    }

    private void generateSchema() throws SQLException {
        for (Class<?> entity : configuration.getEntityClasses()) {
            if (!entity.isAnnotationPresent(javax.persistence.Entity.class)) continue;

            String tableName = entity.getSimpleName().toLowerCase();
            if (entity.isAnnotationPresent(Table.class)) {
                tableName = entity.getAnnotation(Table.class).name();
            }

            // Drop main table
            try (Statement st = connection.createStatement()) {
                String sql = "DROP TABLE IF EXISTS " + tableName;
                st.executeUpdate(sql);
                System.out.println(sql);
            }

            // Drop sequence table if TABLE strategy
            for (Field f : entity.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Id.class) && f.isAnnotationPresent(GeneratedValue.class)) {
                    GeneratedValue gv = f.getAnnotation(GeneratedValue.class);
                    if (gv.strategy() == GenerationType.TABLE) {
                        String seqTable = tableName + "_seq";
                        try (Statement stSeq = connection.createStatement()) {
                            String sqlSeq = "DROP TABLE IF EXISTS " + seqTable;
                            stSeq.executeUpdate(sqlSeq);
                            System.out.println(sqlSeq);
                        }
                    }
                }
            }

            StringBuilder sb = new StringBuilder("CREATE TABLE " + tableName + " (");

            for (Field f : entity.getDeclaredFields()) {
                f.setAccessible(true);

                boolean isId = f.isAnnotationPresent(Id.class);
                boolean isColumn = f.isAnnotationPresent(Column.class);

                if (isId) {
                    String colName = isColumn ? f.getAnnotation(Column.class).name() : f.getName();
                    String idDefinition = convertToSQL(f);

                    if (f.isAnnotationPresent(GeneratedValue.class)) {
                        GeneratedValue gv = f.getAnnotation(GeneratedValue.class);
                        GenerationType strategy = gv.strategy();

                        switch (strategy) {
                            case IDENTITY, AUTO -> idDefinition += " AUTO_INCREMENT";
                            case TABLE -> {
                                String seqTable = tableName + "_seq";
                                try (Statement stSeq = connection.createStatement()) {
                                    stSeq.executeUpdate(
                                            "CREATE TABLE IF NOT EXISTS " + seqTable + " (" +
                                                    "next_val BIGINT NOT NULL)"
                                    );
                                    stSeq.executeUpdate(
                                            "INSERT INTO " + seqTable + " (next_val) " +
                                                    "SELECT 1 WHERE NOT EXISTS (SELECT * FROM " + seqTable + ")"
                                    );
                                }
                            }
                        }
                    }

                    sb.append(colName)
                            .append(" ")
                            .append(idDefinition)
                            .append(" PRIMARY KEY,");
                    continue;
                }

                if (isColumn) {
                    Column c = f.getAnnotation(Column.class);

                    // Use new helper for type + length
                    String colType = getColumnType(f, c);

                    sb.append(c.name())
                            .append(" ")
                            .append(colType);

                    if (f.isAnnotationPresent(NotNull.class)) {
                        sb.append(" NOT NULL");
                    }

                    if (c.unique()) {
                        sb.append(" UNIQUE");
                    }

                    sb.append(",");
                }
            }

            sb.setLength(sb.length() - 1);
            sb.append(")");

            try (Statement st = connection.createStatement()) {
                System.out.println(sb);
                st.executeUpdate(sb.toString());
            }
        }
    }
}

