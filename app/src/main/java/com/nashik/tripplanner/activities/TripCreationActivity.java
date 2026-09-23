package com.nashik.tripplanner.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.button.MaterialButton;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.data.NashikData;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Step-by-step wizard to create a new Nashik trip.
 * Steps: Destination/Name -> Dates -> Group Size -> Budget
 */
public class TripCreationActivity extends AppCompatActivity {
    private int step = 1; 
    private static final int TOTAL = 4;
    private final View[] stepLayouts = new View[4]; 
    private final View[] circles = new View[4]; 
    private final View[] lines = new View[3];
    private TextView tvProgress;
    private AutoCompleteTextView etName, etDest;
    private EditText etStart, etEnd;
    private int travelers = 2, budget = 8000;
    private String travelType = "couple";
    private final Calendar startCal = Calendar.getInstance(), endCal = Calendar.getInstance();
    private TextView tvTravelers, tvBudget; 
    private MaterialButton btnNext, btnBack;
    private LinearLayout llTypeChips; 
    private View layoutTypeSug;

    @Override 
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_trip_creation);
        
        endCal.add(Calendar.DAY_OF_MONTH, 3);
        
        tvProgress = findViewById(R.id.tv_step_progress);
        stepLayouts[0] = findViewById(R.id.layout_step1);
        stepLayouts[1] = findViewById(R.id.layout_step2);
        stepLayouts[2] = findViewById(R.id.layout_step3);
        stepLayouts[3] = findViewById(R.id.layout_step4);
        
        circles[0] = findViewById(R.id.step_circle_1);
        circles[1] = findViewById(R.id.step_circle_2);
        circles[2] = findViewById(R.id.step_circle_3);
        circles[3] = findViewById(R.id.step_circle_4);
        
        lines[0] = findViewById(R.id.step_line_1);
        lines[1] = findViewById(R.id.step_line_2);
        lines[2] = findViewById(R.id.step_line_3);
        
        etName = findViewById(R.id.et_trip_name);
        etDest = findViewById(R.id.et_destination);
        etStart = findViewById(R.id.et_start_date);
        etEnd = findViewById(R.id.et_end_date);
        tvTravelers = findViewById(R.id.tv_traveler_count);
        tvBudget = findViewById(R.id.tv_budget_value);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
        llTypeChips = findViewById(R.id.ll_type_chips);
        layoutTypeSug = findViewById(R.id.layout_type_suggestions);
        
        View ivBack = findViewById(R.id.iv_back);
        if (ivBack != null) ivBack.setOnClickListener(v -> {
            if (step > 1) showStep(step - 1);
            else onBackPressed();
        });
        
        setupSuggestions();
        setupTravelTypeSelection();
        setupDatePickers();
        setupTravelerCounter();
        setupBudgetControls();
        
        if (btnNext != null) btnNext.setOnClickListener(v -> {
            if (validate()) {
                if (step < TOTAL) showStep(step + 1);
                else proceed();
            }
        });
        if (btnBack != null) btnBack.setOnClickListener(v -> {
            if (step > 1) showStep(step - 1);
            else onBackPressed();
        });
        
        showStep(1);
    }

    void setupSuggestions() {
        List<String> spots = NashikData.getSpotNames();
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, spots);
        if (etName != null) etName.setAdapter(adp);
        if (etDest != null) etDest.setAdapter(adp);
    }

    void setupTravelTypeSelection() {
        int[] cids = {R.id.card_solo, R.id.card_couple, R.id.card_family, R.id.card_group};
        String[] types = {"solo", "couple", "family", "group"};
        for (int k = 0; k < cids.length; k++) {
            final String t = types[k];
            MaterialCardView cv = findViewById(cids[k]);
            if (cv != null) {
                cv.setOnClickListener(v -> {
                    travelType = t;
                    resetTypeUI(cids);
                    selectTypeUI(cv);
                    updateTypeSuggestions();
                });
                if ("couple".equals(t)) selectTypeUI(cv);
            }
        }
    }

    private void resetTypeUI(int[] ids) {
        for (int id : ids) {
            MaterialCardView c = findViewById(id);
            if (c != null) {
                c.setCardBackgroundColor(Color.WHITE);
                c.setStrokeWidth(2);
                c.setStrokeColor(Color.parseColor("#E8ECF0"));
                c.setCardElevation(2f);
            }
        }
    }

    private void selectTypeUI(MaterialCardView cv) {
        cv.setCardBackgroundColor(Color.parseColor("#EBF3FB"));
        cv.setStrokeWidth(6);
        cv.setStrokeColor(getResources().getColor(R.color.primary, null));
        cv.setCardElevation(8f);
    }

    void updateTypeSuggestions() {
        if (llTypeChips == null) return;
        llTypeChips.removeAllViews();
        List<String> suggested = NashikData.getSpotsByType(travelType);
        if (layoutTypeSug != null) layoutTypeSug.setVisibility(suggested.isEmpty() ? View.GONE : View.VISIBLE);
        for (String s : suggested) {
            TextView tv = new TextView(this);
            tv.setText(s); tv.setTextSize(12); tv.setPadding(32, 16, 32, 16);
            tv.setTextColor(getResources().getColor(R.color.primary, null));
            tv.setBackgroundResource(R.drawable.bg_chip_outline);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 12, 0); tv.setLayoutParams(lp);
            tv.setOnClickListener(v -> {
                if (etDest != null) {
                    etDest.setText(s);
                    etDest.setSelection(s.length());
                }
            });
            llTypeChips.addView(tv);
        }
    }

    void setupDatePickers() {
        if (etStart != null) etStart.setOnClickListener(v -> pickDate(true));
        if (etEnd != null) etEnd.setOnClickListener(v -> pickDate(false));
        updateDates();
    }

    void setupTravelerCounter() {
        View tm = findViewById(R.id.btn_traveler_minus), tp = findViewById(R.id.btn_traveler_plus);
        if (tm != null) tm.setOnClickListener(v -> { if (travelers > 1) { travelers--; updateTravelersUI(); } });
        if (tp != null) tp.setOnClickListener(v -> { travelers++; updateTravelersUI(); });
        updateTravelersUI();
    }

    private void updateTravelersUI() { if (tvTravelers != null) tvTravelers.setText(String.valueOf(travelers)); }

    void setupBudgetControls() {
        View bm = findViewById(R.id.btn_budget_minus), bp = findViewById(R.id.btn_budget_plus);
        if (bm != null) bm.setOnClickListener(v -> { if (budget > 2000) { budget -= 1000; updateBudget(); } });
        if (bp != null) bp.setOnClickListener(v -> { if (budget < 50000) { budget += 1000; updateBudget(); } });
        
        int[] pids = {R.id.btn_preset_5k, R.id.btn_preset_10k, R.id.btn_preset_20k, R.id.btn_preset_35k};
        int[] amounts = {5000, 10000, 20000, 35000};
        for (int k = 0; k < pids.length; k++) {
            final int amt = amounts[k];
            View pv = findViewById(pids[k]);
            if (pv != null) pv.setOnClickListener(v -> { budget = amt; updateBudget(); });
        }
        updateBudget();
    }

    void pickDate(boolean start) {
        Calendar c = start ? startCal : endCal;
        new DatePickerDialog(this, (v, y, m, d) -> {
            c.set(y, m, d);
            if (!start && endCal.before(startCal)) {
                endCal.setTime(startCal.getTime());
                endCal.add(Calendar.DAY_OF_MONTH, 1);
            }
            updateDates();
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    void updateDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        if (etStart != null) etStart.setText(sdf.format(startCal.getTime()));
        if (etEnd != null) etEnd.setText(sdf.format(endCal.getTime()));
    }

    void updateBudget() {
        if (tvBudget != null) tvBudget.setText("₹" + String.format(Locale.getDefault(), "%,d", budget));
        TextView h = findViewById(R.id.tv_budget_hotel), f = findViewById(R.id.tv_budget_food), tr = findViewById(R.id.tv_budget_transport), ac = findViewById(R.id.tv_budget_activity);
        String fmt = "₹%,d";
        if (h != null) h.setText(String.format(Locale.getDefault(), fmt, (int) (budget * 0.40)));
        if (f != null) f.setText(String.format(Locale.getDefault(), fmt, (int) (budget * 0.25)));
        if (tr != null) tr.setText(String.format(Locale.getDefault(), fmt, (int) (budget * 0.20)));
        if (ac != null) ac.setText(String.format(Locale.getDefault(), fmt, (int) (budget * 0.15)));
    }

    boolean validate() {
        if (step == 1) {
            String d = etDest != null ? etDest.getText().toString().trim() : "";
            if (TextUtils.isEmpty(d)) { if (etDest != null) etDest.setError("Enter a destination"); return false; }
            if (etName != null && TextUtils.isEmpty(etName.getText())) { etName.setError("Enter a trip name"); etName.requestFocus(); return false; }
        }
        if (step == 2 && endCal.before(startCal)) {
            Toast.makeText(this, "End date must be after start date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void showStep(int s) {
        step = s;
        for (int i = 0; i < stepLayouts.length; i++) {
            if (stepLayouts[i] != null) stepLayouts[i].setVisibility(i + 1 == s ? View.VISIBLE : View.GONE);
        }
        for (int i = 0; i < circles.length; i++) {
            if (circles[i] != null) circles[i].setBackgroundResource(i + 1 <= s ? R.drawable.bg_step_active : R.drawable.bg_step_inactive);
        }
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] != null) lines[i].setBackgroundResource(i + 1 < s ? R.drawable.bg_step_line_active : R.drawable.bg_step_line);
        }
        if (tvProgress != null) tvProgress.setText("Step " + s + " of " + TOTAL);
        if (btnBack != null) btnBack.setVisibility(s > 1 ? View.VISIBLE : View.GONE);
        if (btnNext != null) btnNext.setText(s == TOTAL ? "Continue to Preferences →" : "Next →");
    }

    void proceed() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Intent i = new Intent(this, MoodPreferencesActivity.class);
        i.putExtra("trip_name", etName != null ? etName.getText().toString().trim() : "");
        i.putExtra("destination", etDest != null ? etDest.getText().toString().trim() : "");
        i.putExtra("start_date", sdf.format(startCal.getTime()));
        i.putExtra("end_date", sdf.format(endCal.getTime()));
        i.putExtra("travel_type", travelType);
        i.putExtra("budget", budget);
        i.putExtra("travelers", travelers);
        startActivity(i);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
