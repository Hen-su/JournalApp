package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class ViewTaskItem extends AppCompatActivity {
    EditText edt_duedate, edt_subject, edt_description, edt_reminder;
    Button btn_cancel, btn_update, btn_delete;
    Bundle intentBundle;
    int taskID;
    private String dateTime;
    private DatePickerDialog.OnDateSetListener duedatedateSetListener;
    private TimePickerDialog.OnTimeSetListener duedatetimeSetListener;
    private DatePickerDialog.OnDateSetListener reminderdateSetListener;
    private TimePickerDialog.OnTimeSetListener remindertimeSetListener;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_item);

        intentBundle = getIntent().getExtras();
        taskID = intentBundle.getInt("id");

        DbHandler db = new DbHandler(this);
        TaskItem task = db.getSingleTask(taskID);

        edt_duedate = findViewById(R.id.edt_view_duedate);
        edt_subject = findViewById(R.id.edt_view_taskname);
        edt_description = findViewById(R.id.edt_view_taskdesc);
        edt_reminder=findViewById(R.id.edt_view_taskreminder);
        btn_cancel = findViewById(R.id.btn_view_task_cancel);
        btn_delete = findViewById(R.id.btn_delete_task);
        btn_update = findViewById(R.id.btn_view_update_task);

        edt_duedate.setText(task.getDate());
        edt_subject.setText(task.getName());
        edt_description.setText(task.getDescription());
        edt_reminder.setText(task.getReminderDate());

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteTask(taskID);
                finish();
                Toast.makeText(ViewTaskItem.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHandler dbHandler = new DbHandler(ViewTaskItem.this);
                String dueDate = edt_duedate.getText().toString();
                String subject = edt_subject.getText().toString();
                String description = edt_description.getText().toString();
                String reminderDate = edt_reminder.getText().toString();
                String notificationDate = reminderDate;
                if (!dueDate.isEmpty() && !subject.isEmpty() && !description.isEmpty()) {
                    DbHandler db = new DbHandler(getApplicationContext());
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
                        int notification_id = task.getNotificationID();
                        if (notification_id == 0)
                        {
                            UUID unique_id = UUID.randomUUID();
                            notification_id = unique_id.hashCode();
                        }
                        myIntent1.putExtra("notification_id", notification_id);
                        myIntent1.putExtra("subject", subject);
                        myIntent1.putExtra("description", description);
                        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), notification_id, myIntent1,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager1.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent1);
                    }
                    Toast.makeText(ViewTaskItem.this, "Task updated", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(ViewTaskItem.this, "Missing fields", Toast.LENGTH_LONG).show();
                }
                dbHandler.updateTask(taskID, dueDate, subject, description, notificationDate, task.getNotificationID());
            }
        });

        edt_duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate currentDate = null;
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ViewTaskItem.this,
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

                TimePickerDialog duedatetimePickerDialog = new TimePickerDialog(ViewTaskItem.this, duedatetimeSetListener, hour, minute, true);
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
                edt_duedate.setText(dateTime);
            }
        };

        edt_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ViewTaskItem.this,
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(ViewTaskItem.this, remindertimeSetListener, hour, minute, true);
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