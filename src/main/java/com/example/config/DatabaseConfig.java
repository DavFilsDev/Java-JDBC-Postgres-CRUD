package com.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input != null) {
                properties.load(input);
                System.out.println("📁 Loaded database.properties file");
            } else {
                System.err.println("❌ database.properties file not found! Using default values.");
                setDefaultProperties();
            }

        } catch (IOException e) {
            System.err.println("❌ Error loading database.properties: " + e.getMessage());
            System.err.println("Using default values instead.");
            setDefaultProperties();
        }
    }

    private static void setDefaultProperties() {
        // Default values (for development)
        properties.setProperty("db.url", "jdbc:postgresql://localhost:5432/javadb");
        properties.setProperty("db.user", "java_app_user");
        properties.setProperty("db.password", "app_password");
        properties.setProperty("db.driver", "org.postgresql.Driver");

        System.out.println("⚠️  Using default configuration:");
        System.out.println("   URL: " + properties.getProperty("db.url"));
        System.out.println("   User: " + properties.getProperty("db.user"));
        System.out.println("   Password: " + (properties.getProperty("db.password").isEmpty() ? "[empty]" : "[set]"));
    }

    public static String URL = properties.getProperty("db.url");
    public static String USER = properties.getProperty("db.user");
    public static String PASSWORD = properties.getProperty("db.password");
    public static String DRIVER = properties.getProperty("db.driver");

    // Helper method to reload properties (useful for testing)
    public static void reloadProperties() {
        properties.clear();
        loadProperties();
        URL = properties.getProperty("db.url");
        USER = properties.getProperty("db.user");
        PASSWORD = properties.getProperty("db.password");
        DRIVER = properties.getProperty("db.driver");
    }

    // Get all properties as string for debugging
    public static String getConfigSummary() {
        return String.format(
                "Database Configuration:\n" +
                        "  URL: %s\n" +
                        "  User: %s\n" +
                        "  Password: %s\n" +
                        "  Driver: %s",
                URL, USER, PASSWORD.isEmpty() ? "[not set]" : "[set]", DRIVER
        );
    }
}