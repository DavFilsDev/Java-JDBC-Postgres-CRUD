package com.example.main;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.util.DatabaseConnection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        System.out.println("🚀 Starting Java JDBC PostgreSQL CRUD Application\n");

        try {
            // Test Connection
            DatabaseConnection.getConnection();

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
            allUsers.forEach(System.out::println);

            // 4. UPDATE - Update a user
            System.out.println("\n--- UPDATE User ---");
            if (user != null) {
                user.setName("Alice Brown");
                user.setAge(29);
                userDAO.updateUser(user);

                // Verify update
                User updatedUser = userDAO.getUserById(user.getId());
                System.out.println("After update: " + updatedUser);
            }

            // 5. DELETE - Delete a user
            System.out.println("\n--- DELETE User ---");
            userDAO.deleteUser(id2);

            // Show final list
            System.out.println("\n--- Final Users List ---");
            userDAO.getAllUsers().forEach(System.out::println);

        } finally {
            // Close connection
            DatabaseConnection.closeConnection();
        }

        System.out.println("\n✅ Application completed successfully!");
    }
}