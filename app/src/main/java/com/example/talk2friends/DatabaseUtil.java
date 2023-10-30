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

    public DatabaseUtil() {
        root = FirebaseDatabase.getInstance();
        ref = root.getReference();
    }

    public void saveUser(User u) {
        ref.child("Users").child(encodeEmail(u.getEmail())).setValue(u);
    }

    public User getUser(String email) {
        DatabaseReference userRef = root.getReference("Users").child(encodeEmail(email));

        userRef.addValueEventListener(new ValueEventListener () {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }



        });
        return new User();
       // ref.child("Users").child(email);
    }
    /*
    public void doStuff(){
        FirebaseDatabase root1;
        DatabaseReference ref1;
        root1 = FirebaseDatabase.getInstance();
        //ref1 = root1.getReference("testKey");
        //ref1.setValue("ThisIsATestKey");
    ref1 =root1.getReference();
    User u = new User();
        u.setEmail("test@gmail.com");
        u.setPassword("123");
        u.setUsername("testguy");
        ref1.setValue(u);
    }*/

    public static String encodeEmail(String email) {
        return email.replace('.', '!');
    }

    public static String decodeEmail(String email) {
        return email.replace('!', '.');
    }

}
