package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_meeting);

        //JoinMeetingActivity.deleteExpiredMeetings();

        TextView v = (TextView) findViewById(R.id.my_joined_meetings);
        v.setOnClickListener(this::toMyJoinedMeetings);

        v = (TextView) findViewById(R.id.create_meeting);
        v.setOnClickListener(this::toCreateMeeting);

        v = (TextView) findViewById(R.id.join_meeting);
        v.setOnClickListener(this::toJoinMeeting);

        v = (TextView) findViewById(R.id.my_created_meetings);
        v.setOnClickListener(this::toMyCreatedMeeting);

        TextView home_button = (TextView) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMeetingActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        TextView notifyBell = (TextView) findViewById(R.id.notification_bell);
        CardView notifyCircle = (CardView) findViewById(R.id.notification_circle);
        FriendRequestActivity.setNotification(MainMeetingActivity.this, notifyBell, notifyCircle);
    }

    public void toMyJoinedMeetings(View view) {
        Intent intent = new Intent(this, YourMeetingActivity.class);
        startActivity(intent);
    }

    public void toCreateMeeting(View view) {
        Intent intent = new Intent(this, CreateMeetingActivity.class);
        startActivity(intent);
    }

    public void toJoinMeeting(View view) {
        Intent intent = new Intent(this, JoinMeetingActivity.class);
        startActivity(intent);
    }

    public void toMyCreatedMeeting(View view) {
        Intent intent = new Intent(this, MyCreatedMeetingActivity.class);
        startActivity(intent);
    }

}