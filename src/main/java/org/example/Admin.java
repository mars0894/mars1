package org.example;

import java.security.spec.RSAOtherPrimeInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    private static final Scanner scanner = new Scanner(System.in);
    private ArrayList<Category> categories;

    public Admin(String username, String password) {
        super(username, password);
        categories = new ArrayList<>(); // Initialize your categories list
    }

//    public static void addCategoryProduct(Category category) {
//        String sql = "INSERT INTO Categories (name) VALUES (?)";
//        try (Connection conn = MyJDBC.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, category.getName());
//            int affectedRows = pstmt.executeUpdate();
//            if (affectedRows > 0) {
//                System.out.println("Category added successfully.\n\n");
//
//            } else {
//                System.out.println("Failed to add category.\n\n");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error while adding category: " + e.getMessage());
//        }
//    }

//    public static void deleteCategoryProduct(int categoryId) {
//        String sql = "DELETE FROM Categories WHERE id = ?";
//        try (Connection conn = MyJDBC.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, categoryId);
//            int affectedRows = pstmt.executeUpdate();
//            if (affectedRows > 0) {
//                System.out.println("Category deleted successfully.\n\n");
//            } else {
//                System.out.println("Failed to delete category.\n\n");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error while deleting category: " + e.getMessage());
//        }
//    }
    public static void registerMaster(String username, String password) {

        String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, 'master')";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Master added successfully.\n\n");
            } else {
                System.out.println("Failed to add master.\n\n");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding master: " + e.getMessage());
        }
    }

    public void displayOptions() {
        int choice;

        do {
            System.out.println("1. Register master\n2. Add product category\n3. Delete product category\n4.show all personal\n5.show plans\n6 Exit\7");
            choice = getChoice(); // Get user choice and handle it in getChoice
        } while (choice != 4); // Loop until '4' is chosen
    }

    public void showpersonal() {
        List<User> users = new ArrayList<>();
        users = updateUserList();

        for (User e : users) {
            e.show();
        }
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
        public int getChoice() {
            int choice = 0;
            do {
                System.out.println("Please choose one of the options:");
                System.out.println("If you want to exit from system choose number 4");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Please write username for new master");
                        String masterUsername = scanner.next();
                        System.out.println("Please write password for new master");
                        String masterPassword = scanner.next();
                        registerMaster(masterUsername, masterPassword);
                        break;
                    case 2:
                        System.out.println("Name of new category");
                        String categoryName = scanner.next();
//                        addCategoryProduct(new Category(categoryName)); // Assumes Category has a constructor that takes a name
                        break;
                    case 3:
                        System.out.println("ID of category to delete");
                        int categoryId = scanner.nextInt();
                        //deleteCategoryProduct(categoryId);
                        break;
                    case 4:
                        System.out.println("The program is closing....\nWe are looking forward to see you again there!!!!!");
                        break;
                    case 5:
                        System.out.println("show all users ");
                        showpersonal();
                        break;
                    case 6:
                        System.out.println("here u can see our plans");
                        Account.showplans();
                        break;
                    default:
                        System.out.println("Invalid option. Please choose again.");
                        break;

                }
            } while (choice <= 1 || choice > 4); // Keep asking until a valid choice is made
            return choice;
        }
    }

