package com.ines.controller;

import com.ines.EmailManager;
import com.ines.controller.services.LoginService;
import com.ines.controller.services.LoginValidationService;
import com.ines.model.MailAccount;
import com.ines.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {

    @FXML
    private TextField loginEmailField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Label loginErrorField;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction() {
        System.out.println("Login button pressed!");
        if(loginValidationPassed()){
            System.out.println("Login validated");
            MailAccount mailAccount = new MailAccount(loginEmailField.getText(), loginPasswordField.getText());
            LoginService loginService = new LoginService(mailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult= loginService.getValue();
                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("login succesfull!!!" + mailAccount);
                        if(!viewFactory.isMainViewInitialized()) { viewFactory.showMainWindow(); }
                        Stage stage = (Stage) loginErrorField.getScene().getWindow();
                        viewFactory.closeWindow(stage);
                        return;
                    case FAILED_WRONG_CREDENTIALS:
                        loginErrorField.setText("invalid credentials!");
                        return;
                    case FAILED_OTHER_ERROR:
                        loginErrorField.setText("unexpected error!");
                        return;
                    default:
                        return;
                }
            });
        }
    }

    private boolean loginValidationPassed() {
        if(!LoginValidationService.emailValidated(loginEmailField.getText())){
            loginErrorField.setText("Provide correct email address");
            return false;
        }
        if(!LoginValidationService.passwordValidated(loginPasswordField.getText())){
            loginErrorField.setText("Provide email password");
            return false;
        }
        return true;
    }
}
