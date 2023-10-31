package com.example.talk2friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // https://developer.android.com/develop/ui/views/components/spinner#java
        Spinner spinner = (Spinner) findViewById(R.id.affiliation_spinner);
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

        Spinner spinner2 = (Spinner) findViewById(R.id.interest_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.interest_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner2.setAdapter(adapter);

        // prepopulate boxes and dropdowns with database values
        String nameText = ""; // get from database?
        String ageText = "";
        String affiliationText = "native";
        String interestText = "";

        TextView nameView = (TextView) findViewById(R.id.name_text);
        TextView ageView = (TextView) findViewById(R.id.age_text);
        Spinner affiliationView = (Spinner) findViewById(R.id.affiliation_spinner);
        Spinner interestView = (Spinner) findViewById(R.id.interest_spinner);

        nameView.setText(nameText);
        ageView.setText(ageText);
        // https://stackoverflow.com/questions/11072576/set-selected-item-of-spinner-programmatically
        // affiliationView.setSelection(adaptor.getPosition(affiliationText));

        TextView v = (TextView) findViewById(R.id.submit); // submit
        v.setOnClickListener(this::onClickSubmit);

    }

    public void onClickSubmit(View view) {

        Boolean valid = false;

        if (valid) {
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
        } else {
            TextView error_tv = (TextView) findViewById(R.id.error);
            error_tv.setText("One or more fields are empty");
        }

    }
}