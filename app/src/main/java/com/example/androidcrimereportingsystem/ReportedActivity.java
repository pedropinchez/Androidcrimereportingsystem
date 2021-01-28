package com.example.androidcrimereportingsystem;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReportedActivity extends AppCompatActivity {
    ArrayList<MyListData> listdata;

    ArrayList<String> documentIds;
    Button add;

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    CollectionReference collectionReference;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reported);
        recyclerView = findViewById(R.id.recycler);

        listdata = new ArrayList<>();
        documentIds = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        query = firebaseFirestore.collection("USERS").orderBy("timestamp", Query.Direction.DESCENDING);
        firebaseFirestore.collection("USERS");

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                    firebaseFirestore.collection("USERS")
                            .document(documentIds.get(viewHolder.getAdapterPosition()))
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    Toast.makeText(ReportedActivity.this, "deleted" + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ReportedActivity.this, "failure", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }).attachToRecyclerView(recyclerView);
        } else {


        }


    }

    public void loadData() {


            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Toast.makeText(ReportedActivity.this, "Error occured : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        listdata = new ArrayList<>();

                        if (!queryDocumentSnapshots.isEmpty()) {

                            String id = "";

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                id = documentSnapshot.getId();
                                MyListData myListData = documentSnapshot.toObject(MyListData.class);
                                listdata.add(myListData);
                                //String name = myListData.getUsername();

                                documentIds.add(id);

//                                SharedPref sharedPref=new SharedPref(getActivity());
//                                int previouscount=sharedPref.getCount();
//                                int newcounts=queryDocumentSnapshots.size();
//                                int difference =newcounts-previouscount;
//                               // Toast.makeText(getActivity(), "new count" +difference, Toast.LENGTH_SHORT).show();
//                                Log.e(TAG, "onEvent: "+previouscount );
//                                Log.e(TAG, "onEvent: "+newcounts );
//                                int count = queryDocumentSnapshots.size();
//                                sharedPref.setCount(count);
                            }

                            if (IsConnected()) {


                                recyclerView.setAdapter(null);
                                MyListAdapter adapter = new MyListAdapter(listdata, ReportedActivity.this);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ReportedActivity.this));
                                recyclerView.setAdapter(adapter);

                            } else {
                                recyclerView.setAdapter(null);
                                MyListAdapter adapter = new MyListAdapter(listdata, ReportedActivity.this);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ReportedActivity.this));
                                recyclerView.setAdapter(adapter);
                                Toast.makeText(ReportedActivity.this, "Cant Update：" + "network unavailable.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });


    }

    @Override
    public void onStart() {
        super.onStart();

        loadData();
    }

    private boolean IsConnected() {

        try {


            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        } catch (Exception ignored) {
            return true;
        }
    }
}
