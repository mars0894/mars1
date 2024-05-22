package org.example;

import java.sql.*;
import java.util.Scanner;

public class Account {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("Choose an option:");
            int choice = scanner.nextInt();
            //scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the system.");
                    running = false; // Set running to false to exit the loop and end the program
                    break;
                default:
                    System.out.println("Invalid option selected. Please choose again.");
                    break;
            }
        }
        scanner.close();
    }
    private static void register(Scanner scanner) {
        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password,role) VALUES ( ?, ?,?)")) {
            System.out.println("Enter username:");
            String username = scanner.next();
            System.out.println("Enter password:");
            String password = scanner.next();
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "customer");

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
    private static void login(Scanner scanner) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MyJDBC.getConnection();
            System.out.println("Enter username:");
            String username = scanner.next();
            System.out.println("Enter password:");
            String password = scanner.next();

            String query = "SELECT username, password, role FROM users WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");
                System.out.println("Login successful!");
                switch (role) {
                    case "manager":
                        Manager manager = new Manager(username, password);
                        manager.displayOptions();
                        break;
                    case "customer":
                        Customer customer = new Customer(username, password);
                        customer.displayOptions();
                        break;
                    default:
                        System.out.println("No role-specific options available.");
                        break;
                }
            } else {
                System.out.println("Invalid username or password. Try again.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
        }
    }
}
