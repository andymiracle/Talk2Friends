package com.example.talk2friends;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterForViewingMeeting extends RecyclerView.Adapter<AdapterForViewingMeeting.MyViewHolder> {
    private ArrayList<Meeting> meetingsList;
    private Context context;
    private String username;
    private HashMap<String, String> hashmap;

    public AdapterForViewingMeeting(ArrayList<Meeting> meetingsList, Context context, String username) {
        this.meetingsList = meetingsList;
        this.context = context;
        this.username = username;
    }

    @NonNull
    @Override
    public AdapterForViewingMeeting.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items3, parent, false);
        return new AdapterForViewingMeeting.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForViewingMeeting.MyViewHolder holder, int position) {
        String name = meetingsList.get(position).getName();
        String meetingID = meetingsList.get(position).getMeetingID();

        holder.nameTxt.setText(name);

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

                System.out.println("I want to remove this meeting");
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
