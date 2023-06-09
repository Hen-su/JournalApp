package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewCompleteTaskItem extends AppCompatActivity {
    EditText edt_duedate, edt_subject, edt_description, edt_reminder;
    Button btn_cancel, btn_restore;
    Bundle intentBundle;
    int taskID;
    DbHandler db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complete_task_item);

        intentBundle = getIntent().getExtras();
        taskID = intentBundle.getInt("id");

        db = new DbHandler(this);
        TaskItem task = db.getSingleTask(taskID);

        edt_duedate = findViewById(R.id.edt_view_completeTaskduedate);
        edt_subject = findViewById(R.id.edt_view_completeTaskname);
        edt_description = findViewById(R.id.edt_view_completeTaskdesc);
        edt_reminder = findViewById(R.id.edt_view_completeTaskReminder);
        btn_cancel = findViewById(R.id.btn_view_completeTask_cancel);
        btn_restore = findViewById(R.id.btn_restore_task);

        edt_duedate.setKeyListener(null);
        edt_subject.setKeyListener(null);
        edt_description.setKeyListener(null);
        edt_reminder.setKeyListener(null);

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

        btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.restoreTask(taskID);
                finish();
                Toast.makeText(ViewCompleteTaskItem.this, "Task Restored", Toast.LENGTH_SHORT).show();
            }
        });
    }
}