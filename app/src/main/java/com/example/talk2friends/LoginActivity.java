package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import android.widget.Toast;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {
    TextView username_tv;
    TextView password_tv;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        Intent intent = new Intent(this, FriendsProfileActivity.class);
        startActivity(intent);
         */


        username_tv = (TextView) findViewById(R.id.username);
        password_tv = (TextView) findViewById(R.id.password);
        TextView login = (TextView) findViewById(R.id.login);
        TextView signup = (TextView) findViewById(R.id.sign_up);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                username = username_tv.getText().toString();
                int index = username.indexOf('@');
                if (index != -1) {
                    username = username.substring(0, index);
                }

                password = password_tv.getText().toString();
                password = hashPassword(password);
                System.out.println("Username is: " + username + "\n");
                System.out.println("Password is: " + password + "\n");

                // Accepts empty username for debugging purposes. DELETE IN FINAL RELEASE
                if (username.equals("")) {
                    username = "Default";
                    password = hashPassword("Default");
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(username);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        User u = snapshot.getValue(User.class);
                        /*if (u != null && u.getPassword().length() < 20) {
                            u.setPassword(hashPassword(u.getPassword()));
                            DatabaseUtil.saveUser(u);
                        }*/

                        if (u != null && u.getPassword().equals(password)) {
                            u.printClass();
                            Singleton.getInstance().setUsername(username);
                            Singleton.getInstance().setPassword(password);
                            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Error");
                    }


                });



            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // BW - Testing
        DatabaseUtil util = new DatabaseUtil();
        //User temp = new User("bwiencko", "benji", "123", 22, "Native", new HashMap<String, Boolean>(), new ArrayList<String>());
        //User temp1 = new User("Garbo", "DefaultName", "Garbo", 20, "Native", new HashMap<String, Boolean>(), new ArrayList<String>());
        //temp.printClass();
        //util.saveUser(temp);
        //util.saveUser(temp1);
        //util.dbPrintUser(temp.getUsername());
        //u.printClass();
        //    public Meeting(String name, String meetingID, String time, String location, String topic, String creator, ArrayList<String> attendees) {
        //Meeting meet = new Meeting("First", UUID.randomUUID().toString(), "12/12/2023 12:12 am", "THH", "English", );


        //System.out.println(u.getEmail());
    }

    public static String hashPassword(String password) {

        byte[] byteHash = {};
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byteHash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : byteHash) {
            sb.append(String.format("%02X", b));
        }

        return sb.toString();
    }
}
