package com.example.talk2friends;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.DisplayContext;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.Espresso.onData;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.instanceOf;

//import static org.junit.Assert.fail;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.ArrayList;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileInstrumentedTest {

    private static final String DISPLAY_NAME = "TestingName";
    private static final int AGE = 20;
    private static final String AFFILIATION = "Native";
    private static final String PASSWORD_HASH = "03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4";


    @Rule public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);



    @Test
    public void testViewProfile() {

        HashMap<String, Boolean> interestMap = new HashMap<>();
        interestMap.put("Anime", true);
        interestMap.put("Traveling", true);
        interestMap.put("Art", false);
        interestMap.put("Music", false);
        interestMap.put("Hiking", false);
        interestMap.put("Dancing", false);
        interestMap.put("Cooking", false);
        interestMap.put("Sports", false);
        interestMap.put("Video Games", false);
        interestMap.put("Animals", false);

        //User tester = new User ("tester", "testerName", hashPassword("1234"), 20, "Native", new HashMap<>(), new ArrayList<>());
        User u = new User(LoginInstrumentedTest.USERNAME, DISPLAY_NAME, PASSWORD_HASH, AGE, AFFILIATION, interestMap, new ArrayList<>());
        DatabaseUtil.saveUser(u);

        LoginInstrumentedTest.smallSleep();

        LoginInstrumentedTest.login();
        onView(withId(R.id.profile)).perform(click());
        LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.name_text)).check(matches(withText(DISPLAY_NAME)));
        onView(withId(R.id.age_text)).check(matches(withText(Integer.toString(AGE))));
        onView(withId(R.id.affiliation_text)).check(matches(withText(AFFILIATION)));
        onView(withId(R.id.interests_text)).check(matches(withText("Anime, Traveling")));



    }


    @Test
    public void testEditProfile() {
        // Reset tester profile
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(LoginInstrumentedTest.USERNAME);
        userRef.child("age").removeValue();
        userRef.child("interests").removeValue();
        userRef.child("displayName").removeValue();
        userRef.child("affiliation").removeValue();


        LoginInstrumentedTest.login();
        onView(withId(R.id.profile)).perform(click());
        LoginInstrumentedTest.smallSleep();
        onView(withId(R.id.edit)).perform(click());
        LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.name_text))
                .perform(replaceText(DISPLAY_NAME), closeSoftKeyboard());
        onView(withId(R.id.age_text))
                .perform(replaceText(Integer.toString(AGE)), closeSoftKeyboard());
        onView(withId(R.id.affiliation_spinner)).perform(click());
        //onView(allOf(withId(R.id.textview_in_custom_spinner), withText("DummyText"))).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Native"))).perform(click());


        // Setting interests as Anime and Travelling
        onView(withId(R.id.interest1Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("yes"))).perform(click());
        onView(withId(R.id.interest2Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("yes"))).perform(click());
        onView(withId(R.id.interest3Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("no"))).perform(click());
        onView(withId(R.id.interest4Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("no"))).perform(click());
        onView(withId(R.id.interest5Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("no"))).perform(click());

        onView(withId(R.id.interest6Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("no"))).perform(click());
        onView(withId(R.id.interest7Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("no"))).perform(click());
        onView(withId(R.id.interest8Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("no"))).perform(click());
        onView(withId(R.id.interest9Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("no"))).perform(click());
        onView(withId(R.id.interest10Spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("no"))).perform(click());

        onView(withId(R.id.submit)).perform(click());
        LoginInstrumentedTest.smallSleep();
        onView(withId(R.id.profile)).perform(click());
        LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.name_text)).check(matches(withText(DISPLAY_NAME)));
        onView(withId(R.id.age_text)).check(matches(withText(Integer.toString(AGE))));
        onView(withId(R.id.affiliation_text)).check(matches(withText(AFFILIATION)));
        onView(withId(R.id.interests_text)).check(matches(withText("Anime, Traveling")));


        /*
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);

                Assert.assertEquals(u.getUsername(), LoginInstrumentedTest.USERNAME);
                Assert.assertEquals(u.getDisplayName(), DISPLAY_NAME);
                Assert.assertEquals(u.getAffiliation(), AFFILIATION);
                Assert.assertEquals(u.getAge(), AGE);
                HashMap<String, Boolean> interestMap = new HashMap<>();
                interestMap.put("Anime", true);
                interestMap.put("Traveling", true);
                interestMap.put("Art", false);
                interestMap.put("Music", false);
                interestMap.put("Hiking", false);
                interestMap.put("Dancing", false);
                interestMap.put("Cooking", false);
                interestMap.put("Sports", false);
                interestMap.put("Video Games", false);
                interestMap.put("Animals", false);
                Assert.assertEquals(u.getInterests(), interestMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("NO DATA");
            }


        });
         */


    }
}
