package com.example.dao;

import com.example.model.User;
import com.example.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // CREATE - Insert a new user
    public int createUser(User user) {
        String sql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getAge());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        System.out.println("✅ User created with ID: " + generatedId);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error creating user: " + e.getMessage());
        }
        return generatedId;
    }

    // READ - Get user by ID
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age")
                );
                System.out.println("✅ Found user: " + user.getName());
            } else {
                System.out.println("⚠️ No user found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error getting user: " + e.getMessage());
        }
        return user;
    }

    // READ - Get all users
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users ORDER BY id";
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age")
                );
                users.add(user);
            }
            System.out.println("✅ Retrieved " + users.size() + " users");

        } catch (SQLException e) {
            System.err.println("❌ Error getting all users: " + e.getMessage());
        }
        return users;
    }

    // UPDATE - Update user
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?";
        boolean updated = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getAge());
            pstmt.setInt(4, user.getId());

            int affectedRows = pstmt.executeUpdate();
            updated = affectedRows > 0;

            if (updated) {
                System.out.println("✅ User updated successfully!");
            } else {
                System.out.println("⚠️ No user found with ID: " + user.getId());
            }

        } catch (SQLException e) {
            System.err.println("❌ Error updating user: " + e.getMessage());
        }
        return updated;
    }

    // DELETE - Delete user by ID
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        boolean deleted = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            deleted = affectedRows > 0;

            if (deleted) {
                System.out.println("✅ User deleted successfully!");
            } else {
                System.out.println("⚠️ No user found with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error deleting user: " + e.getMessage());
        }
        return deleted;
    }
}