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

import java.util.ArrayList;
import java.util.UUID;
import java.util.Calendar;


public class CreateMeetingActivity extends AppCompatActivity {

    String conversationTopic;
    String time;
    String location;
    String meetingName;

    TextView conversationTopicView;
    TextView timeView;
    TextView locationView;
    TextView meetingNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);


        conversationTopicView = (TextView) findViewById(R.id.conversation_topic_text);
        // need to limit to max 16 characters

        timeView = (TextView) findViewById(R.id.time_text);
        locationView = (TextView) findViewById(R.id.location_text);
        meetingNameView = (TextView) findViewById(R.id.meeting_name_text);

        TextView v = (TextView) findViewById(R.id.create); // create
        v.setOnClickListener(this::onClickCreate);

    }

    public void onClickCreate(View view) {

        Boolean valid = true;
        conversationTopic = conversationTopicView.getText().toString();
        time = timeView.getText().toString();
        location = locationView.getText().toString();
        meetingName = meetingNameView.getText().toString();

        TextView meetingNameSize_tv = (TextView) findViewById(R.id.meeting_name_too_long);

        Boolean textRightSize = true;

        if(meetingName.length()>16)
        {
            meetingNameSize_tv.setText("Meeting name should be no more than 16 characters");
            textRightSize = false;
        }

        if(textRightSize) {
            meetingNameSize_tv.setText("");
        }

        TextView error_tv = (TextView) findViewById(R.id.error);

        if (conversationTopic.equals("") || time.equals("") || location.equals("") || meetingName.equals("")) {
            valid = false;
        }
        else {
            error_tv.setText("");
        }

        Boolean validTimeFormat = true;
        Boolean isDateAfterToday = true;
        TextView time_tv = (TextView) findViewById(R.id.incorrect_time_format);

        if(!time.equals("")) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            dateFormat.setLenient(false);
            try {
                Date date = dateFormat.parse(time);
                if (date.before(new Date())) {
                    // new Date() is unreliable, commenting out below until a more reliable check is found
                    //isDateAfterToday = false;
                }
            } catch (ParseException e) {
                time_tv.setText("Incorrect time format");
                validTimeFormat = false;
            }
        }

        if(validTimeFormat && isDateAfterToday) {
            time_tv.setText("");
        }
        else if (!isDateAfterToday) {
            time_tv.setText("Time cannot be before today");
        }

        if (valid) {
            // add meeting to database
            // go back to main meetings page
            if(validTimeFormat && isDateAfterToday && textRightSize) {
                Meeting m = new Meeting();
                m.setCreator(Singleton.getInstance().getUsername());
                m.setTime(time);
                m.setMeetingID(UUID.randomUUID().toString());
                m.setTopic(conversationTopic);
                m.setLocation(location);
                m.setName(meetingName);

                ArrayList<String> temp = new ArrayList<>();
                temp.add(Singleton.getInstance().getUsername());
                m.setAttendees(temp);

                DatabaseUtil.saveMeeting(m);

                Intent intent = new Intent(this, MainMeetingActivity.class);
                startActivity(intent);
            }
        } else {
            error_tv.setText("One or more fields are empty");
        }

    }
}