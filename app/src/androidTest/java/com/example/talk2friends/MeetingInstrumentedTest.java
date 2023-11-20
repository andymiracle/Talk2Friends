package com.example.talk2friends;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.DisplayContext;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.Espresso.onData;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.action.ViewActions.replaceText;

import static androidx.test.espresso.contrib.RecyclerViewActions.*;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Location;
import android.view.View;
import androidx.test.espresso.ViewAction;

import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;


import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MeetingInstrumentedTest {

    private static final String CONVERSATION_TOPIC = "Misc.";
    private static final String TIME = "12/12/2024 12:12 am";
    private static final String LOCATION = "SAL";
    private static final String MEETING_NAME = "JTest's Meet";

    private static final String EARLY_TIME = "01/01/0001 12:01 am";


    @Rule public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @After
    public void databaseCleanup() {
        deleteTesterMeetings();
        LoginInstrumentedTest.bigSleep();
    }

    @Test
    public void testViewMeeting() {
        ArrayList<String> attendees = new ArrayList<>();
        attendees.add(LoginInstrumentedTest.USERNAME);
        Meeting m = new Meeting(MEETING_NAME, TIME, LOCATION, CONVERSATION_TOPIC, LoginInstrumentedTest.USERNAME, attendees);

        DatabaseUtil.saveMeeting(m);
        //(String name, String meetingID, String time, String location, String topic, String creator, ArrayList<String> attendees) {


        LoginInstrumentedTest.login();

        onView(withId(R.id.manage_meetings)).perform(click());
        //LoginInstrumentedTest.smallSleep();
        onView(withId(R.id.my_created_meetings)).perform(click());
        //LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ClickAction.clickItemButton(R.id.details)));

        //LoginInstrumentedTest.smallSleep();


        onView(withId(R.id.meeting_name_text)).check(matches(withText(MEETING_NAME)));
        onView(withId(R.id.conversation_topic_text)).check(matches(withText(CONVERSATION_TOPIC)));
        onView(withId(R.id.time_text)).check(matches(withText(TIME)));
        onView(withId(R.id.location_text)).check(matches(withText(LOCATION)));

    }


    @Test
    public void testCreateMeeting() {

        LoginInstrumentedTest.login();
        onView(withId(R.id.manage_meetings)).perform(click());
        //LoginInstrumentedTest.smallSleep();
        onView(withId(R.id.create_meeting)).perform(click());
        //LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.conversation_topic_text))
                .perform(replaceText(CONVERSATION_TOPIC), closeSoftKeyboard());
        onView(withId(R.id.time_text))
                .perform(replaceText(TIME), closeSoftKeyboard());
        onView(withId(R.id.location_text))
                .perform(replaceText(LOCATION), closeSoftKeyboard());
        onView(withId(R.id.meeting_name_text))
                .perform(replaceText(MEETING_NAME), closeSoftKeyboard());

        onView(withId(R.id.create)).perform(click());
        //LoginInstrumentedTest.bigSleep();
        onView(withId(R.id.my_created_meetings)).perform(click());
        //LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ClickAction.clickItemButton(R.id.details)));
        //LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.meeting_name_text)).check(matches(withText(MEETING_NAME)));
        onView(withId(R.id.conversation_topic_text)).check(matches(withText(CONVERSATION_TOPIC)));
        onView(withId(R.id.time_text)).check(matches(withText(TIME)));
        onView(withId(R.id.location_text)).check(matches(withText(LOCATION)));
        //onView(withId(R.id.participants_text)).check(matches(withText("")));

        LoginInstrumentedTest.smallSleep();

    }
    @Test
    public void testLeaveMeeting() {
        ArrayList<String> attendees = new ArrayList<>();
        attendees.add(LoginInstrumentedTest.USERNAME);
        Meeting m = new Meeting(MEETING_NAME, TIME, LOCATION, CONVERSATION_TOPIC, LoginInstrumentedTest.USERNAME, attendees);
        DatabaseUtil.saveMeeting(m);

        LoginInstrumentedTest.login();
        onView(withId(R.id.manage_meetings)).perform(click());
        //LoginInstrumentedTest.smallSleep();
        onView(withId(R.id.my_joined_meetings)).perform(click());
        //LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ClickAction.clickItemButton(R.id.leave)));

        LoginInstrumentedTest.bigSleep();

        try {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            fail("Leaving meeting has failed.");
        } catch (Exception e) {
            return;
        }

    }

    @Test
    public void testDeleteMeeting() {
        ArrayList<String> attendees = new ArrayList<>();
        attendees.add(LoginInstrumentedTest.USERNAME);
        Meeting m = new Meeting(MEETING_NAME, TIME, LOCATION, CONVERSATION_TOPIC, LoginInstrumentedTest.USERNAME, attendees);
        DatabaseUtil.saveMeeting(m);

        LoginInstrumentedTest.login();
        onView(withId(R.id.manage_meetings)).perform(click());
        //LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.my_created_meetings)).perform(click());
        //LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ClickAction.clickItemButton(R.id.remove)));
        //LoginInstrumentedTest.smallSleep();

        try {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            fail("Meeting not properly removed.");
        } catch (Exception e) {
            return;
        }

    }


    @Test
    public void testJoinMeeting() {
        Meeting m = new Meeting(MEETING_NAME, EARLY_TIME, LOCATION, CONVERSATION_TOPIC, LoginInstrumentedTest.USERNAME, new ArrayList<>());
        DatabaseUtil.saveMeeting(m);

        LoginInstrumentedTest.login();
        onView(withId(R.id.manage_meetings)).perform(click());
        //LoginInstrumentedTest.smallSleep();
        onView(withId(R.id.join_meeting)).perform(click());
        //LoginInstrumentedTest.smallSleep();
        onView(withId(R.id.meeting_info1)).perform(click());
        //LoginInstrumentedTest.smallSleep();
        onView(withId(R.id.join_button)).perform(click());

        Espresso.pressBack();
        //LoginInstrumentedTest.smallSleep();
        return;
    }


    public static void deleteTesterMeetings() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("meetings");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Meeting> meetingList = new ArrayList<>();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Meeting m = snap.getValue(Meeting.class);
                    if (m.getCreator().equals(Singleton.getInstance().getUsername())) {
                        meetingList.add(m);
                    }
                }


                for (int i = 0; i < meetingList.size(); i++) {
                    ref.child(meetingList.get(i).getMeetingID()).removeValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }


        });
    }



    public static class ClickAction {

        public static ViewAction clickItemButton(final int id) {

            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() { return null; }

                @Override
                public String getDescription() { return ""; }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }
}



