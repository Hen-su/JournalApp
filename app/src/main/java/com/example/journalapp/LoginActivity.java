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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends Activity {
    EditText edtEmail, edtPassword;
    Button btnLogin, btnCreateAcc, btnForgotPassword;
    String userEmail, userName, userPassword;
    DbHandler db;
    Session session;

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
        db = new DbHandler(this);
        session = new Session(this);

        if(session.loggedIn()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
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
                try {
                    String email = edtEmail.getText().toString();
                    String password = edtPassword.getText().toString();
                    Login(email, password);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public void Login(String email, String password) throws NoSuchAlgorithmException {
        Boolean validation = validateLogin(password, email);
        if (validation){
            session.setLoggedIn(true);
            session.setUser(db.getUser(email));
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        else{
            Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show();
        }
    }

    public String encryptString(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger bigInt = new BigInteger(1, messageDigest);
        return bigInt.toString(16);
    }

    private boolean validateLogin(String password, String email) throws NoSuchAlgorithmException {
        String hashedPass = encryptString(password);
        boolean validation = db.validateLogin(email, hashedPass);
        return validation;
    }
}
