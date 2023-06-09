package com.example.journalapp;

import static org.junit.Assert.*;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivityTest {
    String email;
    String password;
    DbHandler db;
    Context context;

    @Before
    public void setUp() throws Exception {
         email = "testings";
         password  = "testings";
         db = new DbHandler(context);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public String encryptString(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(this.password.getBytes());
        BigInteger bigInt = new BigInteger(1, messageDigest);
        assertNotSame(this.password, bigInt.toString());
        return bigInt.toString();
    }
    @Test
    public void validateLogin() throws NoSuchAlgorithmException {
        String hashedPass = encryptString(password);
        boolean validation = db.validateLogin(email, hashedPass);
        assertEquals(true, validation);
    }
}