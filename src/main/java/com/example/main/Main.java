package com.example.main;

import com.example.config.DatabaseConfig;
import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.util.DatabaseConnection;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Starting Java JDBC PostgreSQL CRUD Application\n");

        // Show configuration
        System.out.println(DatabaseConfig.getConfigSummary() + "\n");

        // Test connection first
        System.out.println("🔧 Testing database connection...");
        if (!DatabaseConnection.testConnection()) {
            System.err.println("❌ Cannot proceed without database connection!");
            return;
        }

        DatabaseConnection.printConnectionInfo();

        UserDAO userDAO = new UserDAO();

        try {
            // 1. CREATE - Add new users
            System.out.println("\n--- CREATE Users ---");
            User newUser1 = new User("Alice Johnson", "alice@example.com", 28);
            int id1 = userDAO.createUser(newUser1);

            User newUser2 = new User("Bob Williams", "bob@example.com", 35);
            int id2 = userDAO.createUser(newUser2);

            // 2. READ - Get user by ID
            System.out.println("\n--- READ User by ID ---");
            User user = userDAO.getUserById(id1);
            if (user != null) {
                System.out.println("Retrieved: " + user);
            }

            // 3. READ ALL - Get all users
            System.out.println("\n--- READ All Users ---");
            List<User> allUsers = userDAO.getAllUsers();
            System.out.println("Total users in database: " + allUsers.size());
            allUsers.forEach(System.out::println);

            // 4. UPDATE - Update a user
            System.out.println("\n--- UPDATE User ---");
            if (user != null) {
                user.setName("Alice Brown");
                user.setAge(29);
                boolean updated = userDAO.updateUser(user);

                if (updated) {
                    // Verify update
                    User updatedUser = userDAO.getUserById(user.getId());
                    System.out.println("After update: " + updatedUser);
                }
            }

            // 5. DELETE - Delete a user
            System.out.println("\n--- DELETE User ---");
            boolean deleted = userDAO.deleteUser(id2);
            if (deleted) {
                System.out.println("User with ID " + id2 + " was deleted.");
            }

            // Show final list
            System.out.println("\n--- Final Users List ---");
            List<User> finalUsers = userDAO.getAllUsers();
            if (finalUsers.isEmpty()) {
                System.out.println("No users found in database.");
            } else {
                System.out.println("Remaining users: " + finalUsers.size());
                finalUsers.forEach(System.out::println);
            }

        } catch (Exception e) {
            System.err.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close connection
            DatabaseConnection.closeConnection();
        }

        System.out.println("\n✅ Application completed successfully!");
    }
}