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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Collections;
import java.util.HashMap;

public class ViewFriendsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfriends);

        recyclerView = findViewById(R.id.recycler_view);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> friendsList = new ArrayList<>();
                HashMap<String, String> userToDisplay = new HashMap<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User u = snap.getValue(User.class);
                    userToDisplay.put(u.getUsername(), u.getDisplayName());

                    if (u.getUsername().equals(Singleton.getInstance().getUsername())) {
                        if (u.getFriends() != null) {
                            friendsList = u.getFriends();
                        }
                    }
                }

                AdapterForViewing adapter = new AdapterForViewing(userToDisplay, friendsList, ViewFriendsActivity.this, Singleton.getInstance().getUsername());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(adapter);

                /*
                if (friends.size() > 0) {
                    friends.clear();
                    currentUser.setFriends(friends);
                    DatabaseUtil.saveUser(currentUser);
                } else {
                    System.out.println("Mission complete!");
                }

                 */


                /*
                ArrayList<User> friendsList = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    for (int i = 0; i < friends.size(); ++i) {
                        if (friends.get(i).equals(user.getUsername())) {
                            friendsList.add(user);
                            break;
                        }
                    }
                }

                 */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }


        });

        TextView home_button = (TextView) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewFriendsActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        TextView notifyBell = (TextView) findViewById(R.id.notification_bell);
        CardView notifyCircle = (CardView) findViewById(R.id.notification_circle);
        FriendRequestActivity.setNotification(ViewFriendsActivity.this, notifyBell, notifyCircle);
    }
}
