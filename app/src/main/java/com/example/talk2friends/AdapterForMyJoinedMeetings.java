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

public class AdapterForMyJoinedMeetings extends RecyclerView.Adapter<AdapterForMyJoinedMeetings.MyViewHolder> {
    private ArrayList<Meeting> meetingsList;
    private Context context;
    private String username;
    private HashMap<String, String> hashmap;

    public AdapterForMyJoinedMeetings(ArrayList<Meeting> meetingsList, Context context, String username) {
        this.meetingsList = meetingsList;
        this.context = context;
        this.username = username;
    }

    @NonNull
    @Override
    public AdapterForMyJoinedMeetings.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items4, parent, false);
        return new AdapterForMyJoinedMeetings.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForMyJoinedMeetings.MyViewHolder holder, int position) {
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

        holder.leave_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meetings").child(meetingID);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Meeting meetingToLeave = snapshot.getValue(Meeting.class);

                        //do we have to check whether the host removed the meeting at the moment we try to leave?
                        if (meetingToLeave == null) {
                            System.out.println("Meeting was just deleted");
                            return;
                        }

                        //get the attendees of the meeting we are trying to leave
                        ArrayList<String> attendees = new ArrayList<>();
                        if (meetingToLeave.getAttendees() != null) {
                            attendees = meetingToLeave.getAttendees();
                        }

                        //remove ourselves from the attendees list of the meeting
                        for (int i = 0; i < attendees.size(); ++i) {
                            if (attendees.get(i).equals(Singleton.getInstance().getUsername())) {
                                attendees.remove(i);
                                break;
                            }
                        }

                        meetingToLeave.setAttendees(attendees);
                        DatabaseUtil.saveMeeting(meetingToLeave);

                        Toast toast = Toast.makeText(context, "Successfully left the meeting", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                System.out.println("I want to leave this meeting");
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
        private Button leave_bt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name);
            details_bt = itemView.findViewById(R.id.details);
            leave_bt = itemView.findViewById(R.id.leave);
        }
    }
}
