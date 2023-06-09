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

    public void setUser(User userData){
        editor.putInt(KEY_ID, userData.getId());
        editor.putString(KEY_NAME, userData.getName());
        editor.commit();
    }

    public void clearEditor(){
        editor.clear();
    }
    public boolean loggedIn(){
        return prefs.getBoolean("loggedInMode", false);
    }
}
