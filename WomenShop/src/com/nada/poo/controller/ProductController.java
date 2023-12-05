package com.nada.poo.controller;


import com.nada.poo.dao.ProductDaoImpl;
import com.nada.poo.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProductController {
    @FXML private ComboBox<String> categorySelector;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, Double> priceColumn;
    @FXML private TableColumn<Product, Integer> stockColumn;
    @FXML private TextField capitalField;
    @FXML private TextField incomesField;
    @FXML private TextField costsField;
    @FXML private Label errorLabel;

    private final ProductDaoImpl productDAO = new ProductDaoImpl();
    private ObservableList<Product> products = FXCollections.observableArrayList();
    // You would also need lists for each category or a filter mechanism

    public void initialize() {
        // Initialize TableView column data bindings
        // Initialize ComboBox with category options - this could be done via a DAO as well

        // Load initial data for financial fields and products
        updateFinancialData();
        loadProducts();
    }

    @FXML
    protected void handleAdd(ActionEvent event) {
        // Show dialog for new product and add product to database
    }

    @FXML
    protected void handleUpdate(ActionEvent event) {
        // Get selected product, show dialog for editing, update product in database
    }

    @FXML
    protected void handleDelete(ActionEvent event) {
        // Get selected product and delete product from database
    }

    @FXML
    protected void handleSell(ActionEvent event) {
        // Show dialog for quantity, update product stock and incomes in database
    }

    @FXML
    protected void handlePurchase(ActionEvent event) {
        // Show dialog for quantity and purchasing price, update product stock and costs in database
    }

    @FXML
    protected void handleApplyDiscount(ActionEvent event) {
        // Apply discount to selected product and update in database
    }

    @FXML
    protected void handleStopDiscount(ActionEvent event) {
        // Stop discount for the selected product and update in database
    }

    private void loadProducts() {
        products.clear();
        try {
            products.addAll(productDAO.getAllProducts());
        } catch (Exception e) {
            showError("Unable to load products: " + e.getMessage());
        }
    }

    private void updateFinancialData() {
        // Fetch capital, incomes, and costs then update respective fields
        // capitalField.setText(...);
        // incomesField.setText(...);
        // costsField.setText(...);
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    // You might implement additional methods to fetch and update capital, incomes, and costs fields from the database
}