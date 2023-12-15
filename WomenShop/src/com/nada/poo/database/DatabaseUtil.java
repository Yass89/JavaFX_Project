package com.nada.poo.database;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class DatabaseUtil {

    private static String MYSQL_URL;
    private static String USER;
    private static String PASSWORD;
    private static String PORT;
    private static String DBNAME;

    private static Connection connection;

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
            DBNAME = prop.getProperty("db.name");
            MYSQL_URL = "jdbc:mysql://localhost:"+PORT+"/"+DBNAME+"?useSSL=false";
            System.out.println(MYSQL_URL);
            connection = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createNewDatabase() {
        String sql = "CREATE DATABASE IF NOT EXISTS " + DBNAME;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Database created successfully: " + DBNAME);
        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
        }
    }

    // get coonection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(MYSQL_URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void executeUpdate(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            pstmt.executeUpdate();
            System.out.println("Update executed successfully: " + sql);
        } catch (SQLException e) {
            System.out.println("Error executing update: " + e.getMessage());
        }
    }

    public static ResultSet executeQuery(String sql) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            return null;
        }
    }
}