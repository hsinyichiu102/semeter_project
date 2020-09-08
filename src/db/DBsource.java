package db;
import java.sql.Connection;
import java.sql.SQLException;

interface DBSource {
        public Connection getConnection() throws SQLException;
        public void closeConnection(Connection conn) throws SQLException;
    }

