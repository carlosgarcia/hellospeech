package com.example.carlos.hellospeech;

import java.util.Locale;

public class AppContextUtils {

    private static AppContextUtils THE_INSTANCE = new AppContextUtils();

    private static final Locale MY_LOCALE = new Locale("es", "ES");

    private AppContextUtils() {

    }

    public static AppContextUtils getInstance() {
        return THE_INSTANCE;
    }

    public Locale getLocale() {
        return MY_LOCALE;
    }
}
