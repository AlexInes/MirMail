package com.ines.view;

import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.STYLESHEET_MODENA;

public enum Theme {
    MODENA,
    CASPIAN;

    public static String getStylesheetName(Theme theme) {
        switch (theme){
            case MODENA:
                return STYLESHEET_MODENA;
            case CASPIAN:
                return STYLESHEET_CASPIAN;
            default:
                return STYLESHEET_CASPIAN;
        }
    }
}
