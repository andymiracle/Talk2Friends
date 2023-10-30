package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_meeting);

        TextView v = (TextView) findViewById(R.id.your_meetings);
        v.setOnClickListener(this::toYourMeetings);

        v = (TextView) findViewById(R.id.create_meeting);
        v.setOnClickListener(this::toCreateMeeting);

        v = (TextView) findViewById(R.id.join_meeting);
        v.setOnClickListener(this::toJoinMeeting);
    }

    public void toYourMeetings(View view) {
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
}