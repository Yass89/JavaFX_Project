package com.nada.poo.database;

public class DatabaseSetup {


    public static void databaseCreation() {
        // Create Database
        DatabaseUtil.createNewDatabase();

        // Create tables
        createCategoriesTable();
        createProductsTable();
        createFinancialsTable();
    }

    private static void createCategoriesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS categories (" +
                "category_id INT PRIMARY KEY, " +
                "category_name VARCHAR(255) NOT NULL)";
        DatabaseUtil.executeUpdate(sql);
    }

    private static void createProductsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "product_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "price DECIMAL(10, 2) NOT NULL, " +
                "stock INT NOT NULL, " +
                "size INT,"+
                "category_id INT, " +
                "discount VARCHAR(255) NOT NULL,"+
                "FOREIGN KEY (category_id) REFERENCES categories(category_id))";
        DatabaseUtil.executeUpdate(sql);
    }


    private static void createFinancialsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS financials (" +
                "financial_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "capital DECIMAL(10, 2), " +
                "total_income DECIMAL(10, 2), " +
                "total_cost DECIMAL(10, 2))";
        DatabaseUtil.executeUpdate(sql);
    }
}
