package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Get connection parameters from DatabaseConfig
                String url = com.example.config.DatabaseConfig.URL;
                String user = com.example.config.DatabaseConfig.USER;
                String password = com.example.config.DatabaseConfig.PASSWORD;

                System.out.println("🔗 Attempting to connect to: " + url);
                System.out.println("👤 Using user: " + user);

                // Establish connection
                // JDBC 4.0+ automatically loads the driver, no need for Class.forName()
                connection = DriverManager.getConnection(url, user, password);

                // Test the connection
                if (connection != null && !connection.isClosed()) {
                    System.out.println("✅ Database connected successfully!");
                    System.out.println("📊 Database: " + connection.getMetaData().getDatabaseProductName());
                    System.out.println("🔢 Version: " + connection.getMetaData().getDatabaseProductVersion());
                }

            } catch (SQLException e) {
                System.err.println("❌ Connection failed!");
                System.err.println("Error message: " + e.getMessage());
                System.err.println("Error code: " + e.getErrorCode());
                System.err.println("SQL state: " + e.getSQLState());

                // Provide helpful troubleshooting tips
                System.err.println("\n🔧 Troubleshooting tips:");
                System.err.println("1. Check if PostgreSQL is running: 'sudo service postgresql status'");
                System.err.println("2. Verify database 'javadb' exists");
                System.err.println("3. Check username/password in database.properties");
                System.err.println("4. Ensure user 'java_app_user' has permissions");
                System.err.println("5. Check if port 5432 is open");

                e.printStackTrace();
            }
        }
        return connection;
    }

    // Overloaded method for custom connection parameters
    public static Connection getConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("🔌 Connection closed.");
                }
            } catch (SQLException e) {
                System.err.println("❌ Error closing connection!");
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }

    // Test connection method
    public static boolean testConnection() {
        try (Connection testConn = getConnection()) {
            return testConn != null && !testConn.isClosed();
        } catch (SQLException e) {
            System.err.println("❌ Connection test failed: " + e.getMessage());
            return false;
        }
    }

    // Get connection info
    public static void printConnectionInfo() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("📊 Connection Information:");
                System.out.println("  Database: " + conn.getMetaData().getDatabaseProductName());
                System.out.println("  Version: " + conn.getMetaData().getDatabaseProductVersion());
                System.out.println("  Driver: " + conn.getMetaData().getDriverName());
                System.out.println("  URL: " + conn.getMetaData().getURL());
                System.out.println("  User: " + conn.getMetaData().getUserName());
                System.out.println("  Auto-commit: " + conn.getAutoCommit());
                System.out.println("  Read-only: " + conn.isReadOnly());
            }
        } catch (SQLException e) {
            System.err.println("❌ Cannot get connection info: " + e.getMessage());
        }
    }
}