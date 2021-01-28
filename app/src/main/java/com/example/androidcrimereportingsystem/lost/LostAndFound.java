package com.example.androidcrimereportingsystem.lost;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.androidcrimereportingsystem.R;
import com.example.androidcrimereportingsystem.Utils.SharedPref;
import com.google.android.material.tabs.TabLayout;

public class LostAndFound extends AppCompatActivity {

    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabb);

        TabLayout tabLayout = findViewById(R.id.myTabs);
        tabLayout.addTab(tabLayout.newTab().setText("Found Items"));
        tabLayout.addTab(tabLayout.newTab().setText("Post Item"));
        tabLayout.addTab(tabLayout.newTab().setText("Recently Claimed"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        SharedPref shared=new SharedPref(this);
        shared.setIsGuest(false);


        final ViewPager viewPager =findViewById(R.id.mypage);
        PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (alertDialog != null){
            alertDialog = null;
        }
    }


}

