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


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(username);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User u = snapshot.getValue(User.class);
                        ArrayList<String> incoming = u.getIncomingRequests();
                        if (incoming == null) {
                            incoming = new ArrayList<>();
                        }

                        Boolean duplicate = false;
                        for (int i = 0; i < incoming.size(); ++i) {
                            if (incoming.get(i).equals(Singleton.getInstance().getUsername())) {
                                duplicate = true;
                                break;
                            }
                        }

                        if (duplicate) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Request already sent!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        } else {
                            incoming.add(Singleton.getInstance().getUsername());
                            u.setIncomingRequests(incoming);

                            System.out.println("The user " + u.getUsername() + " received invitations from ");
                            for (int i = 0; i < u.getIncomingRequests().size(); ++i) {
                                System.out.println("The user " +u.getIncomingRequests().get(i));
                            }

                            DatabaseUtil.saveUser(u);
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
    }
}
