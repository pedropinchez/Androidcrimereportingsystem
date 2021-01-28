package com.example.androidcrimereportingsystem.Login;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidcrimereportingsystem.HomeActivity;
import com.example.androidcrimereportingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    EditText password, email;
    Button login;
    TextView create, txt_forgot;
    ProgressDialog pdialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        email.requestFocus();
        password.requestFocus();
        login = findViewById(R.id.login);
        create = findViewById(R.id.createAcc);
        txt_forgot = findViewById(R.id.txt_forgot);
        mAuth = FirebaseAuth.getInstance();
        txt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), forgot_pass.class));
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), signup.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();


            }
        });
    }

    private void login(){
        String Email=email.getText().toString();
        String Password=password.getText().toString();
        if(!TextUtils.isEmpty(Email)&& !TextUtils.isEmpty(Password)) {

            mAuth.signInWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            }
                            else if(!IsConnected()) {
                                Toast.makeText(login.this, "check your network", Toast.LENGTH_SHORT).show();
                            }

                            else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(login.this, "Authentication failed.check password or email",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        else{
            Toast.makeText(login.this,"empty inputs",Toast.LENGTH_SHORT).show();
        }
    }

    public void updateUI(FirebaseUser account) {
        if (account != null) {
            Toast.makeText(this, "You Signed In successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "You arent registered,kindly sign up", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),signup.class));

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            startActivity(new Intent(login.this, HomeActivity.class));

        }
       
    }

    private boolean checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()) {
            // user is verified, so you can finish this activity or send user to activity which you want.
            startActivity(new Intent(this, HomeActivity.class));

            Toast.makeText(login.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            Toast.makeText(this, "email not verified", Toast.LENGTH_SHORT).show();
           // FirebaseAuth.getInstance().signOut();
            pdialog.dismiss();


            //restart this activity

        }
        return true;
    }
    private boolean IsConnected() {

        try {


            ConnectivityManager connectivityManager = (ConnectivityManager) login.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        } catch (Exception ignored) {
            return true;
        }
    }
}
