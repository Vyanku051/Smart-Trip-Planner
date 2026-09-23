package com.nashik.tripplanner.activities;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.utils.LocaleHelper;
import com.nashik.tripplanner.utils.SessionManager;
public class LoginActivity extends AppCompatActivity {
    private EditText etEmail,etPwd; private MaterialButton btnLogin; private SessionManager sm;
    @Override protected void attachBaseContext(android.content.Context newBase) {
        sm = new SessionManager(newBase);
        super.attachBaseContext(LocaleHelper.setLocale(newBase, sm.getLanguage()));
    }
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b); setContentView(R.layout.activity_login); sm=new SessionManager(this);
        etEmail=findViewById(R.id.et_email); etPwd=findViewById(R.id.et_password); btnLogin=findViewById(R.id.btn_login);
        View forgot=findViewById(R.id.tv_forgot); if(forgot!=null)forgot.setOnClickListener(v->Toast.makeText(this,"Password reset sent!",Toast.LENGTH_SHORT).show());
        View create=findViewById(R.id.tv_create_account); if(create!=null)create.setOnClickListener(v->{startActivity(new Intent(this,SignupActivity.class));overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);});
        View google=findViewById(R.id.tv_google_login); if(google!=null)google.setOnClickListener(v->Toast.makeText(this,"Add Firebase for Google Sign-In",Toast.LENGTH_SHORT).show());
        View fb=findViewById(R.id.tv_facebook_login); if(fb!=null)fb.setOnClickListener(v->Toast.makeText(this,"Add Facebook SDK for Sign-In",Toast.LENGTH_SHORT).show());
        if(btnLogin!=null)btnLogin.setOnClickListener(v->doLogin());
    }
    void doLogin(){
        String em=etEmail.getText().toString().trim(),pw=etPwd.getText().toString().trim();
        if(TextUtils.isEmpty(em)){etEmail.setError("Email required");etEmail.requestFocus();return;}
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(em).matches()){etEmail.setError("Valid email required");etEmail.requestFocus();return;}
        if(pw.length()<6){etPwd.setError("Min 6 characters");etPwd.requestFocus();return;}
        if(btnLogin!=null){btnLogin.setEnabled(false);btnLogin.setText("Signing in…");}
        View progress=findViewById(R.id.progress_login); if(progress!=null)progress.setVisibility(View.VISIBLE);
        btnLogin.postDelayed(()->{
            String name=em.split("@")[0];name=Character.toUpperCase(name.charAt(0))+name.substring(1);
            sm.saveUser(name,em);sm.setLoggedIn(true);
            Intent i=new Intent(this,MainActivity.class);i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        },1400);
    }
}
