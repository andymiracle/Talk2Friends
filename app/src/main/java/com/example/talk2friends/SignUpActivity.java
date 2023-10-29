package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
    String sender_email = "talk2friend@gmail.com";
    String recipient_email = "";
    TextView inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputText = (TextView) findViewById(R.id.message);
        TextView bt = (TextView) findViewById(R.id.button);
        bt.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        recipient_email = inputText.getText().toString();
        System.out.println(recipient_email + "\n");
        System.out.println("Button clicked");
    }

    /*
    public void onSendMessage(View view) {
        //textView2.setText(inputText.getText());
        System.out.println("Button clicked");
    }
    */
}