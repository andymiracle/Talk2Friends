package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {
    TextView profile_tv;
    TextView friends_tv;
    TextView meetings_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        profile_tv = (TextView) findViewById(R.id.profile);
        friends_tv = (TextView) findViewById(R.id.friends);
        meetings_tv = (TextView) findViewById(R.id.manage_meetings);
        ((TextView) findViewById(R.id.home_page)).setText("Welcome " + Singleton.getInstance().getUsername() + "!");

        profile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        friends_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, FriendsActivity.class);
                startActivity(intent);
            }
        });

        meetings_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, MainMeetingActivity.class);
                startActivity(intent);
            }
        });
    }
}