package com.nada.poo.controller;


//import com.nada.poo.dao.ProductDaoImpl;
import com.nada.poo.dao.FinancialDaoImpl;
import com.nada.poo.dao.ProductDaoImpl;
import com.nada.poo.database.DatabaseUtil;
import com.nada.poo.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ProductController extends PageController {


    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> typeColumn;
    @FXML private TableColumn<Product, String> discountColumn;
    @FXML private TableColumn<Product, String> sizeColumn;
    @FXML private TableColumn<Product, Double> priceColumn;
    @FXML private TableColumn<Product, Integer> stockColumn;
    @FXML private TextField idField;;
    @FXML private TextField sizeField;
    @FXML private ComboBox<String> discountField;
    @FXML private ComboBox<String> typeField;

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;



    public void initialize() throws SQLException {
        // Load initial data for financial fields and products
        discountField.getItems().addAll("Yes", "No");
        loadProducts();
        fillcategorySelector();
        productsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleRowSelection(newSelection);
            }
        });
    }

    // fill the category selector with the categories from the database
    public void fillcategorySelector() throws SQLException {
        List<String> categories = ProductDaoImpl.getCategoriesFromDatabase();
        ObservableList<String> observableList = FXCollections.observableArrayList(categories);
        typeField.setItems(observableList);
    }

    @FXML
    protected void handleAdd(ActionEvent event) throws SQLException {
        try{// Get data from fields, create product, add product to database
        // check if the values are empty or not except for size
        if (nameField.getText().isEmpty() || discountField.getValue() == null || priceField.getText().isEmpty() || stockField.getText().isEmpty()) {
            showError("Please fill all fields");
            return;
        }
        // check if the id is empty if not show error
        if (!idField.getText().isEmpty()) {
            System.out.println(idField.getText());
            showError("Please don't enter an id if you want to add a product");
            return;
        }
        // check if each field is in the correct format
        if (!priceField.getText().matches("[0-9]+(\\.[0-9]+)?")) {
            showError("Please enter a valid price");
            return;
        }
        if (!stockField.getText().matches("[0-9]+")) {
            showError("Please enter a valid stock");
            return;
        }
        // Get data from fields, create product, add product to database
        String name = nameField.getText();
        String type = typeField.getValue();
        String discount = discountField.getValue();
        double price = Double.parseDouble(priceField.getText());
        int stock = Integer.parseInt(stockField.getText());
        // verify that category is selected
        if (type.equals("Choose...")) {
            showError("Please select a category");
            return;
        }
        // verify  if the discount is yes or no
        if (discount.equals("Choose...")) {
            showError("Please select a discount");
            return;
        }
        Product product = null;
        // get the category id from the database
        int category_id = ProductDaoImpl.getCategoryIdFromDatabase(type);
        switch (category_id) {
            case 1:
                if (!sizeField.getText().matches("[0-9]+")) {
                    showError("Please enter a valid size");
                    return;
                }
                // verify all fields for clothes
                if (sizeField.getText().isEmpty()) {
                    showError("Please fill all fields");
                    return;
                }
                product = new Clothes(name, price, stock, Integer.parseInt(sizeField.getText()));
                if (discount.equals("Yes")) {
                    product.applyDiscount();
                }
                break;
            case 2:
                if (!sizeField.getText().matches("[0-9]+")) {
                    showError("Please enter a valid size");
                    return;
                }
                // verify all fields for shoes
                if (sizeField.getText().isEmpty()) {
                    showError("Please fill all fields");
                    return;
                }
                product = new Shoes(name, price, stock, Integer.parseInt(sizeField.getText()));
                if (discount.equals("Yes")) {
                    product.applyDiscount();
                }
                break;
            case 3:
                // verify all fields for accessories
                product = new Accessories(name, price, stock);
                // check the discount
                if (discount.equals("Yes")) {
                    product.applyDiscount();
                }
                break;
        }
        ProductDaoImpl.addProduct(product);
        loadProducts();
    }
        catch (Exception e){
            showError(e.getMessage());
            e.printStackTrace();
        }
    }

    // display each column in the table
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
    @FXML
    protected void handleUpdate(ActionEvent event) {
        try {
            // check if the values are empty or not except for size
            if (nameField.getText().isEmpty() || discountField.getValue() == null || priceField.getText().isEmpty() || stockField.getText().isEmpty()) {
                showError("Please fill all fields");
                return;
            }
            // check if the id is empty if not show error
            if (idField.getText() == null) {
                showError("Please enter an id if you want to update a product");
                return;
            }

            // check if the id is a number
            if(!idField.getText().matches("[0-9]+")){
                showError("Please enter a valid id");
                return;
            }
            // check if each field is in the correct format
            if (!priceField.getText().matches("[0-9]+(\\.[0-9]+)?")) {
                showError("Please enter a valid price");
                return;
            }
            if (!stockField.getText().matches("[0-9]+")) {
                showError("Please enter a valid stock");
                return;
            }
            // Get data from fields, create product, add product to database
            String name = nameField.getText();
            String type = typeField.getValue();
            String discount = discountField.getValue();
            double price = Double.parseDouble(priceField.getText());
            System.out.println(price);
            int stock = Integer.parseInt(stockField.getText());
            int id = Integer.parseInt(idField.getText());
            // take the previous product from the database with the id
            Product product = ProductDaoImpl.getProductById(id);
            if(product==null){
                showError("this id doesn't exist");
                return;
            }
            int size = 0;
            // get the category id from the database
            int category_id = ProductDaoImpl.getCategoryIdFromDatabase(type);
            switch (category_id) {
                case 1:
                    if (!sizeField.getText().matches("[0-9]+")) {
                        showError("Please enter a valid size");
                        return;
                    }
                    // verify all fields for clothes
                    if (sizeField.getText().isEmpty()) {
                        showError("Please fill all fields");
                        return;
                    }
                    size = Integer.parseInt(sizeField.getText());

                    // delete the previous product from the database
                    ProductDaoImpl.deleteProductNoDatabase(id);
                    // set the new product with the new values
                    product = new Clothes(id,name, price, stock, size);
                    System.out.println(product.getPrice());
                    if (!product.isDiscounted() && discount.equals("Yes")) {
                        product.applyDiscount();
                    } else if ( product.isDiscounted() && discount.equals("No")) {
                        product.removeDiscount();
                    }
                    break;
                // verify all fields for shoes
                // set the new product with the new values
                case 2:
                    if (!sizeField.getText().matches("[0-9]+")) {
                        showError("Please enter a valid size");
                        return;
                    }
                    if (sizeField.getText().isEmpty()) {
                        showError("Please fill all fields");
                        return;
                    }
                    size = Integer.parseInt(sizeField.getText());
                    // delete the previous product from the database
                    ProductDaoImpl.deleteProductNoDatabase(id);
                    product = new Shoes(id,name, price, stock, size);
                    if (!product.isDiscounted() && discount.equals("Yes")) {
                        product.applyDiscount();
                    } else if ( product.isDiscounted() && discount.equals("No")) {
                        product.removeDiscount();
                    }
                    break;
                case 3:
                    // verify that size is empty
                    if (!sizeField.getText().isEmpty()) {
                        showError("Please don't enter a size for accessories");
                        return;
                    }
                    // delete the previous product from the database
                    ProductDaoImpl.deleteProductNoDatabase(id);
                    product = new Accessories(id,name, price, stock);
                    // check the discount
                    System.out.println(product.getPrice()+"hello");
                    if (!product.isDiscounted() && discount.equals("Yes")) {
                        product.applyDiscount();
                    } else if ( product.isDiscounted() && discount.equals("No")) {
                        product.removeDiscount();
                    }
                    break;
            }
            // update product in database
            System.out.println(product.getPrice());
            ProductDaoImpl.updateProduct(id, product);
            loadProducts();
        } catch (Exception e) {
            showError(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleDelete(ActionEvent event) {
       try {
           if (idField.getText() == null) {
               showError("Please enter an id to delete the product");
               return;
           }
              // check if id is valid
            if(!idField.getText().matches("[0-9]+")){
                showError("Please enter a valid id");
                return;
            }
           int id = Integer.parseInt(idField.getText());
           // check if id is valid
              if (ProductDaoImpl.getProductById(id) == null) {
                showError("Please enter a valid id");
                return;
           }
           // Delete product from database
           ProductDaoImpl.deleteProduct(id);
           loadProducts();
       }
         catch (Exception e){
              showError(e.getMessage());
              e.printStackTrace();
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
@FXML
    protected void ResetAllDatas(ActionEvent event) {
        try {
            ProductDaoImpl.reset();
            loadProducts();
            // clear all the fields
            idField.setText("");
            nameField.setText("");
            typeField.setValue("Choose...");
            sizeField.setText("");
            discountField.setValue("Choose...");
            priceField.setText("");
            stockField.setText("");
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }



    // display product data in the fields
    @FXML
    protected void handleRowSelection(Product product) {
        if (product == null) {
            return;
        }
        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        typeField.setValue(product.getType());
        // check the type of product and display in consequence
        switch (product.getCategoryId()) {
            case 1:
                Clothes clothes = (Clothes) product;
                sizeField.setText(String.valueOf(clothes.getSize()));
                break;
            case 2:
                Shoes shoes = (Shoes) product;
                sizeField.setText(String.valueOf(shoes.getSize()));
                break;
            case 3:
                sizeField.setText("");
                break;
        }
        discountField.setValue(product.isDiscounted() ? "Yes" : "No");
        priceField.setText(String.format("%.2f", product.getPrice()));
        stockField.setText(String.valueOf(product.getNbItems()));
    }






}