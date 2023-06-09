package com.example.journalapp;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import android.widget.DatePicker;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.KeyEventAction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.security.Key;
import java.util.Calendar;

public class AddTaskActivityTest {
    Calendar cal;
    int year, month, day;
    @Rule
    public ActivityScenarioRule<AddTaskActivity> activityScenarioRule= new ActivityScenarioRule<AddTaskActivity>(AddTaskActivity.class);
    UiDevice uiDevice;
    @Before
    public void setUp() throws Exception {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCancelButton() throws InterruptedException {
        ActivityScenario<AddTaskActivity> scenario = activityScenarioRule.getScenario();
        onView(withId(R.id.btn_cancel)).perform(click());
        Thread.sleep(1000);
        assertTrue(scenario.getState() == (Lifecycle.State.DESTROYED));
    }

    @Test
    public void testDateTimePickers() throws UiObjectNotFoundException {
        onView(withId(R.id.edt_due_date)).perform(click());
        UiObject dateButton = uiDevice.findObject(new UiSelector().text("OK"));
        if (dateButton.exists() && dateButton.isEnabled()) {
            dateButton.click();
        }
        UiObject timeButton = uiDevice.findObject(new UiSelector().text("OK"));
        if (timeButton.exists() && timeButton.isEnabled()) {
            timeButton.click();
        }
        onView(withId(R.id.edt_due_date)).check(matches(not(withText(emptyOrNullString()))));

        onView(withId(R.id.edt_task_reminder)).perform(click());
        UiObject dateButton1 = uiDevice.findObject(new UiSelector().text("OK"));
        if (dateButton1.exists() && dateButton1.isEnabled()) {
            dateButton1.click();
        }
        UiObject timeButton1 = uiDevice.findObject(new UiSelector().text("OK"));
        if (timeButton1.exists() && timeButton1.isEnabled()) {
            timeButton1.click();
        }
        onView(withId(R.id.edt_task_reminder)).check(matches(not(withText(emptyOrNullString()))));
    }

    @Test
    public void testBlankFields() throws InterruptedException {
        ActivityScenario<AddTaskActivity> scenario = activityScenarioRule.getScenario();
        onView(withId(R.id.edt_due_date)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.edt_task_name)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.edt_task_desc)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.edt_task_reminder)).perform(click());
        closeSoftKeyboard();
        Thread.sleep(1000);
        assertTrue(scenario.getState() != Lifecycle.State.DESTROYED);
    }

    @Test
    public void testValidFields() throws InterruptedException, UiObjectNotFoundException {
        ActivityScenario<AddTaskActivity> scenario = activityScenarioRule.getScenario();
        onView(withId(R.id.edt_due_date)).perform(click());

        UiObject dateButton1 = uiDevice.findObject(new UiSelector().text("OK"));
        if (dateButton1.exists() && dateButton1.isEnabled()) {
            dateButton1.click();
        }
        UiObject timeButton1 = uiDevice.findObject(new UiSelector().text("OK"));
        if (timeButton1.exists() && timeButton1.isEnabled()) {
            timeButton1.click();
        }
        onView(withId(R.id.edt_task_name)).perform(click()).perform(typeText("Task"));
        closeSoftKeyboard();
        onView(withId(R.id.edt_task_desc)).perform(click()).perform(typeText("TaskDescription"));
        closeSoftKeyboard();
        onView(withId(R.id.edt_task_reminder)).perform(click());
        UiObject dateButton2 = uiDevice.findObject(new UiSelector().text("OK"));
        if (dateButton1.exists() && dateButton1.isEnabled()) {
            dateButton1.click();
        }
        UiObject timeButton2 = uiDevice.findObject(new UiSelector().text("OK"));
        if (timeButton1.exists() && timeButton1.isEnabled()) {
            timeButton1.click();
        }
        closeSoftKeyboard();
        Thread.sleep(1000);
        assertTrue(scenario.getState() == Lifecycle.State.DESTROYED);
    }
}