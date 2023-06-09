package com.example.journalapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isNotFocusable;
import static androidx.test.espresso.matcher.ViewMatchers.isNotSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ViewCompleteTaskItemTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityActivityScenarioRule= new ActivityScenarioRule<MainActivity>(MainActivity.class);
    @Before
    public void setUp() throws Exception {
        onView(withText("Tasks")).perform(click());
        onView(withId(R.id.btn_completedTasks)).perform(click());
        onView(withId(R.id.recyclerView_completetask)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFieldsArePopulated(){
        onView(withId(R.id.edt_view_completeTaskduedate)).check(matches(not(withText(isEmptyString()))));
        onView(withId(R.id.edt_view_completeTaskname)).check(matches(not(withText(isEmptyString()))));
        onView(withId(R.id.edt_view_completeTaskdesc)).check(matches(not(withText(isEmptyString()))));
        onView(withId(R.id.edt_view_completeTaskReminder)).check(matches(not(withText(isEmptyString()))));
    }

    @Test
    public void testFieldsNotEditable(){
        onView(withId(R.id.edt_view_completeTaskduedate)).perform(click());
        onView(withId(R.id.edt_view_completeTaskduedate)).check(matches(isNotSelected()));
        onView(withId(R.id.edt_view_completeTaskname)).perform(click());
        onView(withId(R.id.edt_view_completeTaskname)).check(matches(isNotSelected()));
        onView(withId(R.id.edt_view_completeTaskdesc)).perform(click());
        onView(withId(R.id.edt_view_completeTaskdesc)).check(matches(isNotSelected()));
        onView(withId(R.id.edt_view_completeTaskReminder)).perform(click());
        onView(withId(R.id.edt_view_completeTaskReminder)).check(matches(isNotSelected()));
    }

    @Test
    public void testRestore(){
        ActivityScenario scenario = activityActivityScenarioRule.getScenario();
        onView(withId(R.id.btn_restore_task)).perform(click());
        assertTrue(scenario.getState() == Lifecycle.State.RESUMED);

    }
}