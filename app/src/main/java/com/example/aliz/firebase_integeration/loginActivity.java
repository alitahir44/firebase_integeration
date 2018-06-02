package com.example.aliz.firebase_integeration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    Button btnLogin;
    EditText txtEmail, txtPassword;
    TextView txtSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), UserActivity.class));
        }
        btnLogin=findViewById(R.id.btnLogin);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
        txtSignup=findViewById(R.id.txtSignup);

        btnLogin.setOnClickListener(this);
        txtSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnLogin){
            if(TextUtils.isEmpty(txtEmail.getText().toString().trim())){
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(txtPassword.getText().toString().trim())){
                Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setMessage("Logining..");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString().trim(), txtPassword.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(), UserActivity.class));

                            }
                            else {
                                progressDialog.dismiss();
                                try {
                                    if(hasActiveInternetConnection(loginActivity.this)==false){
                                        Toast.makeText(loginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(loginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){
                                    Toast.makeText(loginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
        }
        else if(v==txtSignup){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

    }
    public boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("Exception", "Error checking internet connection", e);
            }
        } else {
            Log.d("Error", "No network available!");
        }
        return false;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
