package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CreateMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        TextView v = (TextView) findViewById(R.id.submit); // submit
        v.setOnClickListener(this::onClickSubmit);
    }

    public void onClickSubmit(View view){

    }
}