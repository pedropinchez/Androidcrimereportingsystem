package com.example.androidcrimereportingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidcrimereportingsystem.Login.login;

public class PrivacyActivity extends AppCompatActivity {
     CheckBox check;
     Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        check=findViewById(R.id.check);
        start=findViewById(R.id.start);
        //check if terms button is checked to enable user to proceed
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check.isChecked()){
                    startActivity(new Intent(getApplicationContext(), login.class));
                }
            }
        });

    }
}
