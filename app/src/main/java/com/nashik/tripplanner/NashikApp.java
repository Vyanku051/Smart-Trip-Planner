package com.nashik.tripplanner;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;

import com.nashik.tripplanner.utils.SessionManager;

public class NashikApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SessionManager sm = new SessionManager(this);
        if (sm.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
