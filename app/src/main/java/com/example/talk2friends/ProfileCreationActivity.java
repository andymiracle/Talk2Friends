package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileCreationActivity extends AppCompatActivity {
    String name = "";
    String age = "";
    String affiliation = "";
    String interest = "";

    TextView name_tv;
    TextView age_tv;
    Spinner spinner;
    Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        name_tv = (TextView) findViewById(R.id.name_text);
        age_tv = (TextView) findViewById(R.id.age_text);

        // https://developer.android.com/develop/ui/views/components/spinner#java
        spinner = (Spinner) findViewById(R.id.affiliation_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.affliation_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.interest_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.interest_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner2.setAdapter(adapter2);

        TextView v = (TextView) findViewById(R.id.submit); // submit
        v.setOnClickListener(this::onClickSubmit);
    }

    public void onClickSubmit(View view){
        Boolean valid = true;
        name = name_tv.getText().toString();
        age = age_tv.getText().toString();
        affiliation = spinner.getSelectedItem().toString();
        interest = spinner2.getSelectedItem().toString();

        if (name.equals("") || age.equals("") || affiliation.equals("") || interest.equals("")) {
            valid = false;
        }

        if (valid) {
            Intent intent = new Intent(ProfileCreationActivity.this, MainPageActivity.class);
            startActivity(intent);
        } else {
            TextView error_tv = (TextView) findViewById(R.id.error);
            error_tv.setText("One or more fields are empty");
        }

    }
}