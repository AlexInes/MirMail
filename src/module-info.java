module MirMail {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;

    opens com.ines;
    opens com.ines.view;
    opens com.ines.controller;
}