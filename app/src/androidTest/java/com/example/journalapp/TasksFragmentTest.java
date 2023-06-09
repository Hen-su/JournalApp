package com.example.journalapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.RowId;

public class TasksFragmentTest {
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
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.act_add_task)).check(matches(isDisplayed()));
    }

    @Test
    public void testCompletedTaskButton(){
        onView(withId(R.id.btn_completedTasks)).perform(click());
        onView(withId(R.id.frag_comp)).check(matches(isDisplayed()));
    }
    //Require task to be added
    @Test
    public void testRecyclerViewItemClick(){
        onView(withId(R.id.recyclerView_task)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.act_view_task)).check(matches(isDisplayed()));
    }
}