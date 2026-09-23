package com.nashik.tripplanner.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.utils.SessionManager;
public class SignupActivity extends AppCompatActivity {
    private EditText etName,etEmail,etPwd,etConf; private MaterialButton btnCreate;
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b); setContentView(R.layout.activity_signup);
        etName=findViewById(R.id.et_name);etEmail=findViewById(R.id.et_email);etPwd=findViewById(R.id.et_password);etConf=findViewById(R.id.et_confirm_password);btnCreate=findViewById(R.id.btn_create_account);
        View back=findViewById(R.id.iv_back);if(back!=null)back.setOnClickListener(v->onBackPressed());
        View si=findViewById(R.id.tv_sign_in);if(si!=null)si.setOnClickListener(v->onBackPressed());
        if(btnCreate!=null)btnCreate.setOnClickListener(v->doSignup());
    }
    void doSignup(){
        String n=etName.getText().toString().trim(),e=etEmail.getText().toString().trim(),p=etPwd.getText().toString().trim(),c=etConf.getText().toString().trim();
        if(TextUtils.isEmpty(n)){etName.setError("Name required");etName.requestFocus();return;}
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches()){etEmail.setError("Valid email required");etEmail.requestFocus();return;}
        if(p.length()<6){etPwd.setError("Min 6 characters");etPwd.requestFocus();return;}
        if(!p.equals(c)){etConf.setError("Passwords don't match");etConf.requestFocus();return;}
        if(btnCreate!=null){btnCreate.setEnabled(false);btnCreate.setText("Creating…");}
        btnCreate.postDelayed(()->{
            SessionManager sm=new SessionManager(this);sm.saveUser(n,e);sm.setLoggedIn(true);
            Intent i=new Intent(this,MainActivity.class);i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);startActivity(i);
        },1200);
    }
}
