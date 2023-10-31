package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

        interest1 = interest1Spinner.getSelectedItem().toString();
        interest2 = interest2Spinner.getSelectedItem().toString();
        interest3 = interest3Spinner.getSelectedItem().toString();
        interest4 = interest4Spinner.getSelectedItem().toString();
        interest5 = interest5Spinner.getSelectedItem().toString();
        interest6 = interest6Spinner.getSelectedItem().toString();
        interest7 = interest7Spinner.getSelectedItem().toString();
        interest8 = interest8Spinner.getSelectedItem().toString();
        interest9 = interest9Spinner.getSelectedItem().toString();
        interest10 = interest10Spinner.getSelectedItem().toString();

        if (name.equals("") || age.equals("") || affiliation.equals("") || interest1.equals("") || interest2.equals("") || interest3.equals("") || interest4.equals("") || interest5.equals("") || interest6.equals("") || interest7.equals("") || interest8.equals("") || interest9.equals("") || interest10.equals("")) {
            valid = false;
        }

        if (!valid) {
            TextView error_tv = (TextView) findViewById(R.id.error);
            error_tv.setText(R.string.error);
            return;
        }

        User u = new User();
        u.setUsername(Singleton.getInstance().getUsername());
        u.setDisplayName(name);
        u.setPassword(Singleton.getInstance().getPassword());
        u.setAge(Integer.parseInt(age));
        u.setAffiliation(affiliation);
        u.setInterest("default interest"); // need to support multiple interests
        DatabaseUtil.saveUser(u);

        Intent intent = new Intent(ProfileCreationActivity.this, MainPageActivity.class);
        startActivity(intent);



    }
}