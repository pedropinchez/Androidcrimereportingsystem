package com.example.androidcrimereportingsystem.lost;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcrimereportingsystem.R;
import com.example.androidcrimereportingsystem.Utils.SharedPref;
import com.example.androidcrimereportingsystem.lost.Connectfound;
import com.example.androidcrimereportingsystem.lost.Lost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FirstFragment extends Fragment {
    private DatabaseReference databaseid;
    private DatabaseReference databaseid2;
    private RecyclerView idrecyclerview;
    private FirebaseAuth mAuth;

    SharedPref sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.page_3, container, false);

        if (isAdded()) {
            sharedPref = new SharedPref(getActivity());

            databaseid = FirebaseDatabase.getInstance().getReference().child("Lost_IDs");



            databaseid2 = FirebaseDatabase.getInstance().getReference().child("Found_IDs");


            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            databaseid.keepSynced(true);

            databaseid2.keepSynced(true);


            idrecyclerview = view.findViewById(R.id.rv);
            idrecyclerview.setLayoutManager(new LinearLayoutManager(this.getActivity()));


            idrecyclerview.setNestedScrollingEnabled(false);

            mAuth = FirebaseAuth.getInstance();

            if (!IsConnected()) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();
            }
            if (mAuth.getCurrentUser() == null) {
                progressDialog.dismiss();
            }
            if (isAdded()) {

                FirebaseRecyclerAdapter<Lost, LostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Lost, LostViewHolder>(
                        Lost.class,
                        R.layout.lostandfoundid,
                        LostViewHolder.class,
                        databaseid


                ) {
                    @Override
                    protected void populateViewHolder(final LostViewHolder viewHolderid, Lost model, final int positionid) {


                        viewHolderid.setIdName(model.getNameid());
                        viewHolderid.setIdNo(model.getIdnumber());
                        viewHolderid.settelid(model.getNumber());
                        progressDialog.dismiss();

                        viewHolderid.idclaim.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               if (!IsConnected()) {

                                    Toast.makeText(getActivity(), "You are not connected to the internet", Toast.LENGTH_SHORT).show();

                                } else {

                                    AlertDialog.Builder alertDialog;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        alertDialog = new AlertDialog.Builder(getActivity());
                                    } else {
                                        alertDialog = new AlertDialog.Builder(getActivity());
                                    }

                                    TextView na = viewHolderid.view.findViewById(R.id.idnametext);
                                    TextView id = viewHolderid.view.findViewById(R.id.idtext);
                                    TextView tel = viewHolderid.view.findViewById(R.id.telid);
                                    final String s = na.getText().toString();
                                    final String str3 = id.getText().toString();
                                    final String telno = tel.getText().toString();
                                    String str2 = "Do you claim to be " + s + "  of ID number " + str3 + "?";
                                    alertDialog.setTitle("Confirm Ownership")

                                            .setMessage(str2)
                                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                                    long ti = timestamp.getTime();
                                                    Calendar calendar = Calendar.getInstance();
                                                    calendar.setTimeInMillis(ti);
                                                    Date d = calendar.getTime();
                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a", java.util.Locale.getDefault());
                                                    SimpleDateFormat simpleDateFormats = new SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault());

                                                    String od = simpleDateFormat.format(d);
                                                    String ods = simpleDateFormats.format(d);
                                                    String f = ods + " at " + od;
                                                    String uid = mAuth.getCurrentUser().getUid();


                                                    String postkeyid = getRef(positionid).getKey();


                                                    DatabaseReference post = databaseid2.push();

                                                    post.child("iduid").setValue(uid);
                                                    post.child("idtime").setValue(f);
                                                    post.child("expiretime").setValue(ti);
                                                    post.child("idname").setValue(s);
                                                    post.child("idno").setValue(str3);

                                                    databaseid.child(postkeyid).removeValue();

                                                    Intent co = new Intent(getActivity(), Connectfound.class);
                                                    co.putExtra("Number", telno);

                                                    startActivity(co);


                                                }
                                            })
                                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_menu_send)
                                            .show();
                                }
                            }
                        });
                    }


                };

                idrecyclerview.setAdapter(firebaseRecyclerAdapter);





                databaseid.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {

                        }}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                databaseid.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        }
        return view;
    }



    public static class LostViewHolder extends RecyclerView.ViewHolder {

        View view;
        CardView idclaim;

        public LostViewHolder(View itemView) {
            super(itemView);

            view = itemView;

            idclaim = view.findViewById(R.id.card);



        }

        public void setIdName(String name) {

            TextView d = view.findViewById(R.id.idnametext);
            d.setText(name);
        }

        public void setIdNo(String name) {

            TextView d = view.findViewById(R.id.idtext);
            d.setText(name);
        }








        public void settelid(String name) {

            TextView d = view.findViewById(R.id.telid);
            d.setText(name);
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