package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    String sender_email = "talk2friendssender@gmail.com";
    String sender_password = "vcrpuobwoeguntnf";
    String recipient_email = "";
    String host = "smtp.gmail.com";

    Boolean valid = false;

    TextView email_tv;
    TextView password_tv;
    TextView code_tv;

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
                System.out.println(recipient_email + "\n");
                System.out.println("Code Button clicked");
            }
        });

        password_tv = (TextView) findViewById(R.id.password);
        code_tv = (TextView) findViewById(R.id.code);
        TextView signup_bt = (TextView) findViewById(R.id.sign_up);
        signup_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = code_tv.getText().toString();
                if (code.equals(random_code)) {
                    valid = true;
                }

                /*add some code more codes to go to the next page (Profile Creation Page)

                if (valid) {
                   valid = false;
                   Intent intent = new Intent(SignUpActivity.this, ?.class);
                   startActivity(?);
                } else {
                   keep being in the page (maybe showing some error message)
                }

                */
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