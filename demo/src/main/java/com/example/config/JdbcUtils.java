package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static final String URL      = "jdbc:mysql://localhost:3306/appdb";
    private static final String USER     = "root";
    private static final String PASSWORD = "abhijeet01"; 

    public static Connection getConnection() throws SQLException {
        try{
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Database connection successful.");
        return connection;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw e; 
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try { connection.close(); }
            catch (SQLException e) { System.err.println(e.getMessage()); 
                
            }
        }
    }
}