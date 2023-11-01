package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_meeting);

        //System.out.println("EEEE");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meetings");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Meeting> meetingList = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Meeting m = snap.getValue(Meeting.class);
                    if (m.getCreator().equals(Singleton.getInstance().getUsername())) {
                        meetingList.add(m);
                    }

                    // meetingList now contains all meetings that the current user has created
                }
                /*for (int i = 0; i < meetingList.size(); i++) {
                    System.out.println(meetingList.get(i).getMeetingID());
                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error");
            }


        });
    }
}