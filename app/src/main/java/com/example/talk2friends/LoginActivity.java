package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView email_tv;
    TextView password_tv;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_tv = (TextView) findViewById(R.id.email);
        password_tv = (TextView) findViewById(R.id.password);
        TextView login = (TextView) findViewById(R.id.login);
        TextView signup = (TextView) findViewById(R.id.sign_up);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                email = email_tv.getText().toString();
                password = password_tv.getText().toString();
                System.out.println("Email is: " + email + "\n");
                System.out.println("Password is: " + password + "\n");
                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                startActivity(intent);
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
        //DatabaseUtil util = new DatabaseUtil();
        //util.saveUser(new User());
    }
}
