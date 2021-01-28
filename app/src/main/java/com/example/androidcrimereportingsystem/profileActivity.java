package com.example.androidcrimereportingsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileActivity extends AppCompatActivity {
    private CircleImageView profile_image;
    private TextView profile_name;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String userId;
    private String postId;
    TextView email,id,phone,age,gender,county,subcounty,location,nearest,disability;
    private Uri mainImageURI = null;
    private RecyclerView post_list;
    FloatingActionButton add;
    Button edit;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile_image =findViewById(R.id.profile_image);
        profile_name = findViewById(R.id.profile_name);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);
        email=findViewById(R.id.profile_email);
        id =findViewById(R.id.profile_id);
        age =findViewById(R.id.profile_age);
        phone =findViewById(R.id.profile_phone);
        gender = findViewById(R.id.profile_gender);
        county = findViewById(R.id.profile_county);
        subcounty=findViewById(R.id.profile_sub);
        location = findViewById(R.id.profile_location);
        nearest =findViewById(R.id.profile_nearest);
        disability=findViewById(R.id.profile_disability);
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
                        profile_name.setText(name);

                        mainImageURI = Uri.parse(image);

                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.default_profile);

                        Glide.with(profileActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(profile_image);

                    }
                } else {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(profileActivity.this, "Firestore Load Error: " + errorMessage, Toast.LENGTH_LONG).show();
                }

            }
        });
        db.collection("USERS").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {

                        String idno = task.getResult().getString("id");
                        String phoneno = task.getResult().getString("phone");
                        String ageno = task.getResult().getString("age");
                        String genders = task.getResult().getString("gender");
                        String countys = task.getResult().getString("county");
                        String subcountys = task.getResult().getString("subcounty");
                        String locations = task.getResult().getString("userlocation");
                        String nearests = task.getResult().getString("nearest");
                        String disabilitys = task.getResult().getString("disability");

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String profemail = user.getEmail();
                        email.setText(profemail);
                        id.setText("IDNO:"+idno);
                        phone.setText( "PhoneNo:"+phoneno);
                        age.setText("AGE:"+ageno);
                        gender.setText("Gender:"+genders);
                        county.setText("County:"+countys);
                        subcounty.setText("Sub-County:"+subcountys);
                        location.setText("Location:"+locations);
                        nearest.setText("Police Station:"+nearests);
                        disability.setText("Disability:"+disabilitys);
                    }
                } else {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(profileActivity.this, "Firestore Load Error: " + errorMessage, Toast.LENGTH_LONG).show();
                }

            }
        });

        db = FirebaseFirestore.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent settings = new Intent(profileActivity.this,Information.class);
                settings.putExtra("user", user_id);
                startActivity(settings);

            }
        });

    }
}