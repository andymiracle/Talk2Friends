package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class AddFriendsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    TextView request_tv;
    TextView username_tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);

        recyclerView = findViewById(R.id.recycler_view);
        username_tv = findViewById(R.id.username);
        request_tv = findViewById(R.id.request);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> userList = new ArrayList<>();
                ArrayList<String> friendsList = new ArrayList<>();
                User current_user = new User();

                //andy's testing code (making sure that we only see the users that are not in the friend's list)
                for (DataSnapshot snap: snapshot.getChildren()) {
                    User u = snap.getValue(User.class);
                    if (u.getUsername().equals(Singleton.getInstance().getUsername())) {
                        current_user = u;
                        break;
                    }
                }

                friendsList = current_user.getFriends();
                if (friendsList == null) {
                    friendsList = new ArrayList<>();
                }

                for (DataSnapshot snap: snapshot.getChildren()) {
                    User u = snap.getValue(User.class);

                    if (u.getUsername().equals(Singleton.getInstance().getUsername())) {
                        Singleton.getInstance().setInterests(u.getInterests());
                    }
                    else {
                        if (!friendsList.contains(u.getUsername())) {
                            userList.add(u);
                            //System.out.println("Hah");
                        }
                    }
                }

                /* Original code
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User u = snap.getValue(User.class);

                    if (u.getUsername().equals(Singleton.getInstance().getUsername())) {
                        Singleton.getInstance().setInterests(u.getInterests());
                    }
                    else {
                        userList.add(u);
                    }

                }

                 */

                /*
                System.out.println("BEFORE");
                for (int i = 0; i < userList.size(); i++) {
                    System.out.println(userList.get(i).getUsername());
                }
                 */

                Collections.sort(userList, new UserComparator());

                // userList now contains friends ordered by the most matching likes and dislikes

                /*
                System.out.println("AFTER");
                for (int i = 0; i < userList.size(); i++) {
                    System.out.println(userList.get(i).getUsername());
                }
                */


                recyclerAdapter adapter = new recyclerAdapter(userList, AddFriendsActivity.this);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }


        });

        request_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_tv.getText().toString();

                if (username.equals(Singleton.getInstance().getUsername())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Cannot send it to yourself", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<String> friendsList = new ArrayList<>();
                            ArrayList<String> incoming = new ArrayList<>();
                            User current_user = new User();
                            User target_user = null;

                            for (DataSnapshot snap : snapshot.getChildren()) {
                                User u = snap.getValue(User.class);

                                if (u.getUsername().equals(Singleton.getInstance().getUsername())) {
                                    current_user = u;
                                } else if (u.getUsername().equals(username)) {
                                    target_user = u;
                                }
                            }

                            friendsList = current_user.getFriends();

                            if (target_user == null) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Username doesn't exist", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            } else {
                                incoming = target_user.getIncomingRequests(); //target user's incoming friend requests
                                ArrayList<String> currentIncoming = current_user.getIncomingRequests();

                                if (incoming == null) {
                                    incoming = new ArrayList<>();
                                }
                                if (currentIncoming == null) {
                                    currentIncoming = new ArrayList<>();
                                }


                                if (friendsList.contains(username)) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Already added as a friend", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toast.show();
                                }
                                else if (incoming.contains(current_user.getUsername())) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Request already sent", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toast.show();
                                }
                                else if (currentIncoming.contains(target_user.getUsername())) {

                                    FriendsActivity.friendUser(target_user.getUsername());
                                    FriendsActivity.friendCurrentUser(target_user.getUsername());

                                    Toast toast = Toast.makeText(getApplicationContext(), target_user.getUsername() + " has already sent a friend request. You are now friends!", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toast.show();
                                }
                                else {
                                    incoming.add(Singleton.getInstance().getUsername());
                                    target_user.setIncomingRequests(incoming);
                                    DatabaseUtil.saveUser(target_user);

                                    Toast toast = Toast.makeText(getApplicationContext(), "Request successfully sent!", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toast.show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }




            }
        });
    }
}




class UserComparator implements Comparator<User> {

    // override the compare() method
    public int compare(User u1, User u2)
    {
        int score1 = UserComparator.getScore(u1);
        int score2 = UserComparator.getScore(u2);

        if (score1 == score2) {
            return 0;
        }
        if (score1 > score2) {
            return -1;
        }
        return 1;
    }

    public static int getScore(User u) {
        int score = 0;
        for (String likes : u.getInterests().keySet()) {
            if (Singleton.getInstance().getInterests().get(likes) == u.getInterests().get(likes)) {
                score++;
            }
        }
        //System.out.println(u.getUsername() + " has score of " + score);
        return score;
    }
}