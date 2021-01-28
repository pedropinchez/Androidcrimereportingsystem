package com.example.androidcrimereportingsystem;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.request.RequestOptions;
import com.example.androidcrimereportingsystem.Login.login;
import com.example.androidcrimereportingsystem.lost.FirstFragment;
import com.example.androidcrimereportingsystem.lost.LostAndFound;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    LinearLayout report,reported,proceedings,profile,aboutas,contactus,lost, found, claimed,appointment;
    TextView top;
    private Uri mainImageURI = null;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;
    Fragment FirstFragment,SecondFragment,ThirdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        report=findViewById(R.id.report);
        reported=findViewById(R.id.reported);
       appointment=findViewById(R.id.appointment);
        profile=findViewById(R.id.profile);
        aboutas=findViewById(R.id.aboutus);
        contactus=findViewById(R.id.contactus);
        lost=findViewById(R.id.lost);
        found =findViewById(R.id.found);
        claimed =findViewById(R.id.claimed);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        db.collection("USER").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        String name = task.getResult().getString("name");
                        String image = task.getResult().getString("image");
                        if (name==null || image==null ) {
                            startActivity(new Intent(getApplicationContext(),SetupActivity.class));
                        }
                        else {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String profemail = user.getEmail();
                            mainImageURI = Uri.parse(image);

                            RequestOptions placeholderRequest = new RequestOptions();
                            placeholderRequest.placeholder(R.drawable.default_profile);

                        }  }
                } else {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(HomeActivity.this, "Firestore Load Error: " + errorMessage, Toast.LENGTH_LONG).show();
                }

            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ReportActivity.class));
            }
        });
        reported.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ReportedActivity.class));
            }
        });
        aboutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), about_us.class));
            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Contact_us.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),profileActivity.class));
            }
        });
        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LostActivity.class));
            }
        });
        found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),FoundActivity.class));
            }
        });
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),appointment.class));
            }
        });
        claimed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Claimed.class));
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            

            case R.id.action_logout:
                logout();
                break;

            case R.id.action_profile:

                startActivity(new Intent(getApplicationContext(), SetupActivity .class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        mAuth.signOut();
        navigateToLogin();
        SharedPreferences preferences =getSharedPreferences("crimereporting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        finish();
    }

    private void navigateToLogin() {
        Intent loginIntent = new Intent(HomeActivity.this, login.class);
        startActivity(loginIntent);
        finish();
    }

}
