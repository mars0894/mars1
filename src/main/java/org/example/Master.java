package org.example;

import java.security.spec.RSAOtherPrimeInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Master extends User {
    Scanner scanner = new Scanner(System.in);



    public Master(String username, String password) {
        super(username, password);
    }

    // Add Product to the Database
    public void addPersona(String name, String description, double price, int categoryId) {
        String sql = "INSERT INTO plans (buildings,workingtime,) VALUES (?, ?)";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, getUserIdByUsername(username));// Assuming master_id is the user's ID
            pstmt.setInt(5, categoryId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Product added successfully.\n");
            } else {
                System.out.println("Failed to add product\n");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding product: " + e.getMessage());
        }
    }

    // Update Product Quantity
//    public void updateProductQuantity(int productId, int newQuantity) {
//        String sql = "UPDATE Products SET quantity = ? WHERE id = ?";
//        try (Connection conn = MyJDBC.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, newQuantity);
//            pstmt.setInt(2, productId);
//            int affectedRows = pstmt.executeUpdate();
//            if (affectedRows > 0) {
//                System.out.println("Product quantity updated successfully.\n");
//            } else {
//                System.out.println("Failed to update product quantity\n");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error while updating product quantity: " + e.getMessage());
//        }
//    }
    // Retrieve User ID from Username
    public int getUserIdByUsername(String username) {
        String sql = "SELECT id FROM Users WHERE username = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idadmins");
            } else {
                System.out.println("User not found.");
                return -1; // User not found
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user ID: " + e.getMessage());
            return -1; // Error case
        }
    }


    // Check Profile Information
    public void checkProfile() {
        String sql = "SELECT username, password FROM Users WHERE username = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, getUserIdByUsername(username));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("password");
                System.out.println("Username: " + username + ", Password: " + email);
            } else {
                System.out.println("Profile not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error checking profile: " + e.getMessage());
        }
    }

    // Update User Information
    public void updateProfile(String newUsername, String newPassword) {
        String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, 'master')";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, newPassword);
            pstmt.setInt(3, getUserIdByUsername(username));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Profile updated successfully.\n");
            } else {
                System.out.println("Failed to update profile\n");
            }
        } catch (SQLException e) {
            System.out.println("Error updating profile: " + e.getMessage());
        }
    }

    public void displayOptions() {
        int choice;
        do{
            System.out.println("Greeting you dear Master!!!\n");
            System.out.println("1. show personal\n2. show plans \n3. Check profile\n4. Edit profile\n5. Exit");
            choice = getChoice();
        }while(choice !=5 & choice<5 &&choice>0);

    }

    public static List<User> updateUserList() {
        List<User> userList = new ArrayList<>();
        try {
            Connection connection = MyJDBC.getConnection();
            String query = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User(username, password);
                userList.add(user);
            }
            return userList;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void showpersonal() {
        List<User> users = new ArrayList<>();
        users = updateUserList();

        for (User e : users) {
            e.show();
        }
    }
    public int getChoice() {
        int choice = 0;
        do {
            System.out.println("Please choose one of the menu numbers to work with the program, if you are finished, then choose number 5!");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Please enter a number.");
                scanner.next(); // this is important!
            }
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("show all users ");
                    showpersonal();
                    break;
                case 2:
                    System.out.println();
                    break;
                case 3:
                    checkProfile();
                    break;
                case 4:
                    System.out.println("Please write new username:");
                    String newUsername = scanner.nextLine(); // Changed to nextLine()
                    System.out.println("Please write new password:");
                    String newPassword = scanner.nextLine(); // Changed to nextLine()
                    updateProfile(newUsername, newPassword);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please choose again.");
            }
        } while (choice < 1 || choice > 5);
        return choice;
    }
}
