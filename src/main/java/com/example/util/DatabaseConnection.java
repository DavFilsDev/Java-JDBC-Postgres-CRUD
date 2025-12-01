package com.example.util;

import com.example.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load PostgreSQL JDBC Driver
                Class.forName("org.postgresql.Driver");

                // Establish connection
                connection = DriverManager.getConnection(
                        DatabaseConfig.URL,
                        DatabaseConfig.USER,
                        DatabaseConfig.PASSWORD
                );
                System.out.println("✅ Database connected successfully!");

            } catch (ClassNotFoundException e) {
                System.err.println("❌ PostgreSQL JDBC Driver not found!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("❌ Connection failed!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔌 Connection closed.");
                connection = null;
            } catch (SQLException e) {
                System.err.println("❌ Error closing connection!");
                e.printStackTrace();
            }
        }
    }
}