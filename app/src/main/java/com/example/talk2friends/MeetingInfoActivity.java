package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MeetingInfoActivity extends AppCompatActivity {

    private String meetingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_info);

        TextView home_button = (TextView) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingInfoActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        TextView meetingNameView = (TextView) findViewById(R.id.meeting_name_text);
        TextView conversationTopicView = (TextView) findViewById(R.id.conversation_topic_text);
        TextView timeView = (TextView) findViewById(R.id.time_text);
        TextView locationView = (TextView) findViewById(R.id.location_text);
        TextView participantsView = (TextView) findViewById(R.id.participants_text);

        Intent intent = getIntent();
        meetingID = intent.getStringExtra("ID");

        //System.out.println(meetingID);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meetings").child(meetingID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Meeting m = snapshot.getValue(Meeting.class);

                // ANDY You need to check if m, is null
                // If it is, you need to close the listener and leave the activity
                // ALSO YOU NEED TO CHECK ALL PERSISTENT EVENT LISTENERS AND CLOSE THEM ACCORDINGLY

                if (m == null) {
                    //close the listener
                    ref.removeEventListener(this);
                    //leave the activity
                    Intent intent = new Intent(MeetingInfoActivity.this, JoinMeetingActivity.class);
                    startActivity(intent);
                }

                meetingNameView.setText(m.getName());
                conversationTopicView.setText(m.getTopic());
                timeView.setText(m.getTime());
                locationView.setText(m.getLocation());

                ArrayList<String> participants = m.getAttendees();
                String output = "";
                if (participants == null) {
                    participants = new ArrayList<>();
                }

                for (int i = 0; i < participants.size(); i++) {
                    output += participants.get(i) + ", ";
                }
                if (!output.equals("")) {
                    output = output.substring(0, output.length()-2);
                }

                participantsView.setText(output);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }


        });



        TextView v = (TextView) findViewById(R.id.join_button); // submit
        v.setOnClickListener(this::onClickJoin);
    }

    public void onClickJoin(View view) {
        // join meeting, add to your meetings


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meetings").child(meetingID);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Meeting m = snapshot.getValue(Meeting.class);

                // If meeting was not found (deleted)
                if (m == null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Meeting was deleted", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
                }

                ArrayList<String> participants= m.getAttendees();
                if (participants == null) {
                    participants = new ArrayList<String>();
                }

                for (int i = 0; i < participants.size(); i++) {
                    // If we are already a member
                    if (participants.get(i).equals(Singleton.getInstance().getUsername())) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Already joined meeting", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        return;
                    }
                }

                participants.add(Singleton.getInstance().getUsername());
                m.setAttendees(participants);
                DatabaseUtil.saveMeeting(m);

                Toast toast = Toast.makeText(getApplicationContext(), "Joined meeting!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }


        });



        // go back to main meetings page
        //Intent intent = new Intent(this, MainMeetingActivity.class);
        //startActivity(intent);
    }

}