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
    private static final String sender_email = "talk2friendssender@gmail.com";
    private static final String sender_password = "vcrpuobwoeguntnf";
    private static String recipient_email = "";
    private static final String host = "smtp.gmail.com";
    private static final int code_length = 6;
    private static String random_code = "INVALID";
    private static String valid_email = "";

    TextView email_tv;
    TextView password_tv;
    TextView code_tv;
    //TextView error_tv;

    String new_email = "";
    String new_password = "";

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

                Boolean dotInUsername = false;

                if (index == -1) {
                    valid = false;
                } else {
                    username = recipient_email.substring(0, index);

                    if (!check.equals("usc.edu")) {
                        valid = false;
                    }

                    //check whether user contains '.'
                    if (username.contains(".")) {
                        valid = false;
                        dotInUsername = true;
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
                                sendEmail(recipient_email);
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
                            System.out.println(error.getMessage());
                        }
                    });

                } else {
                    Toast toast;
                    if (dotInUsername) { //if the user name has a dot inside, set a toast message of warning
                        toast = Toast.makeText(getApplicationContext(), "Username can't have . inside", Toast.LENGTH_SHORT);
                    } else {
                        toast = Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT);
                    }
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }
        });

        password_tv = (TextView) findViewById(R.id.password);
        code_tv = (TextView) findViewById(R.id.code);

        TextView signup_bt = (TextView) findViewById(R.id.sign_up);
        signup_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean valid = true;
                new_email = email_tv.getText().toString();
                new_password = password_tv.getText().toString();

                String error_message = "";

                //System.out.println("Code is " + random_code + " You got " + code_tv.getText().toString());

                if (atLeastOneFieldEmpty(new_email, new_password)) {
                    error_message = "One or more fields are empty";
                    valid = false;
                } else if (!new_email.equals(valid_email)) {
                    error_message = "Unverified email";
                    valid = false;
                } else if (!random_code.equals(code_tv.getText().toString())) {
                    error_message = "Incorrect verification code";
                    valid = false;
                }
                new_password = LoginActivity.hashPassword(new_password);

                if (valid) {

                    Uri data = getIntent().getData();
                    // If app was opened from a link, data will contain the link
                    if (data != null) {
                        //String host = data.getHost(); // "www.talk2friends.com"
                        String friendCode = data.getPath(); // "/signup/username"
                        // Isolate the username
                        friendCode = friendCode.substring(friendCode.lastIndexOf('/')+1, friendCode.length());
                        Singleton.getInstance().setFriendCode(friendCode);
                    }

                    User u = new User();
                    //u.setUsername(new_email.substring(0, new_email.indexOf('@')));
                    //u.setPassword(new_password);
                    Singleton.getInstance().setUsername(new_email.substring(0, new_email.indexOf('@')));
                    Singleton.getInstance().setPassword(new_password);
                    //DatabaseUtil.saveUser(u);

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

    public static Boolean sendEmail(String recipient_email) {
        Boolean isSentEmail = false;
        try {
            random_code = getRandomCode();
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
            isSentEmail = true;
            return isSentEmail;
        } catch (AddressException e) {
            System.out.println("Address Exception happened!");
            isSentEmail = false;
            return isSentEmail;
            //e.printStackTrace();
        } catch (MessagingException e) {
            System.out.println("Messaging Exception happened!");
            e.printStackTrace();
        }

        return isSentEmail;
    }

    public static String getRandomCode() {
        String code = "";
        Random rand = new Random();
        for (int i = 0; i < code_length; ++i) {
            int rand_num = rand.nextInt(10);
            code += Integer.toString(rand_num);
        }
        return code;
    }

    public static Boolean atLeastOneFieldEmpty(String e, String p)
    {
        if(e.equals("") || p.equals(""))
        {
            return true;
        }

        return false;
    }

}