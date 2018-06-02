package com.example.aliz.firebase_integeration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtEmail, txtPassword;
    Button btnRegister;
    TextView txtAlready;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), UserActivity.class));
        }
        progressDialog=new ProgressDialog(this);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
        btnRegister=findViewById(R.id.btnRegister);
        txtAlready=findViewById(R.id.txtAlready);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnRegister){
            registerUser();
        }
        if(v==txtAlready){
            startActivity(new Intent(MainActivity.this, loginActivity.class));
        }
    }

    private void registerUser(){
        if(TextUtils.isEmpty(txtEmail.getText().toString().trim())){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(txtPassword.getText().toString().trim())){
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering user..");
        progressDialog.show();
        
        //Toast.makeText(MainActivity.this, "here", Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString().trim(), txtPassword.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(MainActivity.this, "not now", Toast.LENGTH_SHORT).show();
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
