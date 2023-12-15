package com.nada.poo.dao;

import com.nada.poo.database.DatabaseUtil;
import com.nada.poo.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl {


    public static List<Product> products = new ArrayList<>();

static  {
    try {
        products = getProductsFromDatabase();
        fillCategories();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

// setter for products
    public static void setProducts(List<Product> products) {
        ProductDaoImpl.products = products;
    }
// fill categorie table in the database
    public static void fillCategories() throws SQLException {
        // check if the categories table is empty
        String sql = "SELECT * FROM categories";
        Connection connection = DatabaseUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return;
        }
        sql = "INSERT INTO categories (category_id, category_name) VALUES (1, 'Clothes'), (2, 'Shoes'), (3, 'Accessories')";
        DatabaseUtil.executeUpdate(sql);
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static List<Product> getProductsByCategory(String category) {
        List<Product> productsByCategory = new ArrayList<>();
        for (Product product : products) {
            if (product.getType().equals(category)) {
                productsByCategory.add(product);
            }
        }
        return productsByCategory;
    }

    public static List<Product> getProductsByName(String name) {
        List<Product> productsByName = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().equals(name)) {
                productsByName.add(product);
            }
        }
        return productsByName;
    }

    public static Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
}
    public static void addProduct(Product product) {
        ProductDaoImpl.products.add(product);
        addProductDatabase(product);
    }

    public static void deleteProduct(int i) {
        Product product = getProductById(i);
        ProductDaoImpl.products.remove(product);
        deleteProductDatabase(product);
    }

    public static void deleteProductNoDatabase(int i) {
        Product product = getProductById(i);
        ProductDaoImpl.products.remove(product);
    }

    public static void updateProduct(int id,Product product) {
        System.out.println(product.getPrice());
        ProductDaoImpl.products.add(product);
        updateProductDatabase(id,product);
    }


    // update the database with the products list
    public static void reset() throws SQLException {

        // delete all the products from the database, beggining with categories
        String sql = "DELETE FROM categories";
        DatabaseUtil.executeUpdate(sql);
        sql = "DELETE FROM products";
        DatabaseUtil.executeUpdate(sql);

        products.clear();
        fillCategories();

        // update financials to 1000 capital, 0 income and 0 cost
        sql = "UPDATE financials SET capital = 1000 , total_income = 0, total_cost = 0";
        DatabaseUtil.executeUpdate(sql);
    }

    // get product by category from the database
    public static List<Product> getProductsByCategoryFromDatabase(String category) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = (SELECT category_id FROM categories WHERE category_name = '" + category + "')";
        Connection connection = DatabaseUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        try {
            while (rs.next()) {
                // check the category of the product
                int category_id = rs.getInt("category_id");
                switch (category_id) {
                    case 1:
                        products.add(new Clothes(rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"), rs.getInt("size")));
                        break;
                    case 2:
                        products.add(new Shoes(rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"), rs.getInt("size")));
                        break;
                    case 3:
                        products.add(new Accessories(rs.getString("name"), rs.getDouble("price"), rs.getInt("stock")));
                        break;
                }
                // check if the product has a discount
                String discount = rs.getString("discount");
                if (discount.equals("Yes")) {
                    products.get(products.size() - 1).applyDiscount();
                }
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // get category id from the database
    public static int getCategoryIdFromDatabase(String category) throws SQLException {
        String sql = "SELECT category_id FROM categories WHERE category_name = '" + category + "'";
        Connection connection = DatabaseUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        try {
            while (rs.next()) {
                return rs.getInt("category_id");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    // update one product in the database
    public static void updateProductDatabase(int i,Product p) {
        // check if the product has a discount
        String discount = "No";
        if (p.isDiscounted()) {
            discount = "Yes";
        }
        String sql = "UPDATE products SET name = ?, price = ?, stock = ?, size = ?, category_id = ?, discount = ? WHERE product_id = ?";
        DatabaseUtil.executeUpdate(sql, p.getName(), p.getPrice(), p.getNbItems(),p.getSize(), p.getCategoryId(), discount, i);
    }

    // add one product in the database
    public static void addProductDatabase(Product p) {
        // check if the product has a discount
        String discount = "No";
        if (p.isDiscounted()) {
            discount = "Yes";
        }
        String sql = "INSERT INTO products (name, price, stock, size, category_id,discount) VALUES (?, ?, ?, ?,?,?)";
        DatabaseUtil.executeUpdate(sql, p.getName(), p.getPrice(), p.getNbItems(),p.getSize(), p.getCategoryId(), discount);
    }

    // delete one product in the database
    public static void deleteProductDatabase(Product p) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        DatabaseUtil.executeUpdate(sql, p.getId());
    }

    // get all the products from the database
    public static List<Product> getProductsFromDatabase() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        Connection connection = DatabaseUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        try {
            while (rs.next()) {
                // check the category of the product
                int category_id = rs.getInt("category_id");
                switch (category_id) {
                    case 1:
                        products.add(new Clothes(rs.getInt("product_id"),rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"), rs.getInt("size")));
                        break;
                    case 2:
                        products.add(new Shoes(rs.getInt("product_id"),rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"), rs.getInt("size")));
                        break;
                    case 3:
                        products.add(new Accessories(rs.getInt("product_id"),rs.getString("name"), rs.getDouble("price"), rs.getInt("stock")));
                        break;
                }
                // check if the product has a discount
                String discount = rs.getString("discount");
                if (discount.equals("Yes")) {
                    products.get(products.size() - 1).applyDiscount();
                }
            };
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // get all the categories from the database
    public static List<String> getCategoriesFromDatabase() throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        Connection connection = DatabaseUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        try {
            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}