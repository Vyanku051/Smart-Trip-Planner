package com.nashik.tripplanner.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.button.MaterialButton;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.utils.SessionManager;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import java.util.List;
public class OnboardingActivity extends AppCompatActivity {
    private ViewPager2 vp; private MaterialButton btnNext; private TextView tvSkip;
    private List<NashikData.OnboardSlide> slides; private int cur=0;
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b); setContentView(R.layout.activity_onboarding);
        slides=NashikData.getSlides(); vp=findViewById(R.id.view_pager);
        DotsIndicator dots=findViewById(R.id.dots_indicator); btnNext=findViewById(R.id.btn_next); tvSkip=findViewById(R.id.tv_skip);
        vp.setAdapter(new OA(slides));
        if(dots!=null)dots.attachTo(vp);
        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){@Override public void onPageSelected(int p){cur=p;updateBtn();}});
        btnNext.setOnClickListener(v->{if(cur<slides.size()-1)vp.setCurrentItem(cur+1,true);else goLogin();});
        if(tvSkip!=null)tvSkip.setOnClickListener(v->goLogin());
    }
    void updateBtn(){if(cur==slides.size()-1){btnNext.setText("Get Started 🚀");if(tvSkip!=null)tvSkip.setVisibility(View.GONE);}else{btnNext.setText("Next →");if(tvSkip!=null)tvSkip.setVisibility(View.VISIBLE);}}
    void goLogin(){new SessionManager(this).setOnboardingDone(true);startActivity(new Intent(this,LoginActivity.class));finish();}
    static class OA extends RecyclerView.Adapter<OA.VH>{
        List<NashikData.OnboardSlide> s; OA(List<NashikData.OnboardSlide> s){this.s=s;}
        @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int t){return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_onboarding_slide,p,false));}
        @Override public void onBindViewHolder(@NonNull VH h,int pos){NashikData.OnboardSlide sl=s.get(pos);h.em.setText(sl.emoji);h.ti.setText(sl.title);h.de.setText(sl.desc);}
        @Override public int getItemCount(){return s.size();}
        static class VH extends RecyclerView.ViewHolder{TextView em,ti,de;VH(View v){super(v);em=v.findViewById(R.id.tv_ob_emoji);ti=v.findViewById(R.id.tv_ob_title);de=v.findViewById(R.id.tv_ob_desc);}}
    }
}
