package com.nashik.tripplanner.activities;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.button.MaterialButton;
import com.nashik.tripplanner.R;
public class MoodPreferencesActivity extends AppCompatActivity {
    private String mood=null,pace="moderate";
    private final String[]moodIds={"pilgrimage","wine_tour","adventure","heritage","nature","family"};
    private final String[]moodColors={"#FF6B35","#8E44AD","#27AE60","#E8A020","#2980B9","#E74C3C"};
    private MaterialCardView[]moodCards; private MaterialButton btnGen; private TextView tvHint;
    private final int[]ivMoodIds={R.id.iv_mood_pilgrimage,R.id.iv_mood_wine,R.id.iv_mood_adventure,R.id.iv_mood_heritage,R.id.iv_mood_nature,R.id.iv_mood_family};

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_mood_preferences);
        View back=findViewById(R.id.iv_back);if(back!=null)back.setOnClickListener(v->onBackPressed());
        int[]cids={R.id.card_pilgrimage,R.id.card_wine_tour,R.id.card_adventure,R.id.card_heritage,R.id.card_nature,R.id.card_family};
        moodCards=new MaterialCardView[cids.length];
        for(int k=0;k<cids.length;k++){moodCards[k]=findViewById(cids[k]);final int idx=k;if(moodCards[k]!=null)moodCards[k].setOnClickListener(v->selectMood(idx));}
        int[]pids={R.id.card_pace_relaxed,R.id.card_pace_moderate,R.id.card_pace_packed};
        String[]paces={"relaxed","moderate","packed"};
        for(int k=0;k<pids.length;k++){MaterialCardView cv=findViewById(pids[k]);final String pc=paces[k];if(cv!=null){cv.setOnClickListener(v->selectPace(pc));}}
        selectPace("moderate");
        btnGen=findViewById(R.id.btn_generate_itinerary);tvHint=findViewById(R.id.tv_select_hint);
        if(btnGen!=null){btnGen.setEnabled(false);btnGen.setAlpha(0.5f);btnGen.setOnClickListener(v->{if(mood==null){Toast.makeText(this,"Please select a trip goal first",Toast.LENGTH_SHORT).show();return;}goItinerary();});}
    }
    void selectMood(int idx){
        for(int i=0;i<moodCards.length;i++){
            MaterialCardView c=moodCards[i];
            if(c!=null){
                c.setCardBackgroundColor(Color.WHITE);
                c.setStrokeColor(Color.parseColor("#E8ECF0"));
            }
            ImageView iv=findViewById(ivMoodIds[i]);
            if(iv!=null)iv.setColorFilter(getResources().getColor(R.color.text_secondary,null));
        }
        mood=moodIds[idx];
        if(moodCards[idx]!=null){
            moodCards[idx].setCardBackgroundColor(Color.parseColor(moodColors[idx]+"18"));
            moodCards[idx].setStrokeColor(Color.parseColor(moodColors[idx]));
        }
        ImageView selIv=findViewById(ivMoodIds[idx]);
        if(selIv!=null)selIv.setColorFilter(Color.parseColor(moodColors[idx]));
        
        if(btnGen!=null){btnGen.setEnabled(true);btnGen.setAlpha(1f);}
        if(tvHint!=null)tvHint.setVisibility(View.GONE);
    }
    void selectPace(String p){
        pace = p;
        int[] pids = {R.id.card_pace_relaxed, R.id.card_pace_moderate, R.id.card_pace_packed};
        String[] paces = {"relaxed", "moderate", "packed"};
        for (int k = 0; k < pids.length; k++) {
            MaterialCardView cv = findViewById(pids[k]);
            if (cv != null) {
                boolean isSel = paces[k].equals(p);
                cv.setCardBackgroundColor(isSel ? Color.parseColor("#EBF3FB") : Color.WHITE);
                cv.setStrokeColor(isSel ? getResources().getColor(R.color.primary, null) : Color.parseColor("#E8ECF0"));
            }
        }
    }
    void goItinerary(){if(btnGen!=null){btnGen.setEnabled(false);btnGen.setText("Generating…");}Intent i=new Intent(this,AIItineraryActivity.class);i.putExtra("trip_name",getIntent().getStringExtra("trip_name"));i.putExtra("destination",getIntent().getStringExtra("destination"));i.putExtra("start_date",getIntent().getStringExtra("start_date"));i.putExtra("end_date",getIntent().getStringExtra("end_date"));i.putExtra("travel_type",getIntent().getStringExtra("travel_type"));i.putExtra("budget",getIntent().getIntExtra("budget",8000));i.putExtra("travelers",getIntent().getIntExtra("travelers",2));i.putExtra("mood",mood);i.putExtra("pace",pace);startActivity(i);overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);}
}
