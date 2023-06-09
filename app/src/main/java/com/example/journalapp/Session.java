package com.example.journalapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    Context context;

    public Session(Context context){
        this.context = context;
        prefs = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedIn(boolean loggedIn){
        editor.putBoolean("loggedInMode", loggedIn);
        editor.commit();
    }

    public void setUser(HashMap<String, String> userData){
        editor.putString(KEY_ID, userData.get("id"));
        editor.putString(KEY_NAME, userData.get("name"));
        editor.commit();
    }

    public HashMap<String, String> getUser(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_ID, prefs.getString(KEY_ID, null));
        user.put(KEY_NAME, prefs.getString(KEY_NAME, null));
        return user;
    }
    public boolean loggedIn(){
        return prefs.getBoolean("loggedInMode", false);
    }
}
