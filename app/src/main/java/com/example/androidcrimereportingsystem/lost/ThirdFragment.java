package com.example.androidcrimereportingsystem.lost;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidcrimereportingsystem.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;

public class ThirdFragment extends Fragment {

    private DatabaseReference databaseid2;
    private RecyclerView idrecyclerview;
    private CardView itemsavailable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.page32, container, false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        databaseid2 = FirebaseDatabase.getInstance().getReference().child("Found_IDs");



        databaseid2.keepSynced(true);



        idrecyclerview = view.findViewById(R.id.rv);
        idrecyclerview.setLayoutManager(new LinearLayoutManager(this.getActivity()));



        idrecyclerview.setNestedScrollingEnabled(false);
        

        if (!IsConnected()) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();

        }

        if (isAdded()) {
            progressDialog.dismiss();

            itemsavailable = view.findViewById(R.id.itemsavailable);


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


            FirebaseRecyclerAdapter<Found, FoundViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Found, FoundViewHolder>(

                    Found.class,
                    R.layout.foundid,
                    FoundViewHolder.class,
                    databaseid2
            ) {
                @Override
                protected void populateViewHolder(final FoundViewHolder viewHolder, Found model, int position) {

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


                            TextView uid = viewHolder.view.findViewById(R.id.telid);
                            TextView date = viewHolder.view.findViewById(R.id.timeid);

                            String uidd = uid.getText().toString();
                            String datee = date.getText().toString();

                            Intent r = new Intent(getActivity(), Seer.class);
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


        return view;
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
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;

        } else {
            return false;
        }


    }

}
