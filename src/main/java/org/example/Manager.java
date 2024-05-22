package org.example;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;


public class Manager extends User {
    private static final Scanner scanner = new Scanner(System.in);

    public Manager(String username, String password) {
        super(username, password);
    }

    public void displayOptions() {
        int choice;

        do {
            System.out.println("1. Update product" +
                    "\n2. Add product " +
                    "\n3. " + "Delete product " +
                    "\n4." + "Show user by Id" +
                    "\n5." + "Show product by Id" +
                    "\n6 Add manager(only for overseers)" +
                    "\n7Exit");
            choice = getChoice(); // Get user choice and handle it in getChoice
        } while (choice != 7); // Loop until '4' is chosen
    }

    public int getChoice() {
        int choice = 0;
        do {
            System.out.println("Please choose one of the options:");
            System.out.println("If you want to exit from system choose number 7");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Please write new name for product");
                    String name = scanner.next();
                    System.out.println("new Price: ");
                    double price = scanner.nextDouble();
//                    price.checkInt();
                    break;
                case 2:
                    System.out.println("Name of new building");
                    String productName = scanner.next();
                    System.out.println("Enter description");
                    String productDescription = scanner.next();
                    System.out.println("Enter price");
                    double productPrice = scanner.nextDouble();
                    System.out.println("Enter period");
                    String productPeriod = scanner.next();
                    addProduct(productName, productDescription, productPrice, productPeriod);
                    break;
                case 3:
                    System.out.println("ID of product to delete");
                    int productID = scanner.nextInt();
                    deleteProduct(productID);
                    break;
                case 4:
                    System.out.println("Enter user id ");
                    int userId = scanner.nextInt();
                    showUser(userId);
                    break;
                case 5:
                    System.out.println("Enter product id ");
                    int productId = scanner.nextInt();
                    showProduct(productId);
                    break;
                case 6:
                    System.out.println("Enter your name: ");
                    String managerName = scanner.next();
                    System.out.println("Enter your password: ");
                    String managerPassword = scanner.next();
                    if (!isOverseer(managerName, managerPassword)) {
                        System.out.println("No rights for that");
                        break;
                    }
                    System.out.println("Name of new manager");
                    String username = scanner.next();
                    System.out.println("Enter password");
                    String password = scanner.next();
                    addManager(username, password);
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;

            }
        } while (choice <= 1 || choice > 7); // Keep asking until a valid choice is made
        return choice;
    }

    private boolean isOverseer(String name, String password) {
        String sql = "SELECT isOverseer FROM users WHERE username = ? AND password = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isOverseer");
            } else {
                System.out.println("Profile not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error checking profile: " + e.getMessage());
        }
        return false;
    }

    private void addManager(String name, String password) {
        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password,role) VALUES ( ?, ?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "manager");

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    public void updateProduct(String newName, double newPrice, String newPeriod, int id) {
        String sql = "UPDATE products SET name = ?, price = ? , period = ? WHERE id = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(3, newPeriod);
            pstmt.setDouble(2, newPrice);
            pstmt.setInt(4, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Updated successfully.\n");
            } else {
                System.out.println("Failed to update \n");
            }
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    public static void addProduct(String name, String description, double price, String period) {

        String sql = "INSERT INTO products ( name, description, price, period) VALUES (?, ?, ?,?)";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.setString(4, period);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(" added successfully.\n\n");
            } else {
                System.out.println("Failed to add .\n\n");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding : " + e.getMessage());
        }
    }

    public static void deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Product deleted successfully.\n\n");
            } else {
                System.out.println("Failed to delete .\n\n");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting : " + e.getMessage());
        }
    }

    public void showUser(int userId) {
        String sql = "SELECT username, password FROM users WHERE id = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
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


    private int showProduct(int productId) {
        String sql = "SELECT name, price, period FROM products WHERE id = ?";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
//

            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String period = rs.getString("period");
                System.out.println("Name: " + name + " Price: " + price + " Period: " + period);
                return 0;
            } else {
                System.out.println("User not found.");
                return -1; // User not found
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving ID: " + e.getMessage());
            return -1; // Error case
        }
    }

}

