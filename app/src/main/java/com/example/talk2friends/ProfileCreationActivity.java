package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.ArrayList;

public class ProfileCreationActivity extends AppCompatActivity {
    String name;
    String age;
    String affiliation;
    String interest1;
    String interest2;
    String interest3;
    String interest4;
    String interest5;
    String interest6;
    String interest7;
    String interest8;
    String interest9;
    String interest10;

    TextView name_tv;
    TextView age_tv;
    Spinner affiliationSpinner;
    Spinner interest1Spinner;
    Spinner interest2Spinner;
    Spinner interest3Spinner;
    Spinner interest4Spinner;
    Spinner interest5Spinner;
    Spinner interest6Spinner;
    Spinner interest7Spinner;
    Spinner interest8Spinner;
    Spinner interest9Spinner;
    Spinner interest10Spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        name_tv = (TextView) findViewById(R.id.name_text);
        age_tv = (TextView) findViewById(R.id.age_text);
        affiliationSpinner = (Spinner) findViewById(R.id.affiliation_spinner);

        interest1Spinner = (Spinner) findViewById(R.id.interest1_spinner);
        interest2Spinner = (Spinner) findViewById(R.id.interest2_spinner);
        interest3Spinner = (Spinner) findViewById(R.id.interest3_spinner);
        interest4Spinner = (Spinner) findViewById(R.id.interest4_spinner);
        interest5Spinner = (Spinner) findViewById(R.id.interest5_spinner);
        interest6Spinner = (Spinner) findViewById(R.id.interest6_spinner);
        interest7Spinner = (Spinner) findViewById(R.id.interest7_spinner);
        interest8Spinner = (Spinner) findViewById(R.id.interest8_spinner);
        interest9Spinner = (Spinner) findViewById(R.id.interest9_spinner);
        interest10Spinner = (Spinner) findViewById(R.id.interest10_spinner);

        // https://developer.android.com/develop/ui/views/components/spinner#java
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.affliation_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        affiliationSpinner.setAdapter(adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.interest_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        interest1Spinner.setAdapter(adapter2);
        interest2Spinner.setAdapter(adapter2);
        interest3Spinner.setAdapter(adapter2);
        interest4Spinner.setAdapter(adapter2);
        interest5Spinner.setAdapter(adapter2);
        interest6Spinner.setAdapter(adapter2);
        interest7Spinner.setAdapter(adapter2);
        interest8Spinner.setAdapter(adapter2);
        interest9Spinner.setAdapter(adapter2);
        interest10Spinner.setAdapter(adapter2);

        TextView v = (TextView) findViewById(R.id.submit); // submit
        v.setOnClickListener(this::onClickSubmit);
    }

    public void onClickSubmit(View view){
        Boolean valid = true;
        name = name_tv.getText().toString();
        age = age_tv.getText().toString();
        affiliation = affiliationSpinner.getSelectedItem().toString();

        HashMap<String, Boolean> interestMap = new HashMap<>();

        interest1 = interest1Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest1)).getText().toString(), interest1.equals("yes"));
        interest2 = interest2Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest2)).getText().toString(), interest2.equals("yes"));
        interest3 = interest3Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest3)).getText().toString(), interest3.equals("yes"));
        interest4 = interest4Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest4)).getText().toString(), interest4.equals("yes"));
        interest5 = interest5Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest5)).getText().toString(), interest5.equals("yes"));
        interest6 = interest6Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest6)).getText().toString(), interest6.equals("yes"));
        interest7 = interest7Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest7)).getText().toString(), interest7.equals("yes"));
        interest8 = interest8Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest8)).getText().toString(), interest8.equals("yes"));
        interest9 = interest9Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest9)).getText().toString(), interest9.equals("yes"));
        interest10 = interest10Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest10)).getText().toString(), interest10.equals("yes"));

        TextView error_tv = (TextView) findViewById(R.id.error);

        if (name.equals("") || age.equals("") || affiliation.equals("") || interest1.equals("") || interest2.equals("") || interest3.equals("") || interest4.equals("") || interest5.equals("") || interest6.equals("") || interest7.equals("") || interest8.equals("") || interest9.equals("") || interest10.equals("")) {
            valid = false;
        }
        else {
            error_tv.setText("");
        }

        if (!valid) {
            error_tv.setText("One or more fields are empty");
            return;
        }

        User u = new User();
        u.setUsername(Singleton.getInstance().getUsername());
        u.setDisplayName(name);
        u.setPassword(Singleton.getInstance().getPassword());
        u.setAge(Integer.parseInt(age));
        u.setAffiliation(affiliation);
        u.setInterests(interestMap);

        if (!Singleton.getInstance().getFriendCode().equals("")) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(Singleton.getInstance().getFriendCode());
            // Update current user's friends list to reflect new friend
            u.setFriends(temp);

            // Update friend's friend list to reflect current user
            FriendsActivity.friendCurrentUser(Singleton.getInstance().getFriendCode());

        }
        DatabaseUtil.saveUser(u);

        Intent intent = new Intent(ProfileCreationActivity.this, MainPageActivity.class);
        startActivity(intent);



    }
}