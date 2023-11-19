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
public class SignUpInstrumentedTest {

    @Rule public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testValidEmailCode() {

        onView(withId(R.id.sign_up)).perform(click());
        LoginInstrumentedTest.smallSleep();

        onView(withId(R.id.email))
                .perform(typeText(LoginInstrumentedTest.EMAIL), closeSoftKeyboard());

        onView(withId(R.id.send_code)).perform(click());





    }
}
