package com.example.main;

import com.example.dao.UserDAO;
import com.example.model.User;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Démarrage application JDBC\n");

        UserDAO userDAO = new UserDAO();

        // 1. CREATE
        System.out.println("--- CREATE ---");
        User user1 = new User("Alice", "alice@email.com", 25);
        userDAO.createUser(user1);

        User user2 = new User("Bob", "bob@email.com", 30);
        userDAO.createUser(user2);

        // 2. READ ALL
        System.out.println("\n--- READ ALL ---");
        List<User> users = userDAO.getAllUsers();
        users.forEach(System.out::println);

        // 3. READ BY ID
        System.out.println("\n--- READ BY ID ---");
        if (!users.isEmpty()) {
            User foundUser = userDAO.getUserById(users.get(0).getId());
            System.out.println("Trouvé: " + foundUser);
        }

        // 4. UPDATE
        System.out.println("\n--- UPDATE ---");
        if (!users.isEmpty()) {
            User toUpdate = users.get(0);
            toUpdate.setName("Alice Modifiée");
            toUpdate.setAge(26);
            userDAO.updateUser(toUpdate);
        }

        // 5. DELETE
        System.out.println("\n--- DELETE ---");
        if (!users.isEmpty() && users.size() > 1) {
            userDAO.deleteUser(users.get(1).getId());
        }

        // 6. FINAL RESULT
        System.out.println("\n--- RESULTAT FINAL ---");
        userDAO.getAllUsers().forEach(System.out::println);

        System.out.println("\n✅ Application terminée!");
    }
}