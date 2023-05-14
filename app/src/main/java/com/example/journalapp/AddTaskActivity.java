package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {
    private Button btn_add_task, btn_cancel;
    private EditText edt_name, edt_desc, edt_date;
    private DatePickerDialog.OnDateSetListener dateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        btn_add_task = findViewById(R.id.btn_add_task);
        btn_cancel = findViewById(R.id.btn_task_cancel);
        edt_date = findViewById(R.id.edt_due_date);
        edt_name = findViewById(R.id.edt_task_name);
        edt_desc = findViewById(R.id.edt_task_desc);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = edt_date.getText().toString();
                String name = edt_name.getText().toString();
                String desc = edt_desc.getText().toString();
                if (!date.isEmpty() && !name.isEmpty() && !desc.isEmpty()) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("date", date);
                    returnIntent.putExtra("name", name);
                    returnIntent.putExtra("desc", desc);
                    setResult(RESULT_OK, returnIntent);
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
                        android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener, day, month, year);
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                edt_date.setText(date);
            }
        };
    }
}