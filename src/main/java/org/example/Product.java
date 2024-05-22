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
    private String period;
    public Product(int id, String name, String description, double price,String period) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.period = period;

    }

    public int getId() {
        return id;
    }

    public void updatePrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name ;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Description: " + description + ", Price: " + price
                 + ", Category: " + "Period: " + period;
    }
}
