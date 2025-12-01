package com.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input == null) {
                System.err.println("❌ database.properties file not found!");
                // Fallback to hardcoded values
                properties.setProperty("db.url", "jdbc:postgresql://localhost:5432/javadb");
                properties.setProperty("db.user", "java_app_user");
                properties.setProperty("db.password", "app_password");
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback values
            properties.setProperty("db.url", "jdbc:postgresql://localhost:5432/javadb");
            properties.setProperty("db.user", "java_app_user");
            properties.setProperty("db.password", "app_password");
        }
    }

    public static String URL = properties.getProperty("db.url");
    public static String USER = properties.getProperty("db.user");
    public static String PASSWORD = properties.getProperty("db.password");
}