package com.nashik.tripplanner.activities;
import android.os.Bundle;
import android.view.View;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.utils.SessionManager;
public class EditProfileActivity extends AppCompatActivity {
    private EditText etName,etEmail; private SessionManager sm;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_edit_profile);
        sm=new SessionManager(this);etName=findViewById(R.id.et_name);etEmail=findViewById(R.id.et_email);
        if(etName!=null)etName.setText(sm.getUserName());if(etEmail!=null)etEmail.setText(sm.getUserEmail());
        View back=findViewById(R.id.iv_back);if(back!=null)back.setOnClickListener(v->onBackPressed());
        MaterialButton btnSave=findViewById(R.id.btn_save);if(btnSave!=null)btnSave.setOnClickListener(v->save());
    }
    void save(){String n=etName!=null?etName.getText().toString().trim():"";if(TextUtils.isEmpty(n)){if(etName!=null)etName.setError("Name required");return;}sm.saveUser(n,etEmail!=null?etEmail.getText().toString().trim():"");Toast.makeText(this,"Profile updated!",Toast.LENGTH_SHORT).show();setResult(RESULT_OK);finish();}
}
