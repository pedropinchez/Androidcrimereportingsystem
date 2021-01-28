package com.example.androidcrimereportingsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidcrimereportingsystem.Utils.SharedPref;
import com.example.androidcrimereportingsystem.lost.LostAndFound;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LostActivity extends AppCompatActivity {
    Spinner spinner;
    RelativeLayout layoutId;
    ProgressDialog progressDialog;
    DatabaseReference databaseid;
    FirebaseAuth mAuth;


    Button cardId;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lost2);


            sharedPref = new SharedPref(this);

            spinner = findViewById(R.id.spinner);
            layoutId =findViewById(R.id.nationalid);
            cardId = findViewById(R.id.card);
            progressDialog = new ProgressDialog(LostActivity.this);
            mAuth = FirebaseAuth.getInstance();

            databaseid = FirebaseDatabase.getInstance().getReference().child("Lost_IDs");

            SharedPref shared = new SharedPref(LostActivity.this);
            shared.setIsGuest(false);
            cardId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    EditText name, idno, num;
                    name =findViewById(R.id.w);
                    idno = findViewById(R.id.a);
                    num = findViewById(R.id.ww);

                    String namee = name.getText().toString();
                    String idnoo = idno.getText().toString();
                    String numm = num.getText().toString();

                    if (!TextUtils.isEmpty(namee) && !TextUtils.isEmpty(idnoo) && !TextUtils.isEmpty(numm)) {

                        progressDialog.setMessage("Sending...");
                        // progressDialog.show();


                        DatabaseReference post = databaseid.push();

                        post.child("nameid").setValue(namee);
                        post.child("idnumber").setValue(idnoo);
                        post.child("number").setValue(numm);

                        progressDialog.dismiss();

                    } else if (!TextUtils.isEmpty(namee) && TextUtils.isEmpty(idnoo)) {
                        Toast.makeText(LostActivity.this, "ID Number can't be empty", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(namee) && !TextUtils.isEmpty(idnoo)) {
                        Toast.makeText(LostActivity.this, "Name can't be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LostActivity.this, "Required fields are empty", Toast.LENGTH_SHORT).show();
                    }
                    layoutId.setVisibility(View.INVISIBLE);


                    Intent r = new Intent(LostActivity.this, LostAndFound.class);
                    r.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(r);


                }

            });


            List<String> cate = new ArrayList<>();
            cate.add("Chose Category");
            cate.add("National ID");


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(LostActivity.this, android.R.layout.simple_spinner_item, cate);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    switch (position) {

                        case 0:

                            layoutId.setVisibility(View.INVISIBLE);


                            break;
                        case 1:

                            layoutId.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });




    }}
