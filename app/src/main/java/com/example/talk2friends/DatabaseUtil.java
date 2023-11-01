package com.example.talk2friends;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;


public class DatabaseUtil {
    FirebaseDatabase root;
    DatabaseReference ref;
    Object snapObj;

    public DatabaseUtil() {
        root = FirebaseDatabase.getInstance();
        ref = root.getReference();
    }

    public static void saveUser(User u) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
        userRef.child("users").child(u.getUsername()).setValue(u);
    }

    public static void saveMeeting(Meeting m) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
        userRef.child("meetings").child(m.getMeetingID()).setValue(m);
    }

    public void dbPrintUser(String username) {

        DatabaseReference userRef = root.getReference("users").child(username);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                u.printClass();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("NO DATA");
            }


        });


    }


}
