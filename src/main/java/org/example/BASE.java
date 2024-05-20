package org.example;
import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BASE {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/plans";
    private static final String USER = "root";
    private static final String PASSWORD = "0894974649Mars";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
