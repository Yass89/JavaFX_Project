package com.nada.poo.database;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class DatabaseUtil {

    private static String MYSQL_URL;
    private static String USER;
    private static String PASSWORD;
    private static String PORT;

    static {
        loadDatabaseProperties();
    }

    private static void loadDatabaseProperties() {
        try (InputStream input = DatabaseUtil.class.getResourceAsStream("../dbconfig.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find dbconfig.properties");
                return;
            }

            prop.load(input);
            USER = prop.getProperty("db.user");
            PASSWORD = prop.getProperty("db.password");
            PORT = prop.getProperty("db.port");
            MYSQL_URL = "jdbc:mysql://localhost:"+PORT+"/?useSSL=false";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createNewDatabase(String dbName) {
        String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;

        try (Connection conn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Database created successfully: " + dbName);
            MYSQL_URL = "jdbc:mysql://localhost:" + PORT + "/" + dbName + "?useSSL=false";
        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
        }
    }

    public static void executeUpdate(String sql) {
        try (Connection conn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Operation executed successfully.");
        } catch (SQLException e) {
            System.out.println("Error executing update: " + e.getMessage());
        }
    }

    public static ResultSet executeQuery(String sql) {
        try (Connection conn = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            return null;
        }
    }
}