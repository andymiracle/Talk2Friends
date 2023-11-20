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
public class FriendInstrumentedTest {

    public static final String USERNAME2 = "ktest";
    public static final String PASSWORD2 = "1234";
    public static final String INVITEE_EMAIL1 = "invitee1115455@usc.edu";
    public static final String INVITEE_EMAIL2 = "invitee111115112@gmail.com";
    public static final String INVITEE_EMAIL3 = "in,!!!!!!vite@us.c.edu";
    public static final String INVITEE_EMAIL4 = "@@@@usc.@@";
    public static final String INVITEE_EMAIL5 = "@";
    public static final String INVITEE_EMAIL6 = "invite@gmail.com.com.com";

    public static final String INVALID_USER1 = "invalid_user";
    public static final String INVALID_USER2 = "bwiencko@usc.edu";
    public static final String INVALID_USER3 = " ";
    public static final String INVALID_USER4 = "1";
    public static final String INVALID_USER5 = "...";


    @Rule public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);


    @After
    public void setup() {
        rectifyFriendsDatabase();
    }

    @Test
    public void testSendFriendRequest() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("users").child(LoginInstrumentedTest.USERNAME).child("incomingRequests");
        ref1.removeValue();
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(USERNAME2).child("incomingRequests");
        ref2.removeValue();



        LoginInstrumentedTest.login();

        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.add_friends)).perform(click());

        onView(withId(R.id.username))
                .perform(typeText(USERNAME2), closeSoftKeyboard());
        onView(withId(R.id.request)).perform(click());

        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();

        onView(withId(R.id.username))
                .perform(replaceText(USERNAME2), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(replaceText(PASSWORD2), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.view_friend_request)).perform(click());

        onView(withId(R.id.recycler_view2))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    @Test
    public void testSendInvalidFriendRequest() {

        LoginInstrumentedTest.login();

        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.add_friends)).perform(click());

        onView(withId(R.id.username))
                .perform(replaceText(INVALID_USER1), closeSoftKeyboard());
        onView(withId(R.id.request)).perform(click());

        onView(withId(R.id.username))
                .perform(replaceText(INVALID_USER2), closeSoftKeyboard());
        onView(withId(R.id.request)).perform(click());

        onView(withId(R.id.username))
                .perform(replaceText(INVALID_USER3), closeSoftKeyboard());
        onView(withId(R.id.request)).perform(click());

        onView(withId(R.id.username))
                .perform(replaceText(INVALID_USER4), closeSoftKeyboard());
        onView(withId(R.id.request)).perform(click());

        onView(withId(R.id.username))
                .perform(replaceText(INVALID_USER5), closeSoftKeyboard());
        onView(withId(R.id.request)).perform(click());


    }

    @Test
    public void testAcceptFriendRequest() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("users").child(LoginInstrumentedTest.USERNAME).child("incomingRequests");
        ref1.removeValue();
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(USERNAME2).child("incomingRequests");
        ref2.removeValue();
        LoginInstrumentedTest.login();

        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.add_friends)).perform(click());

        onView(withId(R.id.username))
                .perform(typeText(USERNAME2), closeSoftKeyboard());
        onView(withId(R.id.request)).perform(click());

        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();

        onView(withId(R.id.username))
                .perform(replaceText(USERNAME2), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(replaceText(PASSWORD2), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.view_friend_request)).perform(click());

        onView(withId(R.id.recycler_view2))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, MeetingInstrumentedTest.ClickAction.clickItemButton(R.id.accept)));

        LoginInstrumentedTest.bigSleep();

        Espresso.pressBack();
        onView(withId(R.id.view_friends)).perform(click());
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));



    }

    @Test
    public void testDoubleFriendRequests() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("users").child(LoginInstrumentedTest.USERNAME).child("incomingRequests");
        ref1.removeValue();
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(USERNAME2).child("incomingRequests");
        ref2.removeValue();
        LoginInstrumentedTest.login();

        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.add_friends)).perform(click());

        onView(withId(R.id.username))
                .perform(typeText(USERNAME2), closeSoftKeyboard());

        onView(withId(R.id.request)).perform(click());
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();

        onView(withId(R.id.username))
                .perform(replaceText(USERNAME2), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(replaceText(PASSWORD2), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.add_friends)).perform(click());

        onView(withId(R.id.username))
                .perform(typeText(LoginInstrumentedTest.USERNAME), closeSoftKeyboard());
        onView(withId(R.id.request)).perform(click());

        LoginInstrumentedTest.smallSleep();

        Espresso.pressBack();
        onView(withId(R.id.view_friends)).perform(click());

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }


    @Test
    public void testSendInviteToApp() {
        LoginInstrumentedTest.login();

        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.invite_friends)).perform(click());

        onView(withId(R.id.email))
                .perform(replaceText(INVITEE_EMAIL1), closeSoftKeyboard());
        onView(withId(R.id.send_code)).perform(click());

        onView(withId(R.id.email))
                .perform(replaceText(INVITEE_EMAIL2), closeSoftKeyboard());
        onView(withId(R.id.send_code)).perform(click());

        onView(withId(R.id.email))
                .perform(replaceText(INVITEE_EMAIL3), closeSoftKeyboard());
        onView(withId(R.id.send_code)).perform(click());

        onView(withId(R.id.email))
                .perform(replaceText(INVITEE_EMAIL4), closeSoftKeyboard());
        onView(withId(R.id.send_code)).perform(click());

        onView(withId(R.id.email))
                .perform(replaceText(INVITEE_EMAIL5), closeSoftKeyboard());
        onView(withId(R.id.send_code)).perform(click());

        onView(withId(R.id.email))
                .perform(replaceText(INVITEE_EMAIL6), closeSoftKeyboard());
        onView(withId(R.id.send_code)).perform(click());
    }

    @Test
    public void testRemoveFriend_RemoverPerspective() {
        friendBothTesters();
        LoginInstrumentedTest.login();

        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.view_friends)).perform(click());

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, MeetingInstrumentedTest.ClickAction.clickItemButton(R.id.remove)));
        Espresso.pressBack();

        LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.view_friends)).perform(click());

        try {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            fail("Friend not properly removed");
        } catch (Exception e) {
            return;
        }

    }

    @Test
    public void testRemoveFriend_FriendPerspective(){
        friendBothTesters();
        LoginInstrumentedTest.login();

        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.view_friends)).perform(click());

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, MeetingInstrumentedTest.ClickAction.clickItemButton(R.id.remove)));
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();

        onView(withId(R.id.username))
                .perform(replaceText(USERNAME2), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(replaceText(PASSWORD2), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.view_friends)).perform(click());

        try {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            fail("Friend not properly removed");
        } catch (Exception e) {
            return;
        }

    }

    @Test
    public void testEditProfile_FriendPerspective() {
        friendBothTesters();
        LoginInstrumentedTest.login();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(LoginInstrumentedTest.USERNAME);
        userRef.child("age").removeValue();
        userRef.child("interests").removeValue();
        userRef.child("displayName").removeValue();
        userRef.child("affiliation").removeValue();

        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.id.edit)).perform(click());

        onView(withId(R.id.name_text))
                .perform(replaceText(ProfileInstrumentedTest.DISPLAY_NAME), closeSoftKeyboard());
        onView(withId(R.id.age_text))
                .perform(replaceText(Integer.toString(ProfileInstrumentedTest.AGE)), closeSoftKeyboard());
        onView(withId(R.id.affiliation_spinner)).perform(click());

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
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();


        onView(withId(R.id.username))
                .perform(replaceText(USERNAME2), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(replaceText(PASSWORD2), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.friends)).perform(click());
        onView(withId(R.id.view_friends)).perform(click());

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, MeetingInstrumentedTest.ClickAction.clickItemButton(R.id.details)));

        LoginInstrumentedTest.bigSleep();

        onView(withId(R.id.name_text)).check(matches(withText(ProfileInstrumentedTest.DISPLAY_NAME)));
        onView(withId(R.id.age_text)).check(matches(withText(Integer.toString(ProfileInstrumentedTest.AGE))));
        onView(withId(R.id.affiliation_text)).check(matches(withText(ProfileInstrumentedTest.AFFILIATION)));
        onView(withId(R.id.interests_text)).check(matches(withText("Anime, Traveling")));


    }

    public static void rectifyFriendsDatabase() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("users").child(LoginInstrumentedTest.USERNAME);

        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                u.setFriends(new ArrayList<>());
                u.setIncomingRequests(new ArrayList<>());
                DatabaseUtil.saveUser(u);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("NO DATA");
            }

        });

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(USERNAME2);

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                u.setFriends(new ArrayList<>());
                u.setIncomingRequests(new ArrayList<>());
                DatabaseUtil.saveUser(u);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("NO DATA");
            }

        });

        LoginInstrumentedTest.bigSleep();

    }

    public static void friendBothTesters() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("users").child(LoginInstrumentedTest.USERNAME);

        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);

                ArrayList<String> friends = new ArrayList<>();
                friends.add(USERNAME2);
                u.setFriends(friends);
                u.setIncomingRequests(new ArrayList<>());
                DatabaseUtil.saveUser(u);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("NO DATA");
            }

        });

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users").child(USERNAME2);

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);

                ArrayList<String> friends = new ArrayList<>();
                friends.add(LoginInstrumentedTest.USERNAME);
                u.setFriends(friends);
                u.setIncomingRequests(new ArrayList<>());
                DatabaseUtil.saveUser(u);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("NO DATA");
            }

        });

        LoginInstrumentedTest.bigSleep();

    }
}
