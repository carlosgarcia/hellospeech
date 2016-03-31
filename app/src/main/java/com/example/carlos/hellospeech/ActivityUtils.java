package com.example.carlos.hellospeech;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ActivityUtils {

    private static ActivityUtils THE_INSTANCE = new ActivityUtils();

    private ActivityUtils() {

    }

    public static ActivityUtils getInstance() {
        return THE_INSTANCE;
    }

    public boolean isConnected(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
