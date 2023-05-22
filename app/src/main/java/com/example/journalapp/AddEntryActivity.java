package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddEntryActivity extends AppCompatActivity {

    Button btn_add_entry, btn_cancel;
    TextView txt_currentDate;
    EditText edt_subject, edt_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        btn_add_entry = findViewById(R.id.btn_add_entry);
        btn_cancel = findViewById(R.id.btn_cancel);
        txt_currentDate = findViewById(R.id.txt_current_date);
        edt_entry = findViewById(R.id.edt_new_entry);
        edt_subject = findViewById(R.id.edt_new_subject);

        String currentDate = getCurrentDate();
        txt_currentDate.setText(currentDate);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String finalFormattedDate = currentDate;
        btn_add_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = edt_subject.getText().toString();
                String description = edt_entry.getText().toString();
                addNewEntry(subject, description, finalFormattedDate);
            }
        });

    }

    private void addNewEntry(String subject, String description, String finalFormattedDate){
        //Returns user input to EntryFragment
        if (!subject.isEmpty() && !description.isEmpty()) {
            DbHandler db = new DbHandler(AddEntryActivity.this);
            db.insertEntryDetails(subject, description, finalFormattedDate);
            Toast.makeText(AddEntryActivity.this, "New entry added", Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            Toast.makeText(AddEntryActivity.this, "Missing fields", Toast.LENGTH_LONG).show();
        }
    }

    private String getCurrentDate(){
        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }
        DateTimeFormatter dateTimeFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateTimeFormatter = DateTimeFormatter.ofPattern("E, dd MMM YYYY");
        }
        String currentDate = date.toString();
        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedDate = date.format(dateTimeFormatter).toString();
        }
        return formattedDate;
    }
}