package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddTaskActivity extends AppCompatActivity {
    private Button btn_add_task, btn_cancel;
    private EditText edt_name, edt_desc, edt_date, edt_reminder;
    private DatePickerDialog.OnDateSetListener duedatedateSetListener;
    private TimePickerDialog.OnTimeSetListener duedatetimeSetListener;
    private DatePickerDialog.OnDateSetListener reminderdateSetListener;
    private TimePickerDialog.OnTimeSetListener remindertimeSetListener;
    private String dateTime;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        btn_add_task = findViewById(R.id.btn_add_task);
        btn_cancel = findViewById(R.id.btn_task_cancel);
        edt_date = findViewById(R.id.edt_due_date);
        edt_name = findViewById(R.id.edt_task_name);
        edt_desc = findViewById(R.id.edt_task_desc);
        edt_reminder = findViewById(R.id.edt_task_reminder);
        DbHandler db = new DbHandler(getApplicationContext());

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String duedate = edt_date.getText().toString();
                String subject = edt_name.getText().toString();
                String description = edt_desc.getText().toString();
                String reminderDate= edt_reminder.getText().toString();
                int notification_id = 0;
                String notificationDate = reminderDate;
                if (!duedate.isEmpty() && !subject.isEmpty() && !description.isEmpty()) {
                    if (!reminderDate.isEmpty()){
                        //Set reminder
                        reminderDate = reminderDate.replace('/', ',').replace(':', ',').replace(' ', ',');
                        String[] dateComponents = reminderDate.split(",");

                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateComponents[0]));
                        cal.set(Calendar.MONTH, Integer.parseInt(dateComponents[1])-1);
                        cal.set(Calendar.YEAR, Integer.parseInt(dateComponents[2]));
                        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateComponents[3]));
                        cal.set(Calendar.MINUTE, Integer.parseInt(dateComponents[4]));

                        Intent myIntent1 = new Intent(getApplicationContext(), AlarmReceiver.class);
                        UUID unique_id = UUID.randomUUID();
                        notification_id = unique_id.hashCode();
                        myIntent1.putExtra("notification_id", notification_id);
                        myIntent1.putExtra("subject", subject);
                        myIntent1.putExtra("description", description);
                        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), notification_id, myIntent1,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager1.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent1);
                        System.out.println(cal.getTime());
                        System.out.println(cal.getTimeInMillis());
                        System.out.println(notificationDate);
                        System.out.println(notification_id);
                    }
                    db.insertTaskDetails(subject, description, duedate, notificationDate, notification_id);

                    Toast.makeText(AddTaskActivity.this, "New Task added", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(AddTaskActivity.this, "Missing fields", Toast.LENGTH_LONG).show();
                }

            }
        });

        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate currentDate = null;
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, duedatedateSetListener, day, month, year);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

            }
        });
        duedatedateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                dateTime = date+"\n";

                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog duedatetimePickerDialog = new TimePickerDialog(AddTaskActivity.this, duedatetimeSetListener, hour, minute, true);
                duedatetimePickerDialog.show();
            }

        };
        duedatetimeSetListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String hour = String.valueOf(i);
                String minute = String.valueOf(i1);
                if (hour.length()==1)
                {
                    hour = "0"+hour;
                }
                if (minute.length()==1)
                {
                    minute = "0"+minute;
                }
                String time = hour+":"+minute;
                dateTime += time;
                edt_date.setText(dateTime);
            }
        };

        edt_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, reminderdateSetListener, day, month, year);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

            }
        });
        reminderdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                dateTime = date;

                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, remindertimeSetListener, hour, minute, true);
                timePickerDialog.show();
            }

        };
        remindertimeSetListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String hour = String.valueOf(i);
                String minute = String.valueOf(i1);
                if (hour.length()==1)
                {
                    hour = "0"+hour;
                }
                if (minute.length()==1)
                {
                    minute = "0"+minute;
                }
                String time = hour+":"+minute;
                dateTime += " " + time;
                edt_reminder.setText(dateTime);
            }
        };
    }
}