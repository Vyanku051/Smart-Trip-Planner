package com.nashik.tripplanner.activities;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.*;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.utils.LocaleHelper;
import com.nashik.tripplanner.utils.SessionManager;
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    @Override protected void attachBaseContext(android.content.Context newBase) {
        SessionManager sm = new SessionManager(newBase);
        super.attachBaseContext(LocaleHelper.setLocale(newBase, sm.getLanguage()));
    }
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        TextView logo=findViewById(R.id.tv_logo),title=findViewById(R.id.tv_title),tag=findViewById(R.id.tv_tagline);
        if(logo!=null){ScaleAnimation s=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,.5f,Animation.RELATIVE_TO_SELF,.5f);s.setDuration(700);AlphaAnimation a=new AlphaAnimation(0,1);a.setDuration(700);AnimationSet as=new AnimationSet(true);as.addAnimation(s);as.addAnimation(a);logo.startAnimation(as);}
        if(title!=null){AlphaAnimation a=new AlphaAnimation(0,1);a.setStartOffset(600);a.setDuration(500);a.setFillAfter(true);title.startAnimation(a);}
        if(tag!=null){AlphaAnimation a=new AlphaAnimation(0,1);a.setStartOffset(900);a.setDuration(500);a.setFillAfter(true);tag.startAnimation(a);}
        SessionManager sm=new SessionManager(this);
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            Intent i;
            if(!sm.isOnboardingDone())i=new Intent(this,OnboardingActivity.class);
            else if(!sm.isLoggedIn())i=new Intent(this,LoginActivity.class);
            else i=new Intent(this,MainActivity.class);
            startActivity(i);overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);finish();
        },2800);
    }
}
