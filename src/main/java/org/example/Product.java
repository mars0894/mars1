package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String masterId;
    private List<String> reviews = new ArrayList<>();
    public Product(int id, String name, String description, double price, int quantity, String masterId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.masterId = masterId;
    }
    public void addReview(String review) {
        reviews.add(review);
    }

    public List<String> getReviews() {
        return new ArrayList<>(reviews);
    }

    public int getId() {
        return id;
    }

    public void updatePrice(double price) {
        this.price = price;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name ;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Description: " + description + ", Price: " + price +
                ", Quantity: " + quantity + ", Category: " ;
    }
}
