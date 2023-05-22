package com.example.journalapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import android.app.Activity;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityActivityScenarioRule= new ActivityScenarioRule<LoginActivity>(LoginActivity.class);
    LoginActivity loginActivity;
    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void testInvalidlogin(){
        onView(withId(R.id.edt_email)).perform(typeText("testings"));
        onView(withId(R.id.edt_password)).perform(typeText("test"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void testBlanklogin(){
        onView(withId(R.id.edt_email)).perform(typeText(""));
        onView(withId(R.id.edt_password)).perform(typeText(""));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void testForgotPassword(){
        onView(withId(R.id.btn_forgot_password)).perform(click());
        onView(withId(R.id.act_forgot_password)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccount(){
        onView(withId(R.id.btn_create_acc)).perform(click());
        onView(withId(R.id.act_create_account)).check(matches(isDisplayed()));
    }

    @Test
    public void testValidlogin(){
        onView(withId(R.id.edt_email)).perform(typeText("testings"));
        onView(withId(R.id.edt_password)).perform(typeText("testings"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.main)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }
}