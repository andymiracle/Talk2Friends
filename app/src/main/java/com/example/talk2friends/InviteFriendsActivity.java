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
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class InviteFriendsActivity extends AppCompatActivity {
    String sender_email = "talk2friendssender@gmail.com";
    String sender_password = "vcrpuobwoeguntnf";
    String recipient_email = "";
    String host = "smtp.gmail.com";

    TextView email_tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitefriends);

        email_tv = (TextView) findViewById(R.id.email);
        TextView code_bt = (TextView) findViewById(R.id.send_code);

        code_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean valid = true;
                recipient_email = email_tv.getText().toString();
                int index = recipient_email.indexOf('@');
                String check = recipient_email.substring(index + 1, recipient_email.length());
                String username = "";
                if (index == -1) {
                    valid = false;
                } else {
                    username = recipient_email.substring(0, index);

                    if (!check.equals("usc.edu") && !check.equals("gmail.com")) {
                        valid = false;
                    }
                }

                if (valid) {
                    System.out.println("Username is " + username);

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(username);

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User u = snapshot.getValue(User.class);
                            if (u == null) {
                                try {
                                    Properties properties = System.getProperties();
                                    properties.put("mail.smtp.host", host);
                                    properties.put("mail.smtp.port", "465");
                                    properties.put("mail.smtp.ssl.enable", "true");
                                    properties.put("mail.smtp.auth", "true");

                                    javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                                        @Override
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(sender_email, sender_password);
                                        }
                                    });

                                    MimeMessage mimeMessage = new MimeMessage(session);
                                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient_email));

                                    mimeMessage.setSubject("Invite from Talk2Friends");

                                    mimeMessage.setText(Singleton.getInstance().getUsername() + " is inviting you to join Talk2Friends! Join with this link to automatically become friends with them: "
                                            + "\nhttp://www.talk2friends.com/signup/" + Singleton.getInstance().getUsername());

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Transport.send(mimeMessage);
                                            } catch (MessagingException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    thread.start();
                                } catch (AddressException e) {
                                    e.printStackTrace();
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                                Toast toast = Toast.makeText(getApplicationContext(), "Invitation successfully sent!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "User name already taken!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("Error");
                        }
                    });

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }
        });




        // The email should contain this link: "http://www.talk2friends.com/signup/" + Singleton.getInstance().getUsername()
    }
}
