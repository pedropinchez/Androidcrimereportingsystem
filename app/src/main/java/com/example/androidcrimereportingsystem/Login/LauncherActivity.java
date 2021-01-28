package com.example.androidcrimereportingsystem.Login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.androidcrimereportingsystem.R;

public class LauncherActivity extends Activity implements Animation.AnimationListener {
         ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_launcher);

        Animation animation =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);

        ImageView logo = findViewById(R.id.logo);




        animation.setAnimationListener(this);


        logo.startAnimation(animation);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        Handler b = new Handler();
        b.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent f = new Intent(LauncherActivity.this, Welcome.class);
                startActivity(f);
                finish();
            }
        }, 1000);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
