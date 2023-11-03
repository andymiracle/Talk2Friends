package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class AddFriendsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    TextView button_tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);

        recyclerView = findViewById(R.id.recycler_view);

        button_tv = findViewById(R.id.btnAddToDo);
        button_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFriendsActivity.this, FriendsProfileActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> userList = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    User u = snap.getValue(User.class);

                    if (u.getUsername().equals(Singleton.getInstance().getUsername())) {
                        Singleton.getInstance().setInterests(u.getInterests());
                    }
                    else {
                        userList.add(u);
                    }

                }

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
                System.out.println("Error");
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