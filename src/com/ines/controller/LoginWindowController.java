package com.ines.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginWindowController {

    @FXML
    private TextField LoginEmailField;

    @FXML
    private PasswordField LoginPasswordField;

    @FXML
    private Label LoginErrorField;

    @FXML
    void loginButtonAction() {
        System.out.println("It works");
    }

}
