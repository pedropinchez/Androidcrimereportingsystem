package com.example.androidcrimereportingsystem;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.androidcrimereportingsystem.Utils.SharedPref;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class ReportActivity extends AppCompatActivity {


    private ImageView image;
    private EditText time, crime, details;
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

    private String currentUserId, location, types,pimage,name;

    private Bitmap compressedImageFile;
    private ProgressDialog pdialog;

    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Firebase init
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        sharedPref = new SharedPref(this);
        // initialize view
        spinner = findViewById(R.id.location);
        spinner1 = findViewById(R.id.type);
        currentUserId = mAuth.getCurrentUser().getUid();
        image = findViewById(R.id.image);
        time = findViewById(R.id.time);
        crime = findViewById(R.id.crime);
        details = findViewById(R.id.details);
        submit = findViewById(R.id.submit);
        check = findViewById(R.id.check);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.locations, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View v,
                                       int position, long id) {

                location = spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.v("NothingSelected Item",
                        "" + spinner.getSelectedItem());
            }
        });
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View v,
                                       int position, long id) {

                types = spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.v("NothingSelected Item",
                        "" + spinner1.getSelectedItem());
            }
        });
        //submit button to upload the data
        submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String times = time.getText().toString();
                    final String crimes = crime.getText().toString();
                    final String detail = details.getText().toString();
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    userId = mAuth.getCurrentUser().getUid();
                    db.collection("USERS").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    name = task.getResult().getString("name");
                                    pimage = task.getResult().getString("image");
                                    sharedPref.setname(name);
                                    sharedPref.setimage(pimage);
                                    Toast.makeText(ReportActivity.this, name+","+pimage, Toast.LENGTH_SHORT).show();

                                } else {
                                    //String errorMessage = task.getException().getMessage();
                                    Toast.makeText(ReportActivity.this, "Firestore Load Error: " , Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    });

                    if (!TextUtils.isEmpty(times) && !TextUtils.isEmpty(crimes) && !TextUtils.isEmpty(detail) && location != null && types != null) {
                        submit.setVisibility(View.VISIBLE);

                        String date = df.format(c);
                        long tsLong = System.currentTimeMillis() ;
                        String ts = String.valueOf(tsLong);
                        pdialog = new ProgressDialog(ReportActivity.this);
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
                        post.put("type", types);
                        post.put("location", location);
                        post.put("time",times);
                        post.put("crime", crimes) ;
                        post.put("details", detail);
                        post.put("username",username);
                        post.put("userimage", userimage);



                        FirebaseFirestore.getInstance().collection("USERS").document()
                                .set(post)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ReportActivity.this, "Added succesfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ReportActivity.this,HomeActivity.class));
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
                        Toast.makeText(ReportActivity.this, "Text Fields and Image must not be empty", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
}
