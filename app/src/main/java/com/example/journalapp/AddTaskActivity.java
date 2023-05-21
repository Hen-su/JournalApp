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

import java.time.LocalDate;
import java.util.Calendar;

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
                if (!duedate.isEmpty() && !subject.isEmpty() && !description.isEmpty()) {
                    DbHandler db = new DbHandler(getApplicationContext());
                    db.insertTaskDetails(subject, description, duedate);
                    if (edt_reminder.getText().toString()  != null){
                        String dateTime = edt_reminder.getText().toString();
                        dateTime.replace('-', ',').replace(':', ',');
                        String[] dateComponents = dateTime.split(",");
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateComponents[0]));
                        cal.set(Calendar.MONTH, Integer.parseInt(dateComponents[1]));
                        cal.set(Calendar.YEAR, Integer.parseInt(dateComponents[2]));
                        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateComponents[3]));
                        cal.set(Calendar.MINUTE, Integer.parseInt(dateComponents[4]));

                        Intent myIntent1 = new Intent(getApplicationContext(), AlarmReceiver.class);
                        myIntent1.putExtra("subject", subject);
                        myIntent1.putExtra("description", description);
                        myIntent1.putExtra("duedate", duedate);
                        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 123, myIntent1,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager1.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+3000, pendingIntent1);
                        db.insertTaskDetails(subject, description, duedate);
                    }
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
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, duedatedateSetListener, day, month, year);
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
                String time = i+":"+i1;
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
                dialog.show();

            }
        });
        reminderdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                dateTime = date+"\n";

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
                String time = i+":"+i1;
                dateTime += " " + time;
                edt_reminder.setText(dateTime);
            }
        };
    }
}