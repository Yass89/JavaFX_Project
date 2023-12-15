package com.nada.poo.controller;

import com.nada.poo.dao.FinancialDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BuySellController extends  PageController{

    @FXML
    private Button sellButton;
    @FXML private Button buyButton;
    @FXML private Label capitalField;
    @FXML private Label incomesField;
    @FXML private Label costsField;

    private void updateFinancialData() {
        FinancialDaoImpl.updateDatabase();
    }
}
