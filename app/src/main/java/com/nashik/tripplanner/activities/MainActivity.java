package com.nashik.tripplanner.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.fragments.*;
import com.nashik.tripplanner.utils.LocaleHelper;
import com.nashik.tripplanner.utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TAB = "tab", TAB_HOME = "home", TAB_HOTELS = "hotels", TAB_MAP = "map", TAB_CHAT = "chat", TAB_PROFILE = "profile";
    private BottomNavigationView nav;
    private SessionManager sm;

    @Override 
    protected void attachBaseContext(android.content.Context newBase) {
        sm = new SessionManager(newBase);
        // Apply language preference
        super.attachBaseContext(LocaleHelper.setLocale(newBase, sm.getLanguage()));
    }

    private final HomeFragment home = new HomeFragment(); 
    private final HotelsFragment hotels = new HotelsFragment();
    private final MapFragment map = new MapFragment(); 
    private final ChatFragment chat = new ChatFragment();
    private final ProfileFragment profile = new ProfileFragment();
    private Fragment active;

    @Override 
    protected void onCreate(Bundle b) {
        super.onCreate(b); 
        setContentView(R.layout.activity_main); 
        
        nav = findViewById(R.id.bottom_navigation);
        
        // Setup initial fragment state
        getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment_container, profile, TAB_PROFILE).hide(profile)
            .add(R.id.fragment_container, chat, TAB_CHAT).hide(chat)
            .add(R.id.fragment_container, map, TAB_MAP).hide(map)
            .add(R.id.fragment_container, hotels, TAB_HOTELS).hide(hotels)
            .add(R.id.fragment_container, home, TAB_HOME).commit();
            
        active = home;
        
        // Bottom Navigation listener
        nav.setOnItemSelectedListener(item -> {
            Fragment f = getF(item);
            if (f != null && f != active) {
                getSupportFragmentManager().beginTransaction().hide(active).show(f).commit();
                active = f;
            }
            return true;
        });
        
        // Check for specific tab request from intent
        String t = getIntent().getStringExtra(EXTRA_TAB);
        if (t != null) switchTo(t);
    }

    /**
     * Maps menu item ID to its corresponding Fragment instance
     */
    private Fragment getF(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) return home;
        if (id == R.id.nav_hotels) return hotels;
        if (id == R.id.nav_map) return map;
        if (id == R.id.nav_chat) return chat;
        if (id == R.id.nav_profile) return profile;
        return null;
    }

    /**
     * Programmatically switch between bottom navigation tabs
     */
    public void switchTo(String tab) {
        switch (tab) {
            case TAB_HOTELS: nav.setSelectedItemId(R.id.nav_hotels); break;
            case TAB_MAP: nav.setSelectedItemId(R.id.nav_map); break;
            case TAB_CHAT: nav.setSelectedItemId(R.id.nav_chat); break;
            case TAB_PROFILE: nav.setSelectedItemId(R.id.nav_profile); break;
            default: nav.setSelectedItemId(R.id.nav_home);
        }
    }

    @Override 
    public void onBackPressed() {
        // If not on home tab, return to home tab first
        if (active != home) {
            nav.setSelectedItemId(R.id.nav_home);
        } else {
            super.onBackPressed();
        }
    }

    @Override 
    protected void onNewIntent(Intent i) {
        super.onNewIntent(i);
        String t = i.getStringExtra(EXTRA_TAB);
        if (t != null) switchTo(t);
    }
}
