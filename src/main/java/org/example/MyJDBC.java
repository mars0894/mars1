package org.example;


import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MyJDBC {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/mars";
    private static final String USER = "root";
    private static final String PASSWORD = "0894974649Mars";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
