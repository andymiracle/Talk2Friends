package com.example.talk2friends;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterForMyCreatedMeeting extends RecyclerView.Adapter<AdapterForMyCreatedMeeting.MyViewHolder> {
    private ArrayList<Meeting> meetingsList;
    private Context context;
    private String username;
    private HashMap<String, String> hashmap;

    public AdapterForMyCreatedMeeting(ArrayList<Meeting> meetingsList, Context context, String username) {
        this.meetingsList = meetingsList;
        this.context = context;
        this.username = username;
    }

    @NonNull
    @Override
    public AdapterForMyCreatedMeeting.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items3, parent, false);
        return new AdapterForMyCreatedMeeting.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForMyCreatedMeeting.MyViewHolder holder, int position) {
        String meetingName = meetingsList.get(position).getName();
        String meetingID = meetingsList.get(position).getMeetingID();

        holder.nameTxt.setText(meetingName);

        holder.details_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyMeetingDetailActivity.class);
                intent.putExtra("ID", meetingID);
                context.startActivity(intent);
                System.out.println("I want to see details man");
            }
        });

        holder.remove_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete meeting from database
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meetings").child(meetingID);
                ref.removeValue();

                //System.out.println("I want to remove this meeting I created");
            }
        });
    }

    @Override
    public int getItemCount() {
        return meetingsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTxt;
        private Button details_bt;
        private Button remove_bt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name);
            details_bt = itemView.findViewById(R.id.details);
            remove_bt = itemView.findViewById(R.id.remove);
        }
    }
}
