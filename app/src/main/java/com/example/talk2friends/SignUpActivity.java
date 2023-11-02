package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    String sender_email = "talk2friendssender@gmail.com";
    String sender_password = "vcrpuobwoeguntnf";
    String recipient_email = "";
    String host = "smtp.gmail.com";

    String valid_email = "";

    TextView email_tv;
    TextView password_tv;
    TextView code_tv;
    //TextView error_tv;

    String new_email = "";
    String new_password = "";
    String random_code = "INVALID";

    int code_length = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
                                    random_code = "";
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

                                    mimeMessage.setSubject("Talk2Friends Verification Code");

                                    Random rand = new Random();
                                    for (int i = 0; i < code_length; ++i) {
                                        int rand_num = rand.nextInt(10);
                                        random_code += Integer.toString(rand_num);
                                    }

                                    mimeMessage.setText("Below is the verification code.\n" + random_code);

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
                                valid_email = email_tv.getText().toString();
                                Toast toast = Toast.makeText(getApplicationContext(), "Code successfully sent!", Toast.LENGTH_SHORT);
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

        password_tv = (TextView) findViewById(R.id.password);
        code_tv = (TextView) findViewById(R.id.code);

        final Boolean[] correct_code = {false};
        TextView verify_bt = (TextView) findViewById(R.id.verify);
        verify_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = code_tv.getText().toString();
                if (code.equals(random_code)) {
                    correct_code[0] = true;
                    Toast toast = Toast.makeText(getApplicationContext(), "Correct verification code", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Incorrect verification code", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }
        });

        TextView signup_bt = (TextView) findViewById(R.id.sign_up);
        signup_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean valid = true;
                new_email = email_tv.getText().toString();
                new_password = password_tv.getText().toString();

                String error_message = "";

                if (new_email.equals("") || new_password.equals("")) {
                    error_message = "One or more fields are empty";
                    valid = false;
                } else if (correct_code[0] == false) {
                    error_message = "Verification not checked";
                    valid = false;
                } else if (!new_email.equals(valid_email)) {
                    error_message = "Unverified email";
                    valid = false;
                }

                if (valid) {
                    User u = new User();
                    u.setUsername(new_email.substring(0, new_email.indexOf('@')));
                    u.setPassword(new_password);
                    Singleton.getInstance().setUsername(new_email.substring(0, new_email.indexOf('@')));
                    Singleton.getInstance().setPassword(new_password);
                    DatabaseUtil.saveUser(u);

                    Intent intent = new Intent(SignUpActivity.this, ProfileCreationActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }
        });
    }

    /*
    public void onSendMessage(View view) {
        //textView2.setText(inputText.getText());
        System.out.println("Button clicked");
    }
    */
}