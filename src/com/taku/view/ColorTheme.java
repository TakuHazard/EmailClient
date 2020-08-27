package com.taku.view;

public enum  ColorTheme {
    LIGHT,
    DEFAULT,
    DARK;

    public static String getCssPath(ColorTheme theme){
        switch(theme){
            case LIGHT:
                return "css/themeLight.css";
            case DARK:
                return "css/themeDark.css";
            case DEFAULT:
                return "css/themeDefault.css";
            default:
                return null;
        }
    }
}
