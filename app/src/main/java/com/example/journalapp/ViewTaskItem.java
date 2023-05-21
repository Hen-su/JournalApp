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
    Button btn_cancel;
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

        edt_duedate.setText(entry.get("date"));
        edt_subject.setText(entry.get("subject"));
        edt_description.setText(entry.get("description"));
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void updateTask(View view){
        DbHandler db = new DbHandler(ViewTaskItem.this);
        String subject = edt_subject.getText().toString();
        String description = edt_description.getText().toString();
        String duedate = edt_duedate.getText().toString();
        db.updateTask(taskID, duedate, subject, description);
        Toast.makeText(this, "Task "+taskID+" Updated", Toast.LENGTH_SHORT).show();
    }

    public void deleteTask(View view){
        DbHandler db = new DbHandler(ViewTaskItem.this);
        db.deleteEntry(taskID);
        finish();
        Toast.makeText(this, "Entry "+taskID+" Deleted", Toast.LENGTH_SHORT).show();
    }
}