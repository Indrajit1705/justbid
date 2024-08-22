package com.buysellplatform;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestDBConnection {

    public static void main(String[] args) {
        Properties props = new Properties();
        try (InputStream input = TestDBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                return;
            }

            // Load properties file
            props.load(input);

            // Get database connection details
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            String driver = props.getProperty("db.driver");

            // Load the database driver
            Class.forName(driver);

            // Establish the connection
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                if (connection != null) {
                    System.out.println("Connection to the database established successfully!");
                } else {
                    System.out.println("Failed to make connection!");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
