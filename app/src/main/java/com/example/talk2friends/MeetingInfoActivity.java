package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MeetingInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_info);

        TextView conversationTopicView = (TextView) findViewById(R.id.conversation_topic_text);
        TextView timeView = (TextView) findViewById(R.id.time_text);
        TextView locationView = (TextView) findViewById(R.id.location_text);
        TextView participantsView = (TextView) findViewById(R.id.participants_text);
        // need to pull into to display from database

        TextView v = (TextView) findViewById(R.id.join_button); // submit
        v.setOnClickListener(this::onClickJoin);
    }

    public void onClickJoin(View view) {
        // join meeting, add to your meetings

        // go back to main meetings page
        Intent intent = new Intent(this, MainMeetingActivity.class);
        startActivity(intent);
    }
}