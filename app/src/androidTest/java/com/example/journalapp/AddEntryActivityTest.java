package com.example.journalapp;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Type;

public class AddEntryActivityTest {
    @Rule
    public ActivityScenarioRule<AddEntryActivity> activityScenarioRule= new ActivityScenarioRule<AddEntryActivity>(AddEntryActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCancelButton() throws InterruptedException {
        ActivityScenario<AddEntryActivity> scenario = activityScenarioRule.getScenario();
        onView(withId(R.id.btn_cancel)).perform(click());
        Thread.sleep(1000);
        assertTrue(scenario.getState() == (Lifecycle.State.DESTROYED));
    }

    @Test
    public void testBlankFields() throws InterruptedException {
        onView(withId(R.id.edt_new_subject)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.edt_new_entry)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.btn_add_entry)).perform(click());
        closeSoftKeyboard();
        Thread.sleep(1000);
        onView(withId(R.id.btn_add_entry)).check(matches(isDisplayed()));
    }

    @Test
    public void testValidFields(){
        ActivityScenario<AddEntryActivity> scenario = activityScenarioRule.getScenario();
        onView(withId(R.id.edt_new_subject)).perform(typeText("Entry Subject"));
        onView(withId(R.id.edt_new_entry)).perform(typeText("Entry description"));
        onView(withId(R.id.btn_add_entry)).perform(click());
        assertTrue(scenario.getState() == (Lifecycle.State.DESTROYED));
    }
}