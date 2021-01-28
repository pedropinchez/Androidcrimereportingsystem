package com.example.androidcrimereportingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Information extends AppCompatActivity {
    EditText id, phone, age, gender, county, subcounty, location, nearest, disability;
    Button submit;
    ProgressBar progress;
    private String userId;

    private boolean isChanged = false;

    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String idno,phoneno,userage,usergender,usercounty,usersub,userlocation,nearestpost,isdisabled,name,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        id = findViewById(R.id.id);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        county = findViewById(R.id.county);
        subcounty = findViewById(R.id.subcounty);
        location = findViewById(R.id.location);
        nearest = findViewById(R.id.nearest);
        disability = findViewById(R.id.disability);
        submit = findViewById(R.id.submit);
        progress = findViewById(R.id.progress);
        getSupportActionBar().setTitle("Personal Info");

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 idno = id.getText().toString().trim();
                 phoneno = phone.getText().toString().trim();
                 userage = age.getText().toString().trim();
                 usergender = gender.getText().toString().trim();
                 usercounty = county.getText().toString().trim();
                 usersub = subcounty.getText().toString().trim();
                userlocation = location.getText().toString().trim();
                nearestpost = nearest.getText().toString().trim();
                isdisabled = disability.getText().toString().trim();
                Bundle extras = getIntent().getExtras();
                if(extras == null ) {
                    name= null;
                    image=null;
                } else {
                    name= extras.getString("name");
                    image=extras.getString("image");
                }

                if (idno != null && phoneno != null && userage != null && usergender != null && usercounty != null && usersub != null &&
                        userlocation != null && nearestpost != null && isdisabled != null) {
                    progress.setVisibility(View.VISIBLE);
                    storeFirestore();
                }

            }
        });
    }
    private void storeFirestore() {

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("image", image);
        userMap.put("id", idno);
        userMap.put("phone", phoneno);
        userMap.put("gender" ,usercounty);
        userMap.put("age", userage);
        userMap.put("county", usercounty);
        userMap.put("subcounty", usersub);
        userMap.put("userlocation", userlocation);
        userMap.put("nearest", nearestpost);
        userMap.put("disability", isdisabled);



        db.collection("USERS").document(userId).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Information.this, "The Account Settings are updated", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(Information.this, SetupActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(Information.this, "Firestore Error: " + errorMessage, Toast.LENGTH_LONG).show();
                }
                progress.setVisibility(View.INVISIBLE);
            }
        });

    }
}