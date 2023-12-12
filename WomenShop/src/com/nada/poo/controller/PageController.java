package com.nada.poo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

// this class is used to control the navigation of the application
public class PageController extends ProductController {


    @FXML
    // this method is used to navigate to the home page
    private void handleEnterAction(ActionEvent event) {
        try {
            // Load HomePage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/HomePage.fxml")); // Update the path if needed
            Parent homePage = loader.load();

            // Set the scene to HomePage
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(homePage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @FXML
    // this method is used to navigate to the product page
    private void handleProductAction(ActionEvent event) {
        try {
            // Load ProductPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Productpage.fxml")); // Update the path if needed
            Parent productPage = loader.load();

            // Set the scene to ProductPage
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(productPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @FXML
    // this method is used to navigate to the BuySell page
    private void handleBuySellAction(ActionEvent event) {
        try {
            // Load BuySellPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/BuySellPage.fxml")); // Update the path if needed
            Parent buySellPage = loader.load();

            // Set the scene to BuySellPage
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(buySellPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
}
