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
    Button btn_cancel, btn_update_entry, btn_delete;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry_item);

        intentBundle = getIntent().getExtras();
        entryID = intentBundle.getInt("id");

        DbHandler db = new DbHandler(this);
        EntryItem entry = db.getSingleEntry(entryID);

        txt_date = findViewById(R.id.txt_view_current_date);
        edt_subject = findViewById(R.id.edt_view_subject);
        edt_description = findViewById(R.id.edt_view_entry);
        btn_cancel = findViewById(R.id.btn_view_entry_cancel);
        btn_delete = findViewById(R.id.btn_delete_entry);

        txt_date.setText(entry.getDate());
        edt_subject.setText(entry.getSubject());
        edt_description.setText(entry.getEntry());
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete(view);
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

    public void confirmDelete(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(ViewEntryItem.this);
        builder.setTitle("Confirm Delete")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbHandler db = new DbHandler(ViewEntryItem.this);
                        db.deleteEntry(entryID);
                        finish();
                        Toast.makeText(ViewEntryItem.this, "Entry has been deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}