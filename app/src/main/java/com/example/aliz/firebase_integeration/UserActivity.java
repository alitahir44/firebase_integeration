package com.example.aliz.firebase_integeration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogout;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, loginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        btnLogout=findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnLogout){
            progressDialog.setMessage("Signing out");
            progressDialog.show();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(UserActivity.this, loginActivity.class));
            progressDialog.dismiss();
        }
    }
}
