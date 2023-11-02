package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {
    TextView add_tv;
    TextView invite_tv;
    TextView view_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        add_tv = (TextView) findViewById(R.id.add_friends);
        invite_tv = (TextView) findViewById(R.id.invite_friends);
        view_tv = (TextView) findViewById(R.id.view_friends);

        add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this, AddFriendsActivity.class);
                startActivity(intent);
            }
        });

        invite_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this, InviteFriendsActivity.class);
                startActivity(intent);
            }
        });

        view_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this, ViewFriendsActivity.class);
                startActivity(intent);
            }
        });
    }
}