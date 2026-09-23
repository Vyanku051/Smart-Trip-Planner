package com.nashik.tripplanner.activities;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.models.*;
import com.nashik.tripplanner.utils.TripManager;
import java.util.*;
public class TripOverviewActivity extends AppCompatActivity {
    private Trip trip; private TripManager tm;
    private View tabItin,tabBudget,tabPacking;
    private ImageView ivIcon;
    private LinearLayout packCont; private TextView tvPackProg; private ProgressBar packBar;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_trip_overview);
        tm=new TripManager(this);
        String id=getIntent().getStringExtra("trip_id");
        if(id!=null)trip=tm.getById(id);
        if(trip==null&&!tm.getAll().isEmpty())trip=tm.getAll().get(0);
        if(trip==null){finish();return;}
        initViews();populateHero();setupTabs();showTab(0);
    }
    void initViews(){
        View back=findViewById(R.id.iv_back);if(back!=null)back.setOnClickListener(v->onBackPressed());
        tabItin=findViewById(R.id.tab_content_itinerary);tabBudget=findViewById(R.id.tab_content_budget);tabPacking=findViewById(R.id.tab_content_packing);
        ivIcon=findViewById(R.id.iv_trip_icon);
        if(tabPacking!=null){packCont=tabPacking.findViewById(R.id.packing_items_container);tvPackProg=tabPacking.findViewById(R.id.tv_packing_progress);packBar=tabPacking.findViewById(R.id.progress_packing);}
        View btnShare=findViewById(R.id.btn_share_trip);if(btnShare!=null)btnShare.setOnClickListener(v->shareTrip());
        View btnExport=findViewById(R.id.btn_export_pdf);if(btnExport!=null)btnExport.setOnClickListener(v->Toast.makeText(this,"PDF export coming soon!",Toast.LENGTH_SHORT).show());
        View btnMap=findViewById(R.id.btn_view_map);if(btnMap!=null)btnMap.setOnClickListener(v->{Intent i=new Intent(this,MainActivity.class);i.putExtra(MainActivity.EXTRA_TAB,MainActivity.TAB_MAP);i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);startActivity(i);});
        View ivHome=findViewById(R.id.iv_home);if(ivHome!=null)ivHome.setOnClickListener(v->{Intent i=new Intent(this,MainActivity.class);i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);startActivity(i);finish();});
    }
    void populateHero(){
        TextView tvName=findViewById(R.id.tv_trip_name),tvDates=findViewById(R.id.tv_trip_dates);
        View hero=findViewById(R.id.hero_card);
        if(tvName!=null)tvName.setText(trip.getName());
        if(tvDates!=null)tvDates.setText("📅 "+trip.getTravelDuration());
        if(ivIcon!=null)ivIcon.setImageResource(NashikData.getMoodIcon(trip.getMood()));
        if(hero!=null){try{hero.setBackgroundColor(Color.parseColor(trip.getColorHex()));}catch(Exception ignored){}}
    }
    void setupTabs(){
        TabLayout tl=findViewById(R.id.tab_layout);if(tl==null)return;
        tl.addTab(tl.newTab().setText("📅 Itinerary"));
        tl.addTab(tl.newTab().setText("💰 Budget"));
        tl.addTab(tl.newTab().setText("🎒 Packing"));
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override public void onTabSelected(TabLayout.Tab t){showTab(t.getPosition());}
            @Override public void onTabUnselected(TabLayout.Tab t){}
            @Override public void onTabReselected(TabLayout.Tab t){}
        });
    }
    void showTab(int pos){
        if(tabItin!=null)tabItin.setVisibility(pos==0?View.VISIBLE:View.GONE);
        if(tabBudget!=null)tabBudget.setVisibility(pos==1?View.VISIBLE:View.GONE);
        if(tabPacking!=null)tabPacking.setVisibility(pos==2?View.VISIBLE:View.GONE);
        if(pos==0)populateItin();else if(pos==1)populateBudget();else if(pos==2)populatePacking();
    }
    void populateItin(){
        if(tabItin==null||trip.getItineraryDays()==null)return;
        LinearLayout cont=tabItin.findViewById(R.id.itinerary_container);if(cont==null)return;
        cont.removeAllViews();
        for(ItineraryDay day:trip.getItineraryDays()){
            View dv=LayoutInflater.from(this).inflate(R.layout.item_itinerary_day_simple,cont,false);
            TextView hdr=dv.findViewById(R.id.tv_day_header);if(hdr!=null)hdr.setText("Day "+day.getDayNumber()+" — "+day.getTitle());
            LinearLayout acts=dv.findViewById(R.id.activities_simple_container);
            if(acts!=null){for(ItineraryItem it:day.getActivities()){View row=LayoutInflater.from(this).inflate(R.layout.item_activity_simple,acts,false);TextView em=row.findViewById(R.id.tv_act_emoji),tm=row.findViewById(R.id.tv_act_time),nm=row.findViewById(R.id.tv_act_name),lc=row.findViewById(R.id.tv_act_location);if(em!=null)em.setText(it.getEmoji());if(tm!=null)tm.setText(it.getTime());if(nm!=null)nm.setText(it.getName());if(lc!=null)lc.setText(it.getLocation());acts.addView(row);}}
            cont.addView(dv);
        }
        TextView tvNotes=tabItin.findViewById(R.id.tv_trip_notes);if(tvNotes!=null&&trip.getNotes()!=null)tvNotes.setText(trip.getNotes());
    }
    void populateBudget(){
        if(tabBudget==null)return;
        BudgetBreakdown bd=trip.getBudgetBreakdown();if(bd==null)bd=new BudgetBreakdown(trip.getBudget(), trip.getDayCount(), false);
        String fmt="₹%,d"; Locale l=Locale.getDefault();
        
        // Hide accommodation if 0 (1-day trip)
        View layAcc = tabBudget.findViewById(R.id.layout_budget_accommodation);
        if(layAcc != null) layAcc.setVisibility(bd.getAccommodation() > 0 ? View.VISIBLE : View.GONE);
        
        // Hide activities if 0 (free spots)
        View layAct = tabBudget.findViewById(R.id.layout_budget_activities);
        if(layAct != null) layAct.setVisibility(bd.getActivities() > 0 ? View.VISIBLE : View.GONE);

        set(tabBudget,R.id.tv_estimated_total,String.format(l,"₹%,d",bd.getTotalEstimated()));
        set(tabBudget,R.id.tv_actual_total,String.format(l,"₹%,d",bd.getTotalActual()));
        set(tabBudget,R.id.tv_under_budget_amount,bd.isUnderBudget()?"📉 Under Budget by ₹"+String.format(l,"%,d",bd.getSavings()):"📈 Over Budget");
        set(tabBudget,R.id.tv_hotel_est,String.format(l,"₹%,d est.",bd.getAccommodation()));
        set(tabBudget,R.id.tv_hotel_act,String.format(l,"₹%,d act.",(int)(bd.getAccommodation()*0.9)));
        set(tabBudget,R.id.tv_transport_est,String.format(l,"₹%,d est.",bd.getTransport()));
        set(tabBudget,R.id.tv_transport_act,String.format(l,"₹%,d act.",(int)(bd.getTransport()*0.9)));
        set(tabBudget,R.id.tv_food_est,String.format(l,"₹%,d est.",bd.getFood()));
        set(tabBudget,R.id.tv_food_act,String.format(l,"₹%,d act.",(int)(bd.getFood()*1.05)));
        set(tabBudget,R.id.tv_activity_est,String.format(l,"₹%,d est.",bd.getActivities()));
        set(tabBudget,R.id.tv_activity_act,String.format(l,"₹%,d act.",(int)(bd.getActivities()*0.93)));
    }
    void populatePacking(){
        if(packCont==null||trip.getPackingList()==null)return;
        packCont.removeAllViews();
        List<PackingItem> items=trip.getPackingList().getItems();
        updatePackProgress(items);
        for(PackingItem item:items){
            View row=LayoutInflater.from(this).inflate(R.layout.item_packing,packCont,false);
            CheckBox cb=row.findViewById(R.id.cb_packing);TextView tv=row.findViewById(R.id.tv_packing_name);
            if(tv!=null)tv.setText(item.getName());
            if(cb!=null){cb.setChecked(item.isPacked());applyStrike(tv,item.isPacked());cb.setOnCheckedChangeListener((btn,on)->{item.setPacked(on);applyStrike(tv,on);tm.saveTrip(trip);updatePackProgress(items);});}
            packCont.addView(row);
        }
        View btnAdd=tabPacking.findViewById(R.id.btn_add_packing);
        if(btnAdd!=null)btnAdd.setOnClickListener(v->addPackDialog(items));
    }
    void applyStrike(TextView tv,boolean on){if(tv==null)return;if(on){tv.setPaintFlags(tv.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);tv.setTextColor(Color.parseColor("#9E9E9E"));}else{tv.setPaintFlags(tv.getPaintFlags()&~Paint.STRIKE_THRU_TEXT_FLAG);tv.setTextColor(Color.parseColor("#1A2C42"));}}
    void updatePackProgress(List<PackingItem> items){int packed=0;for(PackingItem i:items)if(i.isPacked())packed++;int total=items.size();if(tvPackProg!=null)tvPackProg.setText(packed+"/"+total+" items packed");if(packBar!=null){packBar.setMax(total>0?total:1);packBar.setProgress(packed);}}
    void addPackDialog(List<PackingItem> items){EditText input=new EditText(this);input.setHint("e.g., Mosquito repellent");input.setPadding(48,24,48,24);new AlertDialog.Builder(this).setTitle("Add Packing Item").setView(input).setPositiveButton("Add",(d,w)->{String n=input.getText().toString().trim();if(!n.isEmpty()){trip.getPackingList().addItem(new PackingItem("pk_"+System.currentTimeMillis(),n,false));tm.saveTrip(trip);populatePacking();}}).setNegativeButton("Cancel",null).show();}
    void set(View parent,int id,String text){TextView tv=parent.findViewById(id);if(tv!=null)tv.setText(text);}
    void shareTrip(){String text="🌿 "+trip.getName()+"\n📅 "+trip.getTravelDuration()+"\n"+trip.getMoodEmoji()+" "+trip.getMoodDisplayName()+"\n💰 Budget: ₹"+String.format(Locale.getDefault(),"%,d",trip.getBudget())+"\n\nPlanned with Nashik Smart Trip Planner 🚀";Intent share=new Intent(Intent.ACTION_SEND);share.setType("text/plain");share.putExtra(Intent.EXTRA_TEXT,text);startActivity(Intent.createChooser(share,"Share Trip"));}
}
