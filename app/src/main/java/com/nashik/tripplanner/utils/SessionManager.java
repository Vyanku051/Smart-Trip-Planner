package com.nashik.tripplanner.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages user session, preferences, and local data persistence using SharedPreferences.
 */
public class SessionManager {
    private static final String PREF_NAME = "NashikPrefs";
    private static final String KEY_IS_LOGGED_IN = "li";
    private static final String KEY_ONBOARDING_DONE = "od";
    private static final String KEY_NAME = "nm";
    private static final String KEY_EMAIL = "em";
    private static final String KEY_DARK_MODE = "dk";
    private static final String KEY_NOTIFICATIONS = "nt";
    private static final String KEY_LANGUAGE = "lng";

    private final SharedPreferences pref;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        
        // Initialize default user if not exists
        if (!pref.contains(KEY_NAME)) {
            pref.edit()
                .putString(KEY_NAME, "Ajay Kumar")
                .putString(KEY_EMAIL, "vyankateshmagare2004@gmail.com")
                .apply();
        }
    }

    public void setLoggedIn(boolean isLoggedIn) {
        pref.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setOnboardingDone(boolean isDone) {
        pref.edit().putBoolean(KEY_ONBOARDING_DONE, isDone).apply();
    }

    public boolean isOnboardingDone() {
        return pref.getBoolean(KEY_ONBOARDING_DONE, false);
    }

    public void saveUser(String name, String email) {
        pref.edit().putString(KEY_NAME, name).putString(KEY_EMAIL, email).apply();
    }

    public String getUserName() {
        return pref.getString(KEY_NAME, "Ajay Kumar");
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, "vyankateshmagare2004@gmail.com");
    }

    public String getLanguage() {
        return pref.getString(KEY_LANGUAGE, "en");
    }

    public void setLanguage(String languageCode) {
        pref.edit().putString(KEY_LANGUAGE, languageCode).apply();
    }

    public String getLanguageDisplay() {
        String l = getLanguage();
        if ("hi".equals(l)) return "हिंदी";
        if ("mr".equals(l)) return "मराठी";
        return "English (US)";
    }

    public String getFirstName() {
        String name = getUserName().trim();
        String[] parts = name.split(" ");
        return parts.length > 0 ? parts[0] : name;
    }

    public String getUserInitials() {
        String name = getUserName().trim();
        if (name.isEmpty()) return "T";
        String[] parts = name.split(" ");
        if (parts.length >= 2) {
            return "" + Character.toUpperCase(parts[0].charAt(0)) + Character.toUpperCase(parts[1].charAt(0));
        }
        return "" + Character.toUpperCase(name.charAt(0));
    }

    public void setDarkMode(boolean isEnabled) {
        pref.edit().putBoolean(KEY_DARK_MODE, isEnabled).apply();
    }

    public boolean isDarkMode() {
        return pref.getBoolean(KEY_DARK_MODE, false);
    }

    public void setNotificationsEnabled(boolean isEnabled) {
        pref.edit().putBoolean(KEY_NOTIFICATIONS, isEnabled).apply();
    }

    public boolean isNotificationsEnabled() {
        return pref.getBoolean(KEY_NOTIFICATIONS, true);
    }

    public void logout() {
        pref.edit().clear().apply();
    }
}
