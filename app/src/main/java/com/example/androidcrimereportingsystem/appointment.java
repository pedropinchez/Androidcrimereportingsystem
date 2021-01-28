package com.example.androidcrimereportingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidcrimereportingsystem.R;
import com.example.androidcrimereportingsystem.Utils.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class appointment extends AppCompatActivity {
    private ImageView image;
    private EditText time, name, details,type;
    private Button submit;
    private CheckBox check;

    private Uri postImageUri = null;
    private Spinner spinner, spinner1;
    public static final int PICK_IMAGE = 1;

    private ProgressBar addPostProgress;

    private StorageReference storageReference;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;

    private String currentUserId, location, types,idno,phoneno,ageno,genders;

    SharedPref sharedPref;;
    private ProgressDialog pdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        // Firebase init
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        sharedPref = new SharedPref(this);
        // initialize view
        currentUserId = mAuth.getCurrentUser().getUid();
        type = findViewById(R.id.type);
        time = findViewById(R.id.time);
       name = findViewById(R.id.name);
        details = findViewById(R.id.details);
        submit = findViewById(R.id.submit);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String times = time.getText().toString();
                final String types = type.getText().toString();
                final String names =name.getText().toString();
                final String detail = details.getText().toString();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userId = mAuth.getCurrentUser().getUid();
                db.collection("USERS").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                 idno = task.getResult().getString("id");
                                 phoneno = task.getResult().getString("phone");
                                ageno = task.getResult().getString("age");
                                genders = task.getResult().getString("gender");


                            } else {
                                //String errorMessage = task.getException().getMessage();

                            }

                        }
                    }
                });

                if (!TextUtils.isEmpty(times) && !TextUtils.isEmpty(types) && !TextUtils.isEmpty(detail) && !TextUtils.isEmpty(names) ) {
                    submit.setVisibility(View.VISIBLE);

                    String date = df.format(c);
                    long tsLong = System.currentTimeMillis() ;
                    String ts = String.valueOf(tsLong);
                    pdialog = new ProgressDialog(appointment.this);
                    pdialog.setMessage("Please wait...");
                    pdialog.setIndeterminate(true);
                    pdialog.setCanceledOnTouchOutside(false);
                    pdialog.setCancelable(false);
                    pdialog.show();
                    Map<String, Object> post = new HashMap<>();


                    final String userimage=sharedPref.getimage();
                    final  String username=sharedPref.getname();
                    post.put("timestamp", ts);
                    post.put("date", date);
                    post.put("time",times);
                    post.put("type", types) ;
                    post.put("details", detail);
                    post.put("idno",idno);
                    post.put("age", ageno);
                    post.put("name",names);
                    post.put("gender", genders);



                    FirebaseFirestore.getInstance().collection("Appointment").document()
                            .set(post)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(appointment.this, "Appointment Added succesfully,you will be contacted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(appointment.this,HomeActivity.class));
                                    finish();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
                                    finish();


                                }
                            });



                } else {
                    Toast.makeText(appointment.this, "Text Fields and Image must not be empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

