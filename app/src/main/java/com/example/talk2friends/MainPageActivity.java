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

        TextView home_button = (TextView) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        TextView notifyBell = (TextView) findViewById(R.id.notification_bell);
        CardView notifyCircle = (CardView) findViewById(R.id.notification_circle);
        FriendRequestActivity.setNotification(MainPageActivity.this, notifyBell, notifyCircle);

        /*
        TextView notification_button = (TextView) findViewById(R.id.notification_bell);
        notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, FriendRequestActivity.class);
                startActivity(intent);
            }
        });

        CardView notifyCircle = (CardView) findViewById(R.id.notification_circle);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(Singleton.getInstance().getUsername());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                if (u.getIncomingRequests() != null) {
                    notifyCircle.setVisibility(View.VISIBLE);
                } else {
                    notifyCircle.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         */
    }
}