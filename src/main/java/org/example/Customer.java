package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends User{
    private ArrayList<String> wishlist;
    public Customer(String username, String password) {
        super(username, password);
    }
    private final Scanner scanner = new Scanner(System.in);
    public void displayOptions() {
        int choice;

        do {
            System.out.println("1. Show all products" +
                    "\n2. Show how many products are avaliable " +
                    "\n3. Exit ");
            choice = getChoice(); // Get user choice and handle it in getChoice
        } while (choice != 3); // Loop until '4' is chosen
    }

    public int getChoice() {
        int choice = 0;
        do {
            System.out.println("Please choose one of the options:");
            System.out.println("If you want to exit from system choose number 3");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showAllProducts();
                    break;
                case 2:
                    System.out.println(countProducts());
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;

            }
        } while (choice <= 1 || choice > 4); // Keep asking until a valid choice is made
        return choice;
    }
    public int countProducts(){
        String sql = "SELECT COUNT(*) AS total FROM products";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt("total");
            }
            else{
                System.out.println("Error");
            }

        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
        return 0;

    }
    public void showAllProducts(){
        String sql = "SELECT * FROM products";
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                while(rs.next())System.out.println("Name: " + rs.getString("name") +"\nDescription: " + rs.getString("description") +"\nPrice: " + rs.getDouble("price") +"\nPeriod: " + rs.getString("period"));
            } else {
                System.out.println("Profile not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error checking profile: " + e.getMessage());
        }
    }
    public void addToWishList(String product){

    }
    public void deleteFromWishList(String product){

    }
}
