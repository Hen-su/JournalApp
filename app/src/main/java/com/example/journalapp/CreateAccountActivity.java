package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateAccountActivity extends AppCompatActivity {

    EditText edt_name;
    EditText edt_email;
    EditText edt_password;
    EditText edt_confirm_pass;
    Button btn_create_acc;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        edt_name = findViewById(R.id.edt_new_name);
        edt_email = findViewById(R.id.edt_new_email);
        edt_confirm_pass = findViewById(R.id.edt_confirm_pass);
        edt_password = findViewById(R.id.edt_new_password);
        btn_create_acc = findViewById(R.id.btn_create_acc);

        btn_create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edt_name.getText().toString();
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                if (password.length() >= 8){
                    String hashedPass;
                    try {
                        hashedPass = encryptString(password);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    String confirmPassword = edt_confirm_pass.getText().toString();

                    if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(CreateAccountActivity.this, "Missing fields", Toast.LENGTH_LONG).show();
                    }
                    else if (!password.equals(confirmPassword)) {
                        Toast.makeText(CreateAccountActivity.this, "The passwords don't match", Toast.LENGTH_LONG).show();
                    }
                    else {
                        DbHandler db = new DbHandler(CreateAccountActivity.this);
                        db.insertUserDetails(name, email, hashedPass);

                        Toast.makeText(CreateAccountActivity.this, "New User has been created", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else {
                    Toast.makeText(CreateAccountActivity.this, "Password must contain minimum 8 characters", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private String encryptString(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger bigInt = new BigInteger(1, messageDigest);
        return bigInt.toString(16);
    }
}