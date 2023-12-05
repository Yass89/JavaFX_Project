package com.nada.poo.dao;

import com.nada.poo.database.DatabaseUtil;
import com.nada.poo.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl {

    // Retrieves all products and their stock from the products table.
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = parseResultSetToProduct(rs);
                if (product != null) {
                    products.add(product);
                }
            }
        }
        return products;
    }

    // Inserts a new product into the products table.
    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getNbItems());
            pstmt.setInt(4, product.getCategoryId());
            pstmt.executeUpdate();
        }
    }

    // Updates an existing product in the products table.
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ?, category_id = ? WHERE product_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getNbItems());
            pstmt.setInt(4, product.getCategoryId());
            pstmt.setInt(5, product.getId());
            pstmt.executeUpdate();
        }
    }

    // Deletes a product from the products table.
    public void deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
        }
    }

    // Sells items, updating stock and incomes accordingly, and recording the transaction.
    public void sellProduct(int productId, int quantity, double sellPrice) throws SQLException {
        Product product = getProductById(productId);
        if (product == null) {
            throw new SQLException("Product not found with ID: " + productId);
        }
        product.sellItems(quantity,sellPrice); // Using the sell method from the Product class
        updateProduct(product); // Update the product database record
    }

    // Purchases items, updating stock and costs accordingly, and recording the transaction.
    public void purchaseProduct(int productId, int quantity, double purchasePrice) throws SQLException {
        Product product = getProductById(productId);
        if (product == null) {
            throw new SQLException("Product not found with ID: " + productId);
        }
        product.purchaseItems(quantity, purchasePrice); // Using the purchase method from the Product class
        updateProduct(product); // Update the product database record
    }

    // Applies a discount to a product based on its category.
    public void applyDiscount(int productId) throws SQLException {
        Product product = getProductById(productId);
        if (product == null) {
            throw new SQLException("Product not found with ID: " + productId);
        }
        product.applyDiscount(); // Using the applyDiscount method from the Product class
        updateProduct(product); // Update the product database record
    }

    // Parses a ResultSet into a Product object.
    private Product parseResultSetToProduct(ResultSet rs) throws SQLException {
        // Implementation details would depend on the actual subclasses and their constructors.
        int productId = rs.getInt("product_id");
        String productName = rs.getString("name");
        double productPrice = rs.getDouble("price");
        int productStock = rs.getInt("stock");
        int categoryId = rs.getInt("category_id");

        // Based on category, create the right subclass of Product
        switch (categoryId) {
            case 1:
                // Assuming Clothes constructor
                return new Clothes(productName, productPrice, productStock, /* Size */ 0);
            case 2:
                // Assuming Shoes constructor
                return new Shoes(productName, productPrice, productStock, /* ShoeSize */ 0);
            case 3:
                // Assuming the Accessories constructor
                return new Accessories(productName, productPrice, productStock);
            default:
                return null;
        }
    }

    // Utility method to get a Product by ID
    private Product getProductById(int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return parseResultSetToProduct(rs);
                }
            }
        }
        return null;
    }

    // Additional unimplemented methods: removeDiscount, getFinancialData, and validateProductData.
}