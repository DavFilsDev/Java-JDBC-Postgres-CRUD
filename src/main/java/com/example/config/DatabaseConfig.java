package com.example.config;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    public static String URL;
    public static String USER;
    public static String PASSWORD;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("database.properties")) {

            Properties props = new Properties();
            props.load(input);

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

        } catch (Exception e) {
            System.err.println("Erreur chargement config: " + e.getMessage());
        }
    }
}