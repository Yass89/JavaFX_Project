package com.nada.poo.controller;

import com.nada.poo.dao.FinancialDaoImpl;
import com.nada.poo.dao.ProductDaoImpl;
import com.nada.poo.database.DatabaseUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.nada.poo.model.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Text;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class BuySellController extends  PageController{

    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> typeColumn;
    @FXML
    private TableColumn<Product, String> discountColumn;
    @FXML
    private TableColumn<Product, String> sizeColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;
    @FXML
    private Button sellButton;
    @FXML private Button buyButton;
    @FXML private Label capitalField;
    @FXML private Label incomesField;
    @FXML private Label costsField;
    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label typeLabel;
    @FXML private Label discountLabel;
    @FXML private Label sizeLabel;
    @FXML private TextField priceLabel;
    @FXML private Label stockLabel;



    // initialize the page
    @FXML
    public void initialize() {
        try {
            loadProducts();
            fillFinancialFields();
            productsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    handleRowSelection(newSelection);
                }
            });
        }
        catch (SQLException e){
            showError(e.getMessage());
        }
    }

    protected void handleRowSelection(Product product) {
        if (product == null) {
            return;
        }
        idLabel.setText(String.valueOf(product.getId()));
        nameLabel.setText(product.getName());
        typeLabel.setText(product.getType());
        // check the type of product and display in consequence
        switch (product.getCategoryId()) {
            case 1,2:
                sizeLabel.setText(String.valueOf(product.getSize()));
                break;
            case 3:
                sizeLabel.setText("");
                break;
        }
        discountLabel.setText(product.isDiscounted() ? "Yes" : "No");
        priceLabel.setText(String.format(Locale.US, "%.2f", product.getPrice()));
        stockLabel.setText(String.valueOf(product.getNbItems()));
        // enable buttons
        sellButton.setDisable(false);
        buyButton.setDisable(false);
    }

    // handle the sell button
    @FXML
    private void handleSellAction() {
        try {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        // sell the item with the price (double) in the price field and update the database
        if(priceLabel.getText().isEmpty() || !priceLabel.getText().matches("[0-9]+(\\.[0-9]+)?")) {
            showError("Please enter a price like this : 12.34");
            return;
        }
        product.sellItems(Double.parseDouble(priceLabel.getText()));
            ProductDaoImpl.updateProductDatabase(product.getId(),product);
            updateFinancialData();
            fillFinancialFields();
            loadProducts();
            productsTable.refresh();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    // handle the buy button
    @FXML
    private void handleBuyAction() {
        try {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        // buy the item with the price (double) in the price field and update the database
        if(priceLabel.getText().isEmpty() || !priceLabel.getText().matches("[0-9]+(\\.[0-9]+)?")) {
            showError("Please enter a price like this : 12.34");
            return;
        }
        product.purchaseItems(Double.parseDouble(priceLabel.getText()));

            ProductDaoImpl.updateProductDatabase(product.getCategoryId(),product);
            updateFinancialData();
            fillFinancialFields();
            loadProducts();
            productsTable.refresh();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void loadProducts() throws SQLException {
        // Get products from database and load them into the table
        List<Product> products = ProductDaoImpl.getProducts();
        if (products == null) {
            return;
        }
        ObservableList<Product> observableList = FXCollections.observableArrayList(products);
        productsTable.setItems(observableList);
        displayProductsInTable();
    }

    public void displayProductsInTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        discountColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isDiscounted() ? "Yes" : "No"));
        sizeColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCategoryId() == 1 || cellData.getValue().getCategoryId() == 2) {
                return new SimpleStringProperty(String.valueOf((cellData.getValue()).getSize()));
            }
            return new SimpleStringProperty("");
        });
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("nbItems"));
    }

    // handle the reset button
    @FXML
    protected void handleResetAction() {
        try {
            ProductDaoImpl.reset();
            loadProducts();
            // clear all the fields
            idLabel.setText("");
            nameLabel.setText("");
            typeLabel.setText("");
            sizeLabel.setText("");
            discountLabel.setText("");
            priceLabel.setText("");
            stockLabel.setText("");
            // disable buttons
            sellButton.setDisable(true);
            buyButton.setDisable(true);
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    // fill the financial fields
    public void fillFinancialFields() {
        try {
        // connect to the database
        Connection connection = DatabaseUtil.getConnection();
        // get the financial data from the database
        String sql = "SELECT * FROM financials";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if (resultSet == null) {
            return;
        }
            // show the financial data in the fields
            while (resultSet.next()) {
                capitalField.setText(resultSet.getDouble("capital") + "");
                incomesField.setText(resultSet.getDouble("total_income") + "");
                costsField.setText(resultSet.getDouble("total_cost") + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateFinancialData() {
        FinancialDaoImpl.updateDatabase();
    }
}
