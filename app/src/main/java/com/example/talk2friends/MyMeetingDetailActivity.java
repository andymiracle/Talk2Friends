package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyMeetingDetailActivity extends AppCompatActivity {
    private String meetingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymeetingdetail);

        TextView home_button = (TextView) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyMeetingDetailActivity.this, MainPageActivity.class);
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

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Meeting m = snapshot.getValue(Meeting.class);

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

        TextView notifyBell = (TextView) findViewById(R.id.notification_bell);
        CardView notifyCircle = (CardView) findViewById(R.id.notification_circle);
        FriendRequestActivity.setNotification(MyMeetingDetailActivity.this, notifyBell, notifyCircle);
    }
}
