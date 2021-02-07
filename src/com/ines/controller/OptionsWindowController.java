package com.ines.controller;

import com.ines.EmailManager;
import com.ines.view.FontSize;
import com.ines.view.Theme;
import com.ines.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsWindowController extends BaseController implements Initializable {

    @FXML
    private Slider fontSizeSlider;

    @FXML
    private ChoiceBox<Theme> themeDropBox;

    public OptionsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void applyOptions() {
        viewFactory.setTheme(themeDropBox.getValue());
        viewFactory.setFontSize(FontSize.values()[(int)(fontSizeSlider.getValue())]);
        viewFactory.setStyles();

    }

    @FXML
    void cancelOptions() {
        Stage stage = (Stage) fontSizeSlider.getScene().getWindow();
        viewFactory.closeWindow(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpThemeChoiceBox();
        setUpFontSizeSlider();
    }

    private void setUpFontSizeSlider() {
        fontSizeSlider.setMin(0);
        fontSizeSlider.setMax(FontSize.values().length - 1);
        fontSizeSlider.setValue(viewFactory.getFontSize().ordinal());
        fontSizeSlider.setMajorTickUnit(1);
        fontSizeSlider.setMinorTickCount(0);
        fontSizeSlider.setBlockIncrement(1);
        fontSizeSlider.setSnapToTicks(true);
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.setShowTickMarks(true);

        fontSizeSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double aDouble) {
                int i = aDouble.intValue();
                return FontSize.values()[i].toString();
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });
        fontSizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            fontSizeSlider.setValue(newVal.intValue());
        });
    }

    private void setUpThemeChoiceBox(){
        themeDropBox.setItems(FXCollections.observableArrayList(Theme.values()));
        themeDropBox.setValue(viewFactory.getTheme());
    }
}