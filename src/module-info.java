module MirMail {
    requires activation;
    requires java.mail;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.ines.view;
    opens com.ines.controller;
    opens com.ines;
    opens com.ines.model;

}