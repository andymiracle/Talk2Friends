package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SignUpActivity extends AppCompatActivity {
    String sender_email = "talk2friendssender@gmail.com";
    String sender_password = "vcrpuobwoeguntnf";
    String recipient_email = "";
    String host = "smtp.gmail.com";

    TextView email_tv;
    TextView password_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email_tv = (TextView) findViewById(R.id.email);
        TextView code_bt = (TextView) findViewById(R.id.send_code);
        code_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    recipient_email = email_tv.getText().toString();

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

                    mimeMessage.setSubject("Subject: Android App email");
                    mimeMessage.setText("Hello my guy");

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
                System.out.println(recipient_email + "\n");
                System.out.println("Code Button clicked");
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