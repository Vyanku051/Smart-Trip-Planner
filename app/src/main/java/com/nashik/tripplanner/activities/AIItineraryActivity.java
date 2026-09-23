package com.nashik.tripplanner.activities;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.models.*;
import com.nashik.tripplanner.utils.TripManager;
import java.util.List;
import java.util.Locale;
/**
 * Activity that displays the AI-generated itinerary.
 * It shows a loading screen while "generating" and then presents a day-by-day plan.
 */
public class AIItineraryActivity extends AppCompatActivity {
    private View layLoad, layContent; 
    private TextView tvTitle, tvMood, tvDays, tvBudget;
    private ImageView ivIcon;
    private RecyclerView rv; 
    private Trip trip; 
    private TripManager tm;

    @Override 
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_ai_itinerary);
        tm = new TripManager(this);
        
        // Initialize UI components
        layLoad = findViewById(R.id.layout_loading);
        layContent = findViewById(R.id.layout_content);
        tvTitle = findViewById(R.id.tv_trip_title);
        tvMood = findViewById(R.id.tv_trip_mood);
        tvDays = findViewById(R.id.tv_days_count);
        tvBudget = findViewById(R.id.tv_budget_info);
        ivIcon = findViewById(R.id.iv_trip_icon);
        
        rv = findViewById(R.id.rv_itinerary_days);
        if (rv != null) rv.setLayoutManager(new LinearLayoutManager(this));
        
        View back = findViewById(R.id.iv_back);
        if (back != null) back.setOnClickListener(v -> onBackPressed());
        
        View regen = findViewById(R.id.btn_regenerate);
        if (regen != null) regen.setOnClickListener(v -> startGen());
        
        startGen();
    }

    /**
     * Starts the generation process with a simulated delay.
     */
    void startGen() {
        if (layLoad != null) layLoad.setVisibility(View.VISIBLE);
        if (layContent != null) layContent.setVisibility(View.GONE);
        // Simulate AI thinking time
        new Handler(Looper.getMainLooper()).postDelayed(this::showResult, 2500);
    }

    /**
     * Fetches the generated trip from NashikData and updates the UI.
     */
    void showResult() {
        Intent i = getIntent();
        // Generate the trip object using data passed from previous steps
        trip = NashikData.generateTrip(
            i.getStringExtra("trip_name"),
            i.getStringExtra("destination"),
            i.getStringExtra("mood"),
            i.getStringExtra("pace"),
            i.getStringExtra("travel_type"),
            i.getStringExtra("start_date"),
            i.getStringExtra("end_date"),
            i.getIntExtra("budget", 8000),
            i.getIntExtra("travelers", 2)
        );

        if (layLoad != null) layLoad.setVisibility(View.GONE);
        if (layContent != null) layContent.setVisibility(View.VISIBLE);
        
        if (trip != null) {
            if (tvTitle != null) tvTitle.setText(trip.getName());
            if (tvMood != null) tvMood.setText(trip.getMoodEmoji() + " " + trip.getMoodDisplayName());
            if (ivIcon != null) ivIcon.setImageResource(NashikData.getMoodIcon(trip.getMood()));
            int days = trip.getItineraryDays() != null ? trip.getItineraryDays().size() : 0;
            if (tvDays != null) tvDays.setText(days + " Days");
            if (tvBudget != null) tvBudget.setText("₹" + String.format(Locale.getDefault(), "%,d", trip.getBudget()));
            if (rv != null && trip.getItineraryDays() != null) rv.setAdapter(new DayAdapter(trip.getItineraryDays()));
        }

        View btnEdit = findViewById(R.id.btn_edit_itinerary);
        if (btnEdit != null) btnEdit.setOnClickListener(v -> openOverview());
        
        View btnAccept = findViewById(R.id.btn_accept_itinerary);
        if (btnAccept != null) {
            btnAccept.setOnClickListener(v -> {
                tm.saveTrip(trip);
                Toast.makeText(this, "✅ Itinerary saved!", Toast.LENGTH_SHORT).show();
                openOverview();
            });
        }
    }

    /**
     * Saves the trip and navigates to the Trip Overview screen.
     */
    void openOverview() {
        if (trip == null) return;
        tm.saveTrip(trip);
        Intent i = new Intent(this, TripOverviewActivity.class);
        i.putExtra("trip_id", trip.getId());
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    static class DayAdapter extends RecyclerView.Adapter<DayAdapter.VH>{
        private final List<ItineraryDay> days; private final boolean[]exp;
        DayAdapter(List<ItineraryDay> d){days=d;exp=new boolean[d.size()];if(d.size()>0)exp[0]=true;}
        @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int v){return new VH(android.view.LayoutInflater.from(p.getContext()).inflate(R.layout.item_itinerary_day,p,false));}
        @Override public void onBindViewHolder(@NonNull VH h,int pos){
            ItineraryDay day=days.get(pos);
            if(h.num!=null)h.num.setText("Day "+day.getDayNumber());
            if(h.title!=null)h.title.setText(day.getTitle());
            if(h.count!=null)h.count.setText(day.getActivities().size()+" activities");
            if(h.chev!=null)h.chev.setText(exp[pos]?"▲":"▼");
            if(h.container!=null)h.container.setVisibility(exp[pos]?View.VISIBLE:View.GONE);
            if(exp[pos]&&h.container!=null){
                h.container.removeAllViews();
                for(ItineraryItem it:day.getActivities()){View row=android.view.LayoutInflater.from(h.itemView.getContext()).inflate(R.layout.item_activity_row,h.container,false);TextView em=row.findViewById(R.id.tv_activity_emoji),tm=row.findViewById(R.id.tv_activity_time),nm=row.findViewById(R.id.tv_activity_name),lc=row.findViewById(R.id.tv_activity_location);if(em!=null)em.setText(it.getEmoji());if(tm!=null)tm.setText(it.getTime());if(nm!=null)nm.setText(it.getName());if(lc!=null)lc.setText(it.getLocation());h.container.addView(row);}
                if(day.getAccommodation()!=null&&!"Checkout".equals(day.getAccommodation())){View stay=android.view.LayoutInflater.from(h.itemView.getContext()).inflate(R.layout.item_stay_row,h.container,false);TextView sn=stay.findViewById(R.id.tv_stay_name),sc=stay.findViewById(R.id.tv_stay_cost);if(sn!=null)sn.setText(day.getAccommodation());if(sc!=null)sc.setText("₹"+String.format(Locale.getDefault(),"%,d",day.getAccommodationCost())+"/night");h.container.addView(stay);}
            }
            h.header.setOnClickListener(v->{int p=h.getAdapterPosition();exp[p]=!exp[p];notifyItemChanged(p);});
        }
        @Override public int getItemCount(){return days.size();}
        static class VH extends RecyclerView.ViewHolder{TextView num,title,count,chev;LinearLayout container;View header;VH(@NonNull View v){super(v);num=v.findViewById(R.id.tv_day_number);title=v.findViewById(R.id.tv_day_title);count=v.findViewById(R.id.tv_activity_count);chev=v.findViewById(R.id.tv_chevron);container=v.findViewById(R.id.activities_container);header=v.findViewById(R.id.day_header);}}
    }
}
