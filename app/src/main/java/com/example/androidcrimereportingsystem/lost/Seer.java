package com.example.androidcrimereportingsystem.lost;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidcrimereportingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Seer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seer);

        final String uid = getIntent().getExtras().getString("Uid");
        String date = getIntent().getExtras().getString("Date");


        final TextView name, datee, contact;
        name = findViewById(R.id.namew);
        datee = findViewById(R.id.dat);
        contact = findViewById(R.id.pho);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = firebaseFirestore.collection("Users").document(uid);
        documentReference.get().addOnCompleteListener(Seer.this, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    if (task.getResult().exists() && task.getResult().exists()) {
                        String nameDB = task.getResult().get("name").toString();
                        String regnoDB = task.getResult().get("regno").toString();

                        name.setText(nameDB);
                        contact.setText(regnoDB);

                    }
                } else {
                    name.setText("Not found");
                    contact.setText("Not found");


                }
            }
        });


        datee.setText(date);
    }
}
