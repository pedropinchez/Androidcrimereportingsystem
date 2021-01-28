package com.example.androidcrimereportingsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private ArrayList<MyListData> listData;
    static Context cnt;
    String timeString = "";
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private String currentUserId;
    private  String userId,name,image;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public MyListAdapter(ArrayList<MyListData> listData, Context cnt) {
        this.listData = listData;
        this.cnt = cnt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.post, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String current_uid = mCurrentUser.getUid();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MyListData myListData = listData.get(position);


        holder.username.setText(myListData.getUsername());
        holder.title.setText("Crime details ::"+myListData.getCrime());
        holder.content.setText("Additional info ::"+myListData.getDetails());
        holder.date.setText(myListData.getDate());
        String dbTimestamp = myListData.getTime();
        Picasso.get().load(myListData.getUserimage()).into(holder.userimage);
        holder.type.setText("Type of crime :: "+myListData.getType());
        holder.time.setText("Time of crime :: "+myListData.getTime());
        holder.place.setText("place of crime :: "+myListData.getLocation());

    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView title,content,type,date,username,time,place;
        CircleImageView userimage;
        
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.post_title);
            content = itemView.findViewById(R.id.post_desc);
            type=itemView.findViewById(R.id.post_type);
            time=itemView.findViewById(R.id.post_time);
            place=itemView.findViewById(R.id.post_place);
           username = itemView.findViewById(R.id.post_username);
            date = itemView.findViewById(R.id.post_date);
            userimage = itemView.findViewById(R.id.post_user_image);
        }
    }


}