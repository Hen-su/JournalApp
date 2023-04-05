package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateAccountActivity extends AppCompatActivity {

    EditText edt_name;
    EditText edt_email;
    EditText edt_password;
    Button btn_create_acc;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        edt_name = findViewById(R.id.edt_new_name);
        edt_email = findViewById(R.id.edt_new_email);
        edt_password = findViewById(R.id.edt_new_password);
        btn_create_acc = findViewById(R.id.btn_create_acc);
        btn_create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}