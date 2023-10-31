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

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView nameView = (TextView)findViewById(R.id.name_text);
        TextView ageView = (TextView)findViewById(R.id.age_text);
        TextView affiliationView = (TextView)findViewById(R.id.affiliation_text);

        ((TextView)findViewById(R.id.profile)).setText(Singleton.getInstance().getUsername() + "'s Profile");

        TextView v = (TextView) findViewById(R.id.edit);
        v.setOnClickListener(this::toEditProfile);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(Singleton.getInstance().getUsername());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                nameView.setText(u.getDisplayName());
                ageView.setText(Integer.toString(u.getAge()));
                affiliationView.setText(u.getAffiliation());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error");
            }


        });



    }

    public void toEditProfile(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
}