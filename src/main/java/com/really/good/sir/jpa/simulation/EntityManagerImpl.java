package com.really.good.sir.jpa.simulation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

import static com.really.good.sir.jpa.simulation.QueryService.convertToSQL;
import static com.really.good.sir.jpa.simulation.QueryService.getColumnType;

public class EntityManagerImpl {

    private final Connection connection;
    private final Configuration configuration;

    // ---- Persistence Context ----
    private final Map<EntityKey, Object> persistenceContext = new HashMap<>();
    private final Map<EntityKey, Snapshot> snapshots = new HashMap<>();

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

    // ----------------------------------------------
    // find with Persistence Context caching
    // ----------------------------------------------
    public <T> T find(Class<T> entityClass, Object idValue) {
        if (!entityClass.isAnnotationPresent(Entity.class))
            throw new RuntimeException("Not an entity: " + entityClass.getSimpleName());

        if (idValue == null)
            return null;

        EntityKey key = new EntityKey(entityClass, idValue);

        // 1) Persistence context lookup
        Object pcEntity = persistenceContext.get(key);
        if (pcEntity != null)
            return (T) pcEntity;

        // 2) DB load
        Field idField = getIdField(entityClass);
        String tableName = getTableName(entityClass);
        String idColumn = getIdColumn(idField);

        String sql = "SELECT * FROM " + tableName + " WHERE " + idColumn + " = ? LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, idValue);
            System.out.println("\n" + ps);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            T entity = entityClass.getDeclaredConstructor().newInstance();
            for (Field f : entityClass.getDeclaredFields()) {
                f.setAccessible(true);

                if (f.isAnnotationPresent(Column.class)) {
                    String colName = f.getAnnotation(Column.class).name();
                    f.set(entity, rs.getObject(colName));
                } else if (f.isAnnotationPresent(Id.class)) {
                    f.set(entity, rs.getObject(idColumn));
                }
            }

            // Register managed
            persistenceContext.put(key, entity);
            snapshots.put(key, Snapshot.of(entity));

            return entity;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------
    // persist with managed state registration
    // ----------------------------------------------
    public void persist(Object entity) {
        Class<?> clazz = entity.getClass();

        if (!clazz.isAnnotationPresent(Entity.class))
            throw new RuntimeException("\"" + clazz.getSimpleName() + "\" is not an actual entity");

        Field idField = getIdField(clazz);
        idField.setAccessible(true);

        Field[] declaredFields = clazz.getDeclaredFields();

        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();
        GenerationType idStrategy = null;
        Object idValue = null;

        String tableName = getTableName(clazz);

        // Extract fields
        for (Field field : declaredFields) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Id.class)) {
                if (field.isAnnotationPresent(GeneratedValue.class)) {
                    idStrategy = field.getAnnotation(GeneratedValue.class).strategy();
                    if (idStrategy == GenerationType.TABLE) {
                        idValue = generateTableId(tableName, field);
                        try {
                            idField.set(entity, idValue);
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
                        if (field.get(entity) == null)
                            throw new RuntimeException("Column '" + col.name() + "' cannot be null");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                names.append(col.name()).append(",");
                values.append("?").append(",");
            }
        }

        if (idStrategy == GenerationType.TABLE) {
            names.insert(0, idField.getName() + ",");
            values.insert(0, "?,");
        }

        names.setLength(names.length() - 1);
        values.setLength(values.length() - 1);

        String sql = "INSERT INTO " + tableName + " (" + names + ") VALUES (" + values + ")";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int idx = 1;

            if (idStrategy == GenerationType.TABLE) {
                ps.setObject(idx++, idValue);
            }

            for (Field f : declaredFields) {
                if (f.isAnnotationPresent(Id.class)) continue;
                if (f.isAnnotationPresent(Column.class)) {
                    ps.setObject(idx++, f.get(entity));
                }
            }

            System.out.println("\n" + ps);
            ps.executeUpdate();

            if (idStrategy != GenerationType.TABLE) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Object generated = rs.getObject(1);
                    idField.set(entity, ((Number) generated).longValue());
                }
            }

