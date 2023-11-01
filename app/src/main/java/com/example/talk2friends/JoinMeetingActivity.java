package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Comparator;
import java.util.Collections;

public class JoinMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_meeting);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meetings");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Meeting> meetingList = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Meeting m = snap.getValue(Meeting.class);
                    meetingList.add(m);
                }

                System.out.println("BEFORE");
                for (int i = 0; i < meetingList.size(); i++) {
                    System.out.println(meetingList.get(i).getMeetingID());
                }


                System.out.println("AFTER");
                Collections.sort(meetingList, new MeetingComparator());
                // meetingList now contains all meetings sorted from first occurring to last occurring.
                for (int i = 0; i < meetingList.size(); i++) {
                    System.out.println(meetingList.get(i).getMeetingID());
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error");
            }


        });

    }
}

class MeetingComparator implements Comparator<Meeting> {

    // override the compare() method
    public int compare(Meeting m1, Meeting m2)
    {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        dateFormat.setLenient(false);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = dateFormat.parse(m1.getTime());
            date2 = dateFormat.parse(m2.getTime());
        } catch (ParseException e) {
            System.out.println("Error parsing date");
        }

        return date1.compareTo(date2);
    }
}

