package com.nada.poo.database;

public class DatabaseSetup {

    private static final String DATABASE_NAME = "WomenShop";

    public static void databaseCreation() {
        // Create Database
        DatabaseUtil.createNewDatabase(DATABASE_NAME);

        // Create tables
        createCategoriesTable();
        createProductsTable();
        createTransactionsTable();
        createFinancialsTable();
    }

    private static void createCategoriesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS categories (" +
                "category_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "category_name VARCHAR(255) NOT NULL)";
        DatabaseUtil.executeUpdate(sql);
    }

    private static void createProductsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "product_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "price DECIMAL(10, 2) NOT NULL, " +
                "stock INT NOT NULL, " +
                "category_id INT, " +
                "FOREIGN KEY (category_id) REFERENCES categories(category_id))";
        DatabaseUtil.executeUpdate(sql);
    }

    private static void createTransactionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                "transaction_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "product_id INT, " +
                "type VARCHAR(255) NOT NULL, " +
                "quantity INT NOT NULL, " +
                "transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "price_per_unit DECIMAL(10, 2) NOT NULL, " +
                "FOREIGN KEY (product_id) REFERENCES products(product_id))";
        DatabaseUtil.executeUpdate(sql);
    }

    private static void createFinancialsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS financials (" +
                "financial_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "capital DECIMAL(10, 2), " +
                "total_income DECIMAL(10, 2), " +
                "total_expense DECIMAL(10, 2))";
        DatabaseUtil.executeUpdate(sql);
    }
}
