package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendsProfileActivity extends AppCompatActivity {
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendsprofile);

        TextView nameView = (TextView)findViewById(R.id.name_text);
        TextView ageView = (TextView)findViewById(R.id.age_text);
        TextView affiliationView = (TextView)findViewById(R.id.affiliation_text);
        TextView interestsView = (TextView)findViewById(R.id.interests_text);

        TextView request_tv = (TextView) findViewById(R.id.request);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(username);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                ((TextView)findViewById(R.id.profile)).setText(u.getUsername() + "'s Profile");
                nameView.setText(u.getDisplayName());
                ageView.setText(Integer.toString(u.getAge()));
                affiliationView.setText(u.getAffiliation());
                String output = "";
                for (String likes : u.getInterests().keySet()) {
                    System.out.println(likes);
                    if (u.getInterests().get(likes)) {
                        output += likes + ", ";
                    }
                }
                if (!output.equals("")) {
                    output = output.substring(0, output.length()-2);
                }
                interestsView.setText(output);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }


        });

        request_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //ArrayList<String> friendsList = new ArrayList<>();
                        ArrayList<String> targetIncoming = new ArrayList<>();
                        ArrayList<String> currentIncoming = new ArrayList<>();
                        User currentUser = null;
                        User targetUser = null;

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            User u = snap.getValue(User.class);

                            if (u.getUsername().equals(Singleton.getInstance().getUsername())) {
                                currentUser = u;
                            } else if (u.getUsername().equals(username)) {
                                targetUser = u;
                            }
                        }

                        // Should never be reached
                        if (targetUser == null || currentUser == null) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Error processing your request", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                            return;
                        }

                        if (currentUser.getIncomingRequests() != null) {
                            currentIncoming = currentUser.getIncomingRequests();
                        }

                        if (targetUser.getIncomingRequests() != null) {
                            targetIncoming = targetUser.getIncomingRequests();
                        }

                        Boolean isDuplicateRequest = false;
                        for (int i = 0; i < targetIncoming.size(); ++i) {
                            if (targetIncoming.get(i).equals(Singleton.getInstance().getUsername())) {
                                isDuplicateRequest = true;
                                break;
                            }
                        }
                        ArrayList<String> friendsList = new ArrayList<>();
                        if (currentUser.getFriends() != null) {
                            friendsList = currentUser.getFriends();
                        }

                        if (isDuplicateRequest) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Request already sent!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                        else if (friendsList.contains(username)) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Already added as a friend", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                        else if (currentIncoming.contains(username)) {
                            FriendsActivity.friendUser(username);
                            FriendsActivity.friendCurrentUser(username);

                            Toast toast = Toast.makeText(getApplicationContext(), targetUser.getUsername() + " has already sent a friend request. You are now friends!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                        else {
                            targetIncoming.add(Singleton.getInstance().getUsername());
                            targetUser.setIncomingRequests(targetIncoming);
                            DatabaseUtil.saveUser(targetUser);

                            Toast toast = Toast.makeText(getApplicationContext(), "Request successfully sent!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getMessage());
                    }


                });
            }
        });

        TextView home_button = (TextView) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsProfileActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
