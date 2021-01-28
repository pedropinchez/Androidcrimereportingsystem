package com.example.androidcrimereportingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;



import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Details extends AppCompatActivity {
    TextView ttle,cont,time,date;
    ImageView img;

    ScrollableGridView simpleGridView;
    Intent parse;
    Intent bund;
    String tt,cn,tm,pm,jc;
    private ArrayList<MyListData> listData;
    Button telegram, whatsapp;
    TextView template;
    private static Context context;
    Toolbar toolbar;
    Button website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();


    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
