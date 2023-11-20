package com.example.talk2friends;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

import static org.junit.Assert.fail;


import androidx.test.espresso.NoMatchingViewException;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginInstrumentedTest {

    public static final String USERNAME = "jtest";
    public static final String EMAIL = "jtest@usc.edu";
    private static final String STRING_PASSWORD = "1234";
    private static final String STRING_WRONG_PASSWORD = "0000";
    private static final String STRING_MAIN_ACTIVITY_DISPLAY_TEXT = "Welcome jtest!";

    @Rule public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testValidLogin() {
        onView(withId(R.id.username))
                .perform(typeText(EMAIL), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText(STRING_PASSWORD), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());

        bigSleep();

        onView(withId(R.id.home_page)).check(matches(withText(STRING_MAIN_ACTIVITY_DISPLAY_TEXT)));
    }

    @Test
    public void testInvalidLogin() {
        onView(withId(R.id.username))
                .perform(typeText(EMAIL), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText(STRING_WRONG_PASSWORD), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());

        bigSleep();

        try {
            onView(withId(R.id.password)).check(matches(withText(STRING_WRONG_PASSWORD)));;
        }
        catch (NoMatchingViewException e) {
            fail("User has successfully logged in with bad password");
        }

    }

    public static void login() {
        onView(withId(R.id.username))
                .perform(typeText(EMAIL), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(STRING_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());

        smallSleep();
    }

    public static void smallSleep() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void bigSleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


}
