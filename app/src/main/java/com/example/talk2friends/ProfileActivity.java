package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView nameView = (TextView)findViewById(R.id.name_text);
        TextView ageView = (TextView)findViewById(R.id.age_text);
        TextView affiliationView = (TextView)findViewById(R.id.affiliation_text);
        TextView interestsView = (TextView)findViewById(R.id.interests_text);
        TextView home_button = (TextView) findViewById(R.id.home_button);

        ((TextView)findViewById(R.id.profile)).setText(Singleton.getInstance().getUsername() + "'s Profile");

        TextView v = (TextView) findViewById(R.id.edit);
        v.setOnClickListener(this::toEditProfile);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(Singleton.getInstance().getUsername());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
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

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        TextView notifyBell = (TextView) findViewById(R.id.notification_bell);
        CardView notifyCircle = (CardView) findViewById(R.id.notification_circle);
        FriendRequestActivity.setNotification(ProfileActivity.this, notifyBell, notifyCircle);
    }

    public void toEditProfile(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
}