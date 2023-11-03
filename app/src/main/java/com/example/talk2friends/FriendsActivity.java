package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {
    TextView add_tv;
    TextView invite_tv;
    TextView view_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        add_tv = (TextView) findViewById(R.id.add_friends);
        invite_tv = (TextView) findViewById(R.id.invite_friends);
        view_tv = (TextView) findViewById(R.id.view_friends);

        add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this, AddFriendsActivity.class);
                startActivity(intent);
            }
        });

        invite_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this, InviteFriendsActivity.class);
                startActivity(intent);
            }
        });

        view_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this, ViewFriendsActivity.class);
                startActivity(intent);
            }
        });
    }
    public static void friendCurrentUser(String username) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(username);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                ArrayList<String> friends = u.getFriends();
                if (friends == null) {
                    friends = new ArrayList<>();
                }
                friends.add(Singleton.getInstance().getUsername());

                ArrayList<String> incomingRequests = u.getIncomingRequests();
                if (incomingRequests == null) {
                    incomingRequests = new ArrayList<>();
                }
                for (int i = 0; i < incomingRequests.size(); i++) {
                    if (incomingRequests.get(i).equals(Singleton.getInstance().getUsername())) {
                        incomingRequests.remove(i);
                        break;
                    }
                }

                u.setFriends(friends);
                u.setIncomingRequests(incomingRequests);
                DatabaseUtil.saveUser(u);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error");
            }


        });

    }
}