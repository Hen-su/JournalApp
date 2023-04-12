package com.example.journalapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class LoginActivity extends Activity {
    EditText edtEmail, edtPassword;
    Button btnLogin, btnCreateAcc, btnForgotPassword;
    String userEmail, userName, userPassword;

    public static final int REQUEST_CODE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            userEmail = data.getStringExtra("email");
            userName = data.getStringExtra("name");
            userPassword = data.getStringExtra("password");
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnCreateAcc = findViewById(R.id.btn_create_acc);
        btnForgotPassword = findViewById(R.id.btn_forgot_password);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                try {
                    if (email.equals(userEmail) && password.equals(userPassword)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("name", userName);
                        startActivity(intent);
                    }
                }
                catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Invalid login", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
