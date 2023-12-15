package com.nada.poo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

// this class is used to control the navigation of the application
public class PageController{

    @FXML private Label errorMessage;
@FXML private Button buttonError;

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


@FXML
protected void closePopup(ActionEvent event) {

    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    stage.close();

}

    protected void showError(String message) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ErrorPopup.fxml"));
            Parent root = loader.load();

            // Get the controller and set the error message
            PageController controller = loader.getController();
            controller.errorMessage.setText(message); // Ensure your controller has public access to errorMessage

            // Create a new stage for the pop-up
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
            stage.setTitle("Error");
            stage.setScene(new Scene(root));

            // Set action for the button
            controller.buttonError.setOnAction(e -> stage.close());

            // Show the pop-up
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception here
        }
    }
}
