package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        conversationTopicView = (TextView) findViewById(R.id.conversation_topic_text);
        timeView = (TextView) findViewById(R.id.time_text);
        locationView = (TextView) findViewById(R.id.location_text);

        TextView v = (TextView) findViewById(R.id.create); // create
        v.setOnClickListener(this::onClickCreate);

    }

    public void onClickCreate(View view) {

        Boolean valid = true;
        conversationTopic = conversationTopicView.getText().toString();
        time = timeView.getText().toString();
        location = locationView.getText().toString();

        TextView error_tv = (TextView) findViewById(R.id.error);

        if (conversationTopic.equals("") || time.equals("") || location.equals("")) {
            valid = false;
        }
        else {
            error_tv.setText("");
        }

        Boolean validTimeFormat = true;
        TextView time_tv = (TextView) findViewById(R.id.incorrect_time_format);

        if(!time.equals("")) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            dateFormat.setLenient(false);
            try {
                Date date = dateFormat.parse(time);
            } catch (ParseException e) {
                time_tv.setText("incorrect time format");
                validTimeFormat = false;
            }
        }

        if(validTimeFormat == true) {
            time_tv.setText("");
        }

        if (valid) {
            // add meeting to database
            // go back to main meetings page
            if(validTimeFormat==true) {
                Intent intent = new Intent(this, MainMeetingActivity.class);
                startActivity(intent);
            }
        } else {
            error_tv.setText("One or more fields are empty");
        }

    }
}