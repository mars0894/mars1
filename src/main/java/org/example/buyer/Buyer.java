package org.example.buyer;

import org.example.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Buyer extends User {
    Scanner scanner = new Scanner(System.in);

    private List<Product> cart = new ArrayList<>();

    public Buyer(String username, String password) {
        super(username, password);
    }

    public void displayOptions() {
        int choice;
        do{
            System.out.println("1. View catalog\n2. Add product to cart\n3. Remove product from cart\n4. View cart\n5. Leave a review about the product\n6. Exit");
            choice = getChoice();
        }while (choice != 6);

    }
    public int getChoice(){
        int choice = 0 ;
        do {
            System.out.println("Please choose one of the options above");
            System.out.println("If you want to exit from system choose number 6 !");
            choice = scanner.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Here is all catalog of products!!!!!");
                    viewCatalog();
                    break;
                case 2:
                    System.out.println("Please provide product id that you want to add to cart:");
                    int productId  = scanner.nextInt();
                    scanner.nextLine();
                    addToCart(productId);
                    break;
                case 3:
                    System.out.println("Please provide product id that you want to delete from cart:");
                    int product_Id  = scanner.nextInt();
                    removeFromCart(product_Id);
                    break;
                case 4:
                    viewCart();
                    break;
                case 5:
                    leaveReview();
                    break;
                case 6:
                    System.out.println("Exiting.....");

            }
        }while(choice>0 && choice<6);
        return choice;
    }
    public void viewCatalog() {
        try (Connection conn = MyJDBC.getConnection(); // Get connection from MyJDBC class
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, description, price FROM products")) {

            if (!rs.next()) {
                System.out.println("The catalog is currently empty.");
            } else {
                System.out.println("Catalog of Products:");
                do {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                            ", Description: " + rs.getString("description") + ", Price: $" + rs.getDouble("price"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println("Error accessing the catalog: " + e.getMessage());
        }
    }

    public void addToCart(int productId) {
        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, name, description, price, quantity, master_id FROM products WHERE id = ?")) {
            stmt.setInt(1, productId); // Set the product ID in the query
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("master_id"));
                    cart.add(product);
                    System.out.println("Added to cart: " + product);
                } else {
                    System.out.println("Product not found with ID: " + productId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error when adding to cart: " + e.getMessage());
        }
    }


    public void removeFromCart(int productId) {
        Product foundProduct = null;
        for (Product product : cart) {
            if (product.getId() == productId) {
                foundProduct = product;
                break;
            }
        }

        if (foundProduct != null && cart.remove(foundProduct)) {
            System.out.println("Removed from cart: " + foundProduct);
        } else {
            System.out.println("Product not found in cart with ID: " + productId);
        }
    }


    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Cart contents:");
            for (Product product : cart) {
                System.out.println(product);
            }
        }
    }

    public void leaveReview() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Add products before you can leave a review.");
            return;
        }

        System.out.println("Enter the ID of the product you want to review:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left after reading the integer.
        Product foundProduct = null;

        for (Product product : cart) {
            if (product.getId() == productId) {
                foundProduct = product;
                break;
            }
        }

        if (foundProduct == null) {
            System.out.println("Product not found in your cart with ID: " + productId);
            return;
        }

        System.out.println("Enter your review for " + foundProduct.getName() + ":");
        String review = scanner.nextLine();
        foundProduct.addReview(review);
        System.out.println("Review added for " + foundProduct.getName() + ": " + review);
    }

}
