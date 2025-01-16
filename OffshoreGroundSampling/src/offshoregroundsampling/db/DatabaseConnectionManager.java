package offshoregroundsampling.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import offshoregroundsampling.constants.Constants;
import offshoregroundsampling.dao.SampleDAO;

public class DatabaseConnectionManager {

        private static DatabaseConnectionManager instance;
        private Connection connection;

        private DatabaseConnectionManager() {}

        public static synchronized DatabaseConnectionManager getInstance() {
            if (instance == null) {
                instance = new DatabaseConnectionManager();
                SampleDAO.initialize();
            }
            return instance;
        }

		public Connection getConnection() throws SQLException {
			if (connection == null || connection.isClosed()) {
				try {
					// Load the H2 driver
					Class.forName(Constants.H2_DB_DRIVER);
					// Establish the connection
					connection = DriverManager.getConnection(Constants.H2_DB_JDBC_URL, Constants.H2_DB_USER,
							Constants.H2_DB_PASSWORD);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
			return connection;
		}

        // Close the connection when the application shuts down
        public void closeConnection() {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    
}
