package com.example.journalapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class EntryFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule= new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFloatingButton(){
        onView(withId(R.id.fab_add)).perform(click());
        onView(withId(R.id.act_add_entry)).check(matches(isDisplayed()));
    }
    //Requires entry to be added
    @Test
    public void testRecyclerViewItemClick(){
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.act_view_entry)).check(matches(isDisplayed()));
    }
}