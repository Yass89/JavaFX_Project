package com.nada.poo.controller;

import com.nada.poo.dao.FinancialDaoImpl;
import com.nada.poo.dao.ProductDaoImpl;
import com.nada.poo.database.DatabaseUtil;
import com.nada.poo.model.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class HomeController extends PageController {

    @FXML
    private TableView<Product> productsTableHome;
    @FXML
    private ComboBox<String> categorySelector;
    @FXML
    private TableColumn<Product, String> idColumnHome;
    @FXML
    private TableColumn<Product, String> nameColumnHome;
    @FXML
    private TableColumn<Product, String> typeColumnHome;
    @FXML
    private TableColumn<Product, String> discountColumnHome;
    @FXML
    private TableColumn<Product, String> sizeColumnHome;
    @FXML
    private TableColumn<Product, Double> priceColumnHome;
    @FXML
    private TableColumn<Product, Integer> stockColumnHome;

    @FXML
    private Label incomesField;

    @FXML
    private Label costsField;

    @FXML
    private Label capitalField;

    // execute when the page is loaded
    @FXML
    public void initialize() {
        try {
            loadProducts();
            fillcategorySelector();
            fillFinancialFields();
        }
        catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    // fill the category selector with the categories from the database
    public void fillcategorySelector() throws SQLException {
        List<String> categories = ProductDaoImpl.getCategoriesFromDatabase();
        if (categories == null) {
            return;
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(categories);
        // add " everything " to the list
        observableList.add(0, "Everything");
        categorySelector.setItems(observableList);
    }

    // fill the table with the products from the database
    private void loadProducts() throws SQLException {
        // Get products from database and load them into the table
        List<Product> products = ProductDaoImpl.getProducts();
        if (products == null) {
            return;
        }
        ObservableList<Product> observableList = FXCollections.observableArrayList(products);
        productsTableHome.setItems(observableList);
        displayProductsInTable();
    }

    // display the products in the table
    public void displayProductsInTable() {
        idColumnHome.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnHome.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumnHome.setCellValueFactory(new PropertyValueFactory<>("type"));
        discountColumnHome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isDiscounted() ? "Yes" : "No"));
        sizeColumnHome.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCategoryId() == 1 || cellData.getValue().getCategoryId() == 2) {
                return new SimpleStringProperty(String.valueOf((cellData.getValue()).getSize()));
            }
            return new SimpleStringProperty("");
        });
        priceColumnHome.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumnHome.setCellValueFactory(new PropertyValueFactory<>("nbItems"));
    }

    // filter the table by category
    @FXML
    protected void filterByCategory() throws SQLException {
        // get the selected category from the category selector
        String category = categorySelector.getSelectionModel().getSelectedItem();
        // show the products from the selected category
        if (category.equals("Everything")) {
            loadProducts();
            return;
        }
        List<Product> products = ProductDaoImpl.getProductsByCategoryFromDatabase(category);
        if (products == null) {
            return;
        }
        ObservableList<Product> observableList = FXCollections.observableArrayList(products);
        productsTableHome.setItems(observableList);
    }

    // show financial data from the database
    public void fillFinancialFields() throws SQLException {
        // connect to the database
        Connection connection = DatabaseUtil.getConnection();
        // get the financial data from the database
        String sql = "SELECT * FROM financials";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if (resultSet == null) {
            return;
        }
        try {
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



}