            Object finalId = idField.get(entity);
            EntityKey key = new EntityKey(clazz, finalId);
            persistenceContext.put(key, entity);
            snapshots.put(key, Snapshot.of(entity));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------
    // merge with managed copy semantics
    // ----------------------------------------------
    public <T> T merge(T entity) {
        Class<?> clazz = entity.getClass();
        Field idField = getIdField(clazz);
        idField.setAccessible(true);

        Object idValue;
        try {
            idValue = idField.get(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // If detached transient
        if (idValue == null) {
            persist(entity);
            return entity;
        }

        EntityKey key = new EntityKey(clazz, idValue);

        // If managed already
        Object managed = persistenceContext.get(key);
        if (managed != null) {
            copyState(entity, managed);
            return (T) managed;
        }

        // If exists in DB -> load, copy, return managed
        Object existing = find(clazz, idValue);
        if (existing != null) {
            copyState(entity, existing);
            return (T) existing;
        }

        // If not existing -> insert + load
        insertWithId(entity);
        Object loaded = find(clazz, idValue);
        return (T) loaded;
    }

    // ----------------------------------------------
    // remove from PC + DB
    // ----------------------------------------------
    public void remove(Object entity) {
        Class<?> clazz = entity.getClass();
        Field idField = getIdField(clazz);
        idField.setAccessible(true);

        Object idValue;
        try {
            idValue = idField.get(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (idValue == null)
            throw new RuntimeException("Cannot remove entity without ID");

        String table = getTableName(clazz);
        String idColumn = getIdColumn(idField);
        String sql = "DELETE FROM " + table + " WHERE " + idColumn + " = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, idValue);
            System.out.println("\n" + ps);
            ps.executeUpdate();

            EntityKey key = new EntityKey(clazz, idValue);
            persistenceContext.remove(key);
            snapshots.remove(key);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------
    // flush: dirty checking
    // ----------------------------------------------
    public void flush() {
        for (Map.Entry<EntityKey, Object> entry : persistenceContext.entrySet()) {
            EntityKey key = entry.getKey();
            Object entity = entry.getValue();
            Snapshot oldSnap = snapshots.get(key);
            Snapshot newSnap = Snapshot.of(entity);

            if (!oldSnap.equals(newSnap)) {
                updateDirty(entity, key.id, oldSnap, newSnap);
                snapshots.put(key, newSnap);
            }
        }
    }

    private void updateDirty(Object entity, Object idValue, Snapshot oldSnap, Snapshot newSnap) {
        Class<?> clazz = entity.getClass();
        String table = getTableName(clazz);

        Field idField = getIdField(clazz);
        String idColumn = getIdColumn(idField);

        StringBuilder sb = new StringBuilder("UPDATE " + table + " SET ");

        List<Field> cols = new ArrayList<>();
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Column.class)) {
                cols.add(f);
            }
        }

        for (Field f : cols) {
            Column c = f.getAnnotation(Column.class);
            sb.append(c.name()).append(" = ?,");
        }

        sb.setLength(sb.length() - 1);
        sb.append(" WHERE ").append(idColumn).append(" = ?");

        try (PreparedStatement ps = connection.prepareStatement(sb.toString())) {
            int idx = 1;
            for (Field f : cols) {
                f.setAccessible(true);
                ps.setObject(idx++, f.get(entity));
            }
            ps.setObject(idx, idValue);

            System.out.println("\n" + ps);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws SQLException {
        flush();
        connection.close();
    }

    // ----------------------------------------------
    // Helper: Id
    // ----------------------------------------------
    private Field getIdField(Class<?> clazz) {
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                return f;
            }
        }
        throw new RuntimeException("No @Id on entity " + clazz.getSimpleName());
    }

    private String getIdColumn(Field idField) {
        return idField.isAnnotationPresent(Column.class)
                ? idField.getAnnotation(Column.class).name()
                : idField.getName();
    }

    private String getTableName(Class<?> clazz) {
        return clazz.isAnnotationPresent(Table.class)
                ? clazz.getAnnotation(Table.class).name()
                : clazz.getSimpleName().toLowerCase();
    }

    // ----------------------------------------------
    // Sequence Table strategy support
    // ----------------------------------------------
    private Object generateTableId(String tableName, Field idField) {
        String seqTable = tableName + "_seq";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT next_val FROM " + seqTable + " FOR UPDATE");
            long nextId;
            if (rs.next()) nextId = rs.getLong(1);
            else throw new RuntimeException("Sequence table empty");
            st.executeUpdate("UPDATE " + seqTable + " SET next_val = next_val + 1");
            return nextId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------
    // Insert with fixed ID
    // ----------------------------------------------
    private void insertWithId(Object entity) {
        try {
            Class<?> clazz = entity.getClass();
            String table = getTableName(clazz);
            Field[] fields = clazz.getDeclaredFields();

            StringBuilder names = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field f : fields) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Id.class) || f.isAnnotationPresent(Column.class)) {
                    String col = f.isAnnotationPresent(Column.class)
                            ? f.getAnnotation(Column.class).name()
                            : f.getName();
                    names.append(col).append(",");
                    values.append("?,");
                }
            }

            names.setLength(names.length() - 1);
            values.setLength(values.length() - 1);

            String sql = "INSERT INTO " + table + " (" + names + ") VALUES (" + values + ")";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                int idx = 1;
                for (Field f : fields) {
                    f.setAccessible(true);
                    ps.setObject(idx++, f.get(entity));
                }
                System.out.println("\n" + ps);
                ps.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------
    // Copy state for merge
    // ----------------------------------------------
    private void copyState(Object from, Object to) {
        Class<?> clazz = from.getClass();
        for (Field f : clazz.getDeclaredFields()) {
            if (!f.isAnnotationPresent(Column.class)) continue;
            try {
                f.setAccessible(true);
                Object v = f.get(from);
                f.set(to, v);
            } catch (Exception ignored) {
            }
        }
    }

    // ----------------------------------------------
    // generateSchema unchanged (except formatting)
    // ----------------------------------------------
    private void generateSchema() throws SQLException {
        for (Class<?> entity : configuration.getEntityClasses()) {
            if (!entity.isAnnotationPresent(Entity.class)) continue;

            String tableName = getTableName(entity);

            try (Statement st = connection.createStatement()) {
                String sql = "DROP TABLE IF EXISTS " + tableName;
                st.executeUpdate(sql);
                System.out.println("\n" + sql);
            }

            for (Field f : entity.getDeclaredFields()) {
                if (f.isAnnotationPresent(Id.class) && f.isAnnotationPresent(GeneratedValue.class)) {
                    GeneratedValue gv = f.getAnnotation(GeneratedValue.class);
                    if (gv.strategy() == GenerationType.TABLE) {
                        String seq = tableName + "_seq";
                        try (Statement stSeq = connection.createStatement()) {
                            stSeq.executeUpdate("DROP TABLE IF EXISTS " + seq);
                            System.out.println("\nDROP TABLE IF EXISTS " + seq);
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
                    String col = f.isAnnotationPresent(Column.class)
                            ? f.getAnnotation(Column.class).name()
                            : f.getName();
                    String type = convertToSQL(f);

                    if (f.isAnnotationPresent(GeneratedValue.class)) {
                        GeneratedValue gv = f.getAnnotation(GeneratedValue.class);
                        if (gv.strategy() == GenerationType.IDENTITY ||
                                gv.strategy() == GenerationType.AUTO) {
                            type += " AUTO_INCREMENT";
                        } else if (gv.strategy() == GenerationType.TABLE) {
                            String seq = tableName + "_seq";
                            try (Statement stSeq = connection.createStatement()) {
                                stSeq.executeUpdate(
                                        "CREATE TABLE IF NOT EXISTS " + seq + " (next_val BIGINT NOT NULL)");
                                stSeq.executeUpdate(
                                        "INSERT INTO " + seq + " (next_val) SELECT 1 WHERE NOT EXISTS (SELECT * FROM " + seq + ")");
                            }
                        }
                    }

                    sb.append(col).append(" ").append(type).append(" PRIMARY KEY,");
                    continue;
                }

                if (isColumn) {
                    Column c = f.getAnnotation(Column.class);
                    String colType = getColumnType(f, c);

                    sb.append(c.name()).append(" ").append(colType);
                    if (f.isAnnotationPresent(NotNull.class)) sb.append(" NOT NULL");
                    if (c.unique()) sb.append(" UNIQUE");
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

    // ----------------------------------------------
    // EntityKey + Snapshot helpers
    // ----------------------------------------------
    private record EntityKey(Class<?> entityClass, Object id) {}

    private static class Snapshot {
        private final Map<String, Object> values = new HashMap<>();

        static Snapshot of(Object entity) {
            Snapshot s = new Snapshot();
            for (Field f : entity.getClass().getDeclaredFields()) {
                if (!f.isAnnotationPresent(Column.class)) continue;
                try {
                    f.setAccessible(true);
                    s.values.put(f.getName(), f.get(entity));
                } catch (Exception ignored) {}
            }
            return s;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Snapshot other)) return false;
            return Objects.equals(this.values, other.values);
        }
    }
}
