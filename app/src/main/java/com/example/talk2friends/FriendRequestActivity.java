package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class FriendRequestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendrequest);

        recyclerView = findViewById(R.id.recycler_view2);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        // NOTE THIS IS NOT A SINGLE VALUE LISTENER!!!!! THIS WILL KEEP RUNNING UNTIL YOU STOP IT MANUALLY
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //System.out.println("Triggered");
                ArrayList<String> incomingRequests = new ArrayList<>();
                HashMap<String, String> userToDisplay = new HashMap<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User u = snap.getValue(User.class);

                    userToDisplay.put(u.getUsername(), u.getDisplayName());
                    System.out.println(userToDisplay.get("Default"));

                    if (u.getUsername().equals(Singleton.getInstance().getUsername())) {
                        if (u.getIncomingRequests() != null) {
                            incomingRequests = u.getIncomingRequests();
                        }
                    }
                }

                AdapterForRequest adapter = new AdapterForRequest(userToDisplay, incomingRequests, FriendRequestActivity.this, Singleton.getInstance().getUsername());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(adapter);



                // userToDisplay maps username to display name
                // incomingRequests holds all pending incoming friend requests
                // Display requests (up to 10?)


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
                Intent intent = new Intent(FriendRequestActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void setNotification(Context context, TextView notifyBell, CardView notifyCircle) {
        notifyBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FriendRequestActivity.class);
                context.startActivity(intent);
            }
        });

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
    }
}
