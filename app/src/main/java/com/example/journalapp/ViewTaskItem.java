package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class ViewTaskItem extends AppCompatActivity {
    EditText edt_duedate, edt_subject, edt_description;
    Button btn_cancel, btn_delete;
    Bundle intentBundle;
    int taskID;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_item);

        intentBundle = getIntent().getExtras();
        taskID = intentBundle.getInt("id");

        DbHandler db = new DbHandler(this);
        HashMap<String, String> entry = db.getSingleTask(taskID);

        edt_duedate = findViewById(R.id.edt_view_duedate);
        edt_subject = findViewById(R.id.edt_view_taskname);
        edt_description = findViewById(R.id.edt_view_taskdesc);
        btn_cancel = findViewById(R.id.btn_view_task_cancel);
        btn_delete = findViewById(R.id.btn_delete_task);

        edt_duedate.setText(entry.get("date"));
        edt_subject.setText(entry.get("subject"));
        edt_description.setText(entry.get("description"));
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
            }
        });
    }
}