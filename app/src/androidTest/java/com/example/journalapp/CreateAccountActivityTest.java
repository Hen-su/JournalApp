package com.example.journalapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CreateAccountActivityTest {
    @Rule
    public ActivityScenarioRule<CreateAccountActivity> activityActivityScenarioRule= new ActivityScenarioRule<CreateAccountActivity>(CreateAccountActivity.class);
    CreateAccountActivity createAccountActivity;
    String message = "testings";
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testValidInputs(){
        ActivityScenario<CreateAccountActivity> scenario = ActivityScenario.launchActivityForResult(CreateAccountActivity.class);
        onView(withId(R.id.edt_new_name)).perform(typeText(message));
        onView(withId(R.id.edt_new_email)).perform(typeText(message));
        onView(withId(R.id.edt_new_password)).perform(typeText(message));
        onView(withId(R.id.edt_confirm_pass)).perform(typeText(message));
        closeSoftKeyboard();
        onView(withId(R.id.btn_create_acc)).perform(click());
        assertTrue(scenario.getResult().getResultCode() == Activity.RESULT_CANCELED);
    }

    @Test
    public void passwordMatch(){
        onView(withId(R.id.edt_new_name)).perform(typeText(message));
        onView(withId(R.id.edt_new_email)).perform(typeText(message));
        onView(withId(R.id.edt_new_password)).perform(typeText(message));
        onView(withId(R.id.edt_confirm_pass)).perform(typeText("test"));
        closeSoftKeyboard();
        onView(withId(R.id.btn_create_acc)).perform(click());
        onView(withId(R.id.act_create_account)).check(matches(isDisplayed()));
    }

    @Test
    public void passwordLength(){
        ActivityScenario<CreateAccountActivity> scenario = ActivityScenario.launchActivityForResult(CreateAccountActivity.class);
        onView(withId(R.id.edt_new_name)).perform(typeText(message));
        onView(withId(R.id.edt_new_email)).perform(typeText(message));
        onView(withId(R.id.edt_new_password)).perform(typeText("test"));
        onView(withId(R.id.edt_confirm_pass)).perform(typeText("test"));
        closeSoftKeyboard();
        onView(withId(R.id.btn_create_acc)).perform(click());
        onView(withId(R.id.act_create_account)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        createAccountActivity = null;
    }
}