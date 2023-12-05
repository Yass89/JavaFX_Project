package com.nada.poo;
import com.nada.poo.database.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
            launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create database
        DatabaseSetup.databaseCreation();
        Parent root = FXMLLoader.load(getClass().getResource("view/ProductView.fxml"));
        primaryStage.setTitle("Product Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}