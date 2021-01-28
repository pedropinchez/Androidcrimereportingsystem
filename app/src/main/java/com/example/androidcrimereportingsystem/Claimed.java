package com.example.androidcrimereportingsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcrimereportingsystem.lost.Found;
import com.example.androidcrimereportingsystem.lost.Seer;
import com.example.androidcrimereportingsystem.lost.ThirdFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;

public class Claimed extends AppCompatActivity {
    private DatabaseReference databaseid2;
    private RecyclerView idrecyclerview;
    private CardView itemsavailable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page32);
        final ProgressDialog progressDialog = new ProgressDialog(Claimed.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        databaseid2 = FirebaseDatabase.getInstance().getReference().child("Found_IDs");



        databaseid2.keepSynced(true);



        idrecyclerview = findViewById(R.id.rv);
        idrecyclerview.setLayoutManager(new LinearLayoutManager(Claimed.this));



        idrecyclerview.setNestedScrollingEnabled(false);


        if (!IsConnected()) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Toast.makeText(Claimed.this, "No Internet connection", Toast.LENGTH_SHORT).show();

        }


            progressDialog.dismiss();

            itemsavailable = findViewById(R.id.itemsavailable);


            Handler ls = new Handler();
            ls.postDelayed(new Runnable() {
                @Override
                public void run() {
                    itemsavailable.setVisibility(View.VISIBLE);

                    Handler s = new Handler();
                    s.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            itemsavailable.setVisibility(View.GONE);
                        }
                    }, 3500);

                }
            }, 2500);


            FirebaseRecyclerAdapter<Found, Claimed.FoundViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Found, Claimed.FoundViewHolder>(

                    Found.class,
                    R.layout.foundid,
                    Claimed.FoundViewHolder.class,
                    databaseid2
            ) {
                @Override
                protected void populateViewHolder(final Claimed.FoundViewHolder viewHolder, Found model, int position) {

                    String foundkey = getRef(position).getKey();
                    viewHolder.setIdname(model.getIdname());
                    viewHolder.setIdno(model.getIdno());
                    viewHolder.setIdtel(model.getIduid());
                    viewHolder.setIdtime(model.getIdtime());
                    viewHolder.setExpireTime(model.getExpiretime());

                    TextView textView = viewHolder.view.findViewById(R.id.expiretime);
                    String posttime = textView.getText().toString();
                    if (!TextUtils.isEmpty(posttime)) {
                        long r = Long.parseLong(posttime);
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        long ti = timestamp.getTime();

                        long diff = ti - r;

                        if (diff >= 1000 * 60 * 60 * 24 * 14) {
                            databaseid2.child(foundkey).removeValue();
                        }
                    }

                    viewHolder.cardviewid.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            TextView uid = findViewById(R.id.telid);
                            TextView date =findViewById(R.id.timeid);

                            String uidd = uid.getText().toString();
                            String datee = date.getText().toString();

                            Intent r = new Intent(Claimed.this, Seer.class);
                            r.putExtra("Uid", uidd);
                            r.putExtra("Date", datee);
                            startActivity(r);

                        }
                    });


                }
            };

            idrecyclerview.setAdapter(firebaseRecyclerAdapter);






            databaseid2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {






                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            databaseid2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });






    }

    public static class FoundViewHolder extends RecyclerView.ViewHolder {

        View view;
        CardView cardviewid;

        public FoundViewHolder(View itemView) {
            super(itemView);

            view = itemView;
           cardviewid = view.findViewById(R.id.card);

        }


        public void setIdname(String name) {
            TextView textView = view.findViewById(R.id.idnametext);
            textView.setText(name);
        }


        public void setIdno(String name) {
            TextView textView = view.findViewById(R.id.idtext);
            textView.setText(name);
        }


        public void setIdtel(String name) {
            TextView textView = view.findViewById(R.id.telid);
            textView.setText(name);
        }

        public void setIdtime(String name) {
            TextView textView = view.findViewById(R.id.timeid);
            textView.setText(name);
        }




        public void setExpireTime(long to) {

            TextView textView = view.findViewById(R.id.expiretime);
            textView.setText(String.valueOf(to));
        }








    }

    private boolean IsConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;

        } else {
            return false;
        }


    }

}

