package com.example.journalapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class ViewEntryItem extends AppCompatActivity {
    Bundle intentBundle;
    int entryID;
    TextView txt_date;
    EditText edt_subject, edt_description;
    Button btn_cancel, btn_update_entry;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry_item);

        intentBundle = getIntent().getExtras();
        entryID = intentBundle.getInt("id");

        DbHandler db = new DbHandler(this);
        HashMap<String, String> entry = db.getSingleEntry(entryID);

        txt_date = findViewById(R.id.txt_view_current_date);
        edt_subject = findViewById(R.id.edt_view_subject);
        edt_description = findViewById(R.id.edt_view_entry);
        btn_cancel = findViewById(R.id.btn_view_entry_cancel);

        txt_date.setText(entry.get("date"));
        edt_subject.setText(entry.get("subject"));
        edt_description.setText(entry.get("description"));
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void updateEntry(View view){
        DbHandler db = new DbHandler(ViewEntryItem.this);
        String subject = edt_subject.getText().toString();
        String description = edt_description.getText().toString();
        db.updateEntry(entryID, subject, description);
        Toast.makeText(this, "Entry "+entryID+" Updated", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void deleteEntry(View view){
        DbHandler db = new DbHandler(ViewEntryItem.this);
        db.deleteEntry(entryID);
        finish();
        Toast.makeText(this, "Entry "+entryID+" Deleted", Toast.LENGTH_SHORT).show();
    }

    public void confirmDelete(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        AlertDialog alert = builder.create();
        builder.setTitle("Confirm Delete")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEntry(view);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alert.cancel();
                    }
                });
        alert.show();
    }
}