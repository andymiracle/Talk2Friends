package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MyCreatedMeetingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycreatedmeeting);

        TextView home_button = (TextView) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCreatedMeetingActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recycler_view);

        //System.out.println("EEEE");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meetings");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Meeting> meetingList = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Meeting m = snap.getValue(Meeting.class);
                    if (m.getCreator().equals(Singleton.getInstance().getUsername())) {
                        meetingList.add(m);
                    }
                }

                AdapterForMyCreatedMeeting adapter = new AdapterForMyCreatedMeeting(meetingList, MyCreatedMeetingActivity.this, Singleton.getInstance().getUsername());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(adapter);


                for (int i = 0; i < meetingList.size(); i++) {
                    System.out.println(meetingList.get(i).getName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }


        });

        TextView notifyBell = (TextView) findViewById(R.id.notification_bell);
        CardView notifyCircle = (CardView) findViewById(R.id.notification_circle);
        FriendRequestActivity.setNotification(MyCreatedMeetingActivity.this, notifyBell, notifyCircle);
    }
}