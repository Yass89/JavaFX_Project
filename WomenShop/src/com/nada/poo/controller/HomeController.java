package com.nada.poo.controller;

import com.nada.poo.dao.ProductDaoImpl;
import com.nada.poo.database.DatabaseUtil;
import com.nada.poo.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.event.ActionEvent;
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
        /*loadProducts();
        fillcategorySelector();
        fillFinancialFields();*/
    }

    // fill the category selector with the categories from the database
    public void fillcategorySelector() throws SQLException {
        List<String> categories = ProductDaoImpl.getCategoriesFromDatabase();
        if (categories == null) {
            return;
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(categories);
        categorySelector.setItems(observableList);
    }

    // fill the table with the products from the database
    public void loadProducts() throws SQLException {
        List<Product> products = ProductDaoImpl.getProductsFromDatabase();
        if (products == null) {
            return;
        }
        ObservableList<Product> observableList = FXCollections.observableArrayList(products);
        productsTableHome.setItems(observableList);
    }

    // filter the table by category
    @FXML
    public void filterByCategory(ActionEvent event) throws SQLException {
        // get the selected category from the category selector
        String category = categorySelector.getSelectionModel().getSelectedItem();
        // show the products from the selected category
        List<Product> products = ProductDaoImpl.getProductsByCategoryFromDatabase(category);
        if (products == null) {
            return;
        }
        ObservableList<Product> observableList = FXCollections.observableArrayList(products);
        productsTableHome.setItems(observableList);
    }

    // show financial data from the database
    public void fillFinancialFields() {
        // get the financial data from the database
        ResultSet resultSet = DatabaseUtil.executeQuery("SELECT * FROM financials");
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
