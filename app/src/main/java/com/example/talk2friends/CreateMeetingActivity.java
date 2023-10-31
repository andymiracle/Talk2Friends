package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateMeetingActivity extends AppCompatActivity {

    String conversationTopic;
    String time;
    String location;

    TextView conversationTopicView;
    TextView timeView;
    TextView locationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        TextView v = (TextView) findViewById(R.id.create); // submit
        v.setOnClickListener(this::onClickCreate);
    }

    public void onClickCreate(View view){

        Boolean valid = false;

        valid = true;

        if (valid) {
            // add meeting to database
            // go back to main meetings page
            Intent intent = new Intent(this, MainMeetingActivity.class);
            startActivity(intent);
        } else {
            TextView error_tv = (TextView) findViewById(R.id.error);
            error_tv.setText("One or more fields are empty");
        }

    }
}