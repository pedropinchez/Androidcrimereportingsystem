package com.example.androidcrimereportingsystem.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.androidcrimereportingsystem.HomeActivity;
import com.example.androidcrimereportingsystem.PrivacyActivity;
import com.example.androidcrimereportingsystem.R;
import com.google.firebase.auth.FirebaseAuth;

public class Welcome extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

    }


    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            startActivity(new Intent(Welcome.this, HomeActivity.class));

        }
    }

    public void Proceed(View view) {


        startActivity(new Intent(Welcome.this, PrivacyActivity.class));

    }
}
