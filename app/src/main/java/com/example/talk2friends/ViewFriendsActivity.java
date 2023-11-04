package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
                User currentUser = new User();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    if (user.getUsername().equals(Singleton.getInstance().getUsername())) {
                        currentUser = user;
                        break;
                    }
                }

                ArrayList<String> friends = currentUser.getFriends();

                for (int i = 0; i < friends.size(); ++i) {
                    System.out.println(Singleton.getInstance().getUsername() + " has " + friends.get(i) + " as a friend");
                }

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
    }
}
