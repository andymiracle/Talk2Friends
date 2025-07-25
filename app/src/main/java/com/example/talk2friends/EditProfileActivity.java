package com.example.talk2friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

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

    TextView nameView;
    TextView ageView;
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
    TextView home_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        System.out.println(Singleton.getInstance().getUsername());
        System.out.println(Singleton.getInstance().getUsername());
        System.out.println(Singleton.getInstance().getUsername());
        System.out.println(Singleton.getInstance().getUsername());
        System.out.println(Singleton.getInstance().getUsername());

        nameView = (TextView) findViewById(R.id.name_text);
        ageView = (TextView) findViewById(R.id.age_text);
        affiliationSpinner = (Spinner) findViewById(R.id.affiliation_spinner);

        interest1Spinner = (Spinner) findViewById(R.id.interest1Spinner);
        interest2Spinner = (Spinner) findViewById(R.id.interest2Spinner);
        interest3Spinner = (Spinner) findViewById(R.id.interest3Spinner);
        interest4Spinner = (Spinner) findViewById(R.id.interest4Spinner);
        interest5Spinner = (Spinner) findViewById(R.id.interest5Spinner);
        interest6Spinner = (Spinner) findViewById(R.id.interest6Spinner);
        interest7Spinner = (Spinner) findViewById(R.id.interest7Spinner);
        interest8Spinner = (Spinner) findViewById(R.id.interest8Spinner);
        interest9Spinner = (Spinner) findViewById(R.id.interest9Spinner);
        interest10Spinner = (Spinner) findViewById(R.id.interest10Spinner);

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

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(Singleton.getInstance().getUsername());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);

                // prepopulate interests dropdowns with database values
                name = u.getDisplayName();
                age = String.valueOf(u.getAge());
                affiliation = u.getAffiliation();
                HashMap<String, Boolean> interests = u.getInterests();

                // https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
                for(Map.Entry<String, Boolean> m : interests.entrySet()) {

                    String key = m.getKey();

                    if(key.equals("Anime")) {
                        if(m.getValue()) {
                            interest1 = "yes";
                        }
                        else {
                            interest1 = "no";
                        }
                    }
                    else if(key.equals("Traveling")) {
                        if(m.getValue()) {
                            interest2 = "yes";
                        }
                        else {
                            interest2 = "no";
                        }
                    }
                    else if(key.equals("Art")) {
                        if(m.getValue()) {
                            interest3 = "yes";
                        }
                        else {
                            interest3 = "no";
                        }
                    }
                    else if(key.equals("Music")) {
                        if(m.getValue()) {
                            interest4 = "yes";
                        }
                        else {
                            interest4 = "no";
                        }
                    }
                    else if(key.equals("Hiking")) {
                        if(m.getValue()) {
                            interest5 = "yes";
                        }
                        else {
                            interest5 = "no";
                        }
                    }
                    else if(key.equals("Dancing")) {
                        if(m.getValue()) {
                            interest6 = "yes";
                        }
                        else {
                            interest6 = "no";
                        }
                    }
                    else if(key.equals("Cooking")) {
                        if(m.getValue()) {
                            interest7 = "yes";
                        }
                        else {
                            interest7 = "no";
                        }
                    }
                    else if(key.equals("Sports")) {
                        if(m.getValue()) {
                            interest8 = "yes";
                        }
                        else {
                            interest8 = "no";
                        }
                    }
                    else if(key.equals("Video Games")) {
                        if(m.getValue()) {
                            interest9 = "yes";
                        }
                        else {
                            interest9 = "no";
                        }
                    }
                    else if(key.equals("Animals")) {
                        if(m.getValue()) {
                            interest10 = "yes";
                        }
                        else {
                            interest10 = "no";
                        }
                    }
                }

                nameView.setText(name);
                ageView.setText(age);
                // https://stackoverflow.com/questions/11072576/set-selected-item-of-spinner-programmatically
                affiliationSpinner.setSelection(adapter.getPosition(affiliation));

                interest1Spinner.setSelection(adapter2.getPosition(interest1));
                interest2Spinner.setSelection(adapter2.getPosition(interest2));
                interest3Spinner.setSelection(adapter2.getPosition(interest3));
                interest4Spinner.setSelection(adapter2.getPosition(interest4));
                interest5Spinner.setSelection(adapter2.getPosition(interest5));
                interest6Spinner.setSelection(adapter2.getPosition(interest6));
                interest7Spinner.setSelection(adapter2.getPosition(interest7));
                interest8Spinner.setSelection(adapter2.getPosition(interest8));
                interest9Spinner.setSelection(adapter2.getPosition(interest9));
                interest10Spinner.setSelection(adapter2.getPosition(interest10));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }

        });

        home_button = (TextView) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        TextView notifyBell = (TextView) findViewById(R.id.notification_bell);
        CardView notifyCircle = (CardView) findViewById(R.id.notification_circle);
        FriendRequestActivity.setNotification(EditProfileActivity.this, notifyBell, notifyCircle);
    }

    public void onClickSubmit(View view) {

        Boolean valid = true;
        name = nameView.getText().toString();
        age = ageView.getText().toString();
        affiliation = affiliationSpinner.getSelectedItem().toString();
        HashMap<String, Boolean> interestMap = new HashMap<>();

        interest1 = interest1Spinner.getSelectedItem().toString();
        interestMap.put(((TextView) findViewById(R.id.interest1)).getText().toString(), interest1.equals("yes"));
        //System.out.println(((TextView) findViewById(R.id.interest1)).getText().toString() + " Equals " + interest1);
        //System.out.println(interestMap.get("Anime"));
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

        Boolean validInteger = true;
        Boolean positiveInteger = true;

        TextView invalidAge = (TextView) findViewById(R.id.invalid_age);

        int ageInteger = 0;

        if(!(validIntegerChecker(ageInteger, age)))
        {
            invalidAge.setText("age should be an integer no greater than 2147483647");
            validInteger = false;
        }
        else
        {
            ageInteger = Integer.parseInt(age);
        }

        if(negativeIntegerChecker(ageInteger)) {
            positiveInteger = false;
            invalidAge.setText("age should not be negative");
        }

        if(validInteger && positiveInteger) {
            invalidAge.setText("");
        }

        if (atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10)) {
            valid = false;
        }
        else {
            error_tv.setText("");
        }

        if (valid) {

            if(validInteger && positiveInteger) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(Singleton.getInstance().getUsername());

                //System.out.println("Saving to " + Singleton.getInstance().getUsername());

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User u = snapshot.getValue(User.class);
                        u.printClass();

                        User u2 = new User();

                        u2.setUsername(Singleton.getInstance().getUsername());
                        u2.setDisplayName(name);
                        u2.setPassword(u.getPassword());
                        u2.setAge(Integer.parseInt(age));
                        u2.setAffiliation(affiliation);
                        u2.setInterests(interestMap);
                        u2.setFriends(u.getFriends());
                        u2.setIncomingRequests(u.getIncomingRequests());

                        u2.printClass();

                        DatabaseUtil.saveUser(u2);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.getMessage());
                    }


                });

                Intent intent = new Intent(this, MainPageActivity.class);
                startActivity(intent);
            }

        } else {
            error_tv.setText("One or more fields are empty");
        }

    }

    public static Boolean validIntegerChecker(int ageInteger, String number) {

        try {
            ageInteger = Integer.parseInt(number);
        } catch(NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static Boolean negativeIntegerChecker(int number) {
        if(number < 0) {
            return true;
        }

        return false;
    }

    public static Boolean atLeastOneFieldEmpty(String n, String ag, String af, String i1, String i2, String i3, String i4, String i5, String i6, String i7, String i8, String i9, String i10)
    {
        if (n.equals("") || ag.equals("") || af.equals("") || i1.equals("") || i2.equals("") || i3.equals("") || i4.equals("") || i5.equals("") || i6.equals("") || i7.equals("") || i8.equals("") || i9.equals("") || i10.equals(""))
        {
            return true;
        }

        return false;
    }
}
