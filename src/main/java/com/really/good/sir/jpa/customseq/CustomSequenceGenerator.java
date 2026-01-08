package com.really.good.sir.jpa.customseq;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.*;

public class CustomSequenceGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Connection connection = null;
        try {
            connection = session.getJdbcConnectionAccess().obtainConnection();

            // Step 1: select current value with lock
            PreparedStatement ps1 = connection.prepareStatement(
                    "SELECT next_val FROM sequence_table WHERE seq_name = 'user_seq' FOR UPDATE"
            );
            ResultSet rs = ps1.executeQuery();
            if (!rs.next()) {
                throw new RuntimeException("sequence not initialized");
            }

            long current = rs.getLong(1);
            long next = current + 1;

            // Step 2: update sequence table
            PreparedStatement ps2 = connection.prepareStatement(
                    "UPDATE sequence_table SET next_val = ? WHERE seq_name = 'user_seq'"
            );
            ps2.setLong(1, next);
            ps2.executeUpdate();

            return current;

        } catch (SQLException e) {
            throw new RuntimeException("Error generating custom sequence", e);

        } finally {
            try {
                if (connection != null) {
                    session.getJdbcConnectionAccess().releaseConnection(connection);
                }
            } catch (SQLException ignore) {}
        }
    }
}
