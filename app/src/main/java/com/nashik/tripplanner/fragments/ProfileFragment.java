package com.nashik.tripplanner.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.activities.*;
import com.nashik.tripplanner.utils.*;
import com.nashik.tripplanner.utils.LocaleHelper;
public class ProfileFragment extends Fragment {
    private SessionManager sm; 
    private TripManager tm; 
    private static final int REQ = 101;

    @Nullable 
    @Override 
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle s) {
        // Inflate the profile layout
        return i.inflate(R.layout.fragment_profile, c, false);
    }

    @Override 
    public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        super.onViewCreated(v, s);
        sm = new SessionManager(requireContext());
        tm = new TripManager(requireContext());
        setupAll(v);
    }

    @Override 
    public void onResume() {
        super.onResume();
        if (getView() != null) setupCard(getView());
    }

    /**
     * Initializes all UI components on the profile screen
     */
    void setupAll(View v) {
        setupCard(v);
        setupPrefs(v);
        setupMenu(v);
    }

    /**
     * Sets up the user profile card with name, email and avatar
     */
    void setupCard(View v) {
        // Display user basic info
        set(v, R.id.tv_profile_name, sm.getUserName());
        set(v, R.id.tv_profile_email, sm.getUserEmail());
        set(v, R.id.tv_profile_initials, sm.getUserInitials());
        
        // The stats (trips, visits, reviews) have been removed from the layout as requested
        
        // Show count in the menu row
        set(v, R.id.tv_row_trips_count, String.valueOf(tm.count()));
        
        // Edit Profile Button
        View btnEdit = v.findViewById(R.id.btn_edit_profile);
        if (btnEdit != null) {
            btnEdit.setOnClickListener(b -> startActivityForResult(new Intent(requireContext(), EditProfileActivity.class), REQ));
        }
        
        // Header Notifications Button
        View btnNotif = v.findViewById(R.id.btn_notifications_header);
        if (btnNotif != null) {
            btnNotif.setOnClickListener(b -> startActivity(new Intent(requireContext(), NotificationsActivity.class)));
        }
        
        // Avatar text in header
        TextView tvAv = v.findViewById(R.id.tv_avatar_header);
        if (tvAv != null) tvAv.setText(sm.getUserInitials());
        
        // Nashik badge logic based on trip count
        int trips = tm.count();
        set(v, R.id.tv_nashik_badge_title, trips >= 3 ? "Nashik Explorer 🏆" : trips >= 1 ? "Nashik Traveler 🌿" : "Nashik Beginner 🗺️");
    }

    /**
     * Sets up app settings like Notifications, Dark Mode, and Language
     */
    void setupPrefs(View v) {
        // Notification Toggle
        Switch swN = v.findViewById(R.id.switch_notifications);
        if (swN != null) {
            swN.setChecked(sm.isNotificationsEnabled());
            swN.setOnCheckedChangeListener((b, on) -> {
                sm.setNotificationsEnabled(on);
                Toast.makeText(requireContext(), on ? "Notifications enabled" : "Notifications disabled", Toast.LENGTH_SHORT).show();
            });
        }

        // Dark Mode Toggle
        Switch swD = v.findViewById(R.id.switch_dark_mode);
        if (swD != null) {
            swD.setChecked(sm.isDarkMode());
            swD.setOnCheckedChangeListener((b, on) -> {
                sm.setDarkMode(on);
                AppCompatDelegate.setDefaultNightMode(on ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            });
        }
        
        // Language Display and Selection
        LinearLayout rowLang = v.findViewById(R.id.row_language);
        if (rowLang != null) {
            for (int k = 0; k < rowLang.getChildCount(); k++) {
                View child = rowLang.getChildAt(k);
                if (child instanceof TextView) {
                    TextView tv = (TextView) child;
                    if (tv.getText().toString().contains("(") || tv.getText().toString().contains("हिंदी") || tv.getText().toString().contains("मराठी")) {
                        tv.setText(sm.getLanguageDisplay());
                        break;
                    }
                }
            }
        }

        row(v, R.id.row_language, () -> picker("Select Language", new String[]{"English (US)", "हिंदी (Hindi)", "मराठी (Marathi)"}, s -> {
            String code = "en";
            if (s.contains("हिंदी")) code = "hi";
            else if (s.contains("मराठी")) code = "mr";
            sm.setLanguage(code);
            LocaleHelper.setLocale(requireContext(), code);
            if (getActivity() != null) getActivity().recreate();
        }));

        // Other settings (Placeholders)
        row(v, R.id.row_currency, () -> picker("Currency", new String[]{"INR – ₹", "USD – $", "EUR – €"}, s -> Toast.makeText(requireContext(), "Currency: " + s, Toast.LENGTH_SHORT).show()));
        row(v, R.id.row_ai_tuning, () -> picker("AI Style", new String[]{"Standard", "Creative", "Detailed", "Concise"}, s -> Toast.makeText(requireContext(), "AI Style: " + s, Toast.LENGTH_SHORT).show()));
    }

    /**
     * Sets up the menu items for "More" section
     */
    void setupMenu(View v) {
        row(v, R.id.row_my_trips, () -> ((MainActivity) requireActivity()).switchTo(MainActivity.TAB_HOME));
        row(v, R.id.row_saved_places, () -> ((MainActivity) requireActivity()).switchTo(MainActivity.TAB_MAP));
        row(v, R.id.row_payment, () -> Toast.makeText(requireContext(), "Payment methods coming soon!", Toast.LENGTH_SHORT).show());
        row(v, R.id.row_share_app, () -> {
            Intent sh = new Intent(Intent.ACTION_SEND);
            sh.setType("text/plain");
            sh.putExtra(Intent.EXTRA_TEXT, "Check out Nashik Smart Trip Planner! 🌿 The best app for Nashik travel planning.");
            startActivity(Intent.createChooser(sh, "Share App"));
        });
        row(v, R.id.row_help, () -> new AlertDialog.Builder(requireContext()).setTitle("Help & Support").setMessage("Email: support@nashiktripplanner.in\nPhone: +91 253 000 0000\nApp Version: 1.0.0").setPositiveButton("OK", null).show());
        row(v, R.id.row_about, () -> new AlertDialog.Builder(requireContext()).setTitle("About Nashik Smart Trip Planner").setMessage("Version 1.0.0\n\nYour complete Nashik, Maharashtra travel companion.\n\n🌿 India's Wine Capital\n🕉️ Kumbh Mela City\n🏔️ Trekking Paradise\n\n© 2024 Nashik Trip Planner").setPositiveButton("OK", null).show());
        
        // Logout logic
        View btnLogout = v.findViewById(R.id.btn_logout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(b -> new AlertDialog.Builder(requireContext()).setTitle("Logout").setMessage("Are you sure you want to logout?").setPositiveButton("Logout", (d, w) -> {
                sm.logout();
                Intent i = new Intent(requireContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }).setNegativeButton("Cancel", null).show());
        }
    }

    /**
     * Helper to set text to a TextView by ID
     */
    void set(View v, int id, String text) {
        TextView tv = v.findViewById(id);
        if (tv != null) tv.setText(text);
    }

    /**
     * Helper to attach a click listener to a View by ID
     */
    void row(View v, int id, Runnable action) {
        View row = v.findViewById(id);
        if (row != null) row.setOnClickListener(b -> action.run());
    }

    /**
     * Helper to show a simple selection dialog
     */
    void picker(String title, String[] opts, java.util.function.Consumer<String> cb) {
        new AlertDialog.Builder(requireContext()).setTitle(title).setItems(opts, (d, w) -> cb.accept(opts[w])).show();
    }

    @Override 
    public void onActivityResult(int req, int res, @Nullable Intent data) {
        super.onActivityResult(req, res, data);
        if (req == REQ && res == getActivity().RESULT_OK && getView() != null) {
            setupCard(getView());
        }
    }
}
