package com.ines.view;


public enum FontSize {
    SMALL,
    MEDIUM,
    LARGE;

    public static String getCssPath(FontSize fontSize){
        switch (fontSize){
            case SMALL:
                return "css/font_small.css";
            case MEDIUM:
                return "css/font_medium.css";
            case LARGE:
                return "css/font_large.css";
            default:
                return null;
        }
    }
}
