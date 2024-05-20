package org.example;

import org.example.buyer.Buyer;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

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
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);
    private static void register(Scanner scanner) {
        try (Connection connection = MyJDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (id, username, password,role) VALUES (?,?, ?, ?)")) {
            System.out.println("Enter username:");
            String username = scanner.next();
            System.out.println("Enter password:");
            String password = scanner.next();
            System.out.println("Enter role (director/managee/worker):");
            String role = scanner.next();
            int id = ID_GENERATOR.getAndIncrement();
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, role);

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
    private static final String plans="";
    public static void showplans(){
        System.out.println(plans);
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
                    case "director":
                        Admin admin = new Admin(username, password);
                        admin.displayOptions();
                        break;
                    case "manager":
                        Master master = new Master(username, password);
                        master.displayOptions();
                        break;
                    case "worker":
                        Buyer buyer = new Buyer(username, password);
                        buyer.displayOptions();
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
