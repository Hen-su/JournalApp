package com.example.journalapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CompleteTasksFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule= new ActivityScenarioRule<MainActivity>(MainActivity.class);
    @Before
    public void setUp() throws Exception {
        onView(withText("Tasks")).perform(click());
        onView(withId(R.id.btn_completedTasks)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testShowActiveButton(){
        onView(withId(R.id.btn_ActiveTasks)).perform(click());
        onView(withId(R.id.frag_task)).check(matches(isDisplayed()));
    }
    //Require completed view to be added
    @Test
    public void testRecyclerViewItem() throws InterruptedException {
        onView(withId(R.id.recyclerView_completetask)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(1000);
        onView(withId(R.id.act_view_completed)).check(matches(isDisplayed()));
    }
}