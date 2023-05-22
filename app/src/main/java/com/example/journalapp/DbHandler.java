package com.example.journalapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "journalDB";
    private static final String TABLE_USERS = "userTable";
    private static final String TABLE_ENTRIES = "entriesTable";
    private static final String TABLE_TASKS = "tasksTable";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "name";
    private static final String KEY_USEREMAIL = "email";
    private static final String KEY_USERPASSWORD = "password";
    private static final String KEY_ENTRYSUB = "entrySubject";
    private static final String KEY_ENTRYDESC = "entryDescription";
    private static final String KEY_ENTRYDATE = "entryDate";
    private static final String KEY_TASKSUB = "taskSubject";
    private static final String KEY_TASKDESC = "taskDescription";
    private static final String KEY_TASKDUEDATE = "taskDueDate";
    private static final String KEY_TASKSTATUS = "taskStatus";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERTABLE = " CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USERNAME + " TEXT," + KEY_USEREMAIL + " TEXT," + KEY_USERPASSWORD + " TEXT" + ")";
        String CREATE_ENTRIESTABLE = " CREATE TABLE " + TABLE_ENTRIES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ENTRYSUB + " TEXT," + KEY_ENTRYDESC + " TEXT," + KEY_ENTRYDATE + " TEXT" + ")";
        String CREATE_TASKSTABLE = " CREATE TABLE " + TABLE_TASKS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TASKSUB + " TEXT," + KEY_TASKDESC + " TEXT," + KEY_TASKDUEDATE + " TEXT," + KEY_TASKSTATUS + " TEXT" + ")";
        db.execSQL(CREATE_USERTABLE);
        db.execSQL(CREATE_ENTRIESTABLE);
        db.execSQL(CREATE_TASKSTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void insertUserDetails(String name, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, name);
        contentValues.put(KEY_USEREMAIL, email);
        contentValues.put(KEY_USERPASSWORD, password);

        long newRow = db.insert(TABLE_USERS, null, contentValues);
        db.close();
    }

    public void insertEntryDetails(String subject, String description, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ENTRYDATE, date);
        contentValues.put(KEY_ENTRYSUB, subject);
        contentValues.put(KEY_ENTRYDESC, description);

        long newRow = db.insert(TABLE_ENTRIES, null, contentValues);
        db.close();
    }

    public void insertTaskDetails(String subject, String description, String dueDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TASKSUB, subject);
        contentValues.put(KEY_TASKDESC, description);
        contentValues.put(KEY_TASKDUEDATE, dueDate);
        contentValues.put(KEY_TASKSTATUS, "Active");

        long newRow = db.insert(TABLE_TASKS, null, contentValues);
        db.close();
    }

    @SuppressLint("Range")
    public HashMap<String, String> getUser(String email){
        HashMap<String, String> user = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id, name FROM "+TABLE_USERS+" WHERE "+KEY_USEREMAIL+" = '"+email+"'";
        Cursor cursor = null;
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        user.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
        user.put("name", cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
        return user;
    }

    public boolean validateLogin(String useremail, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_USERS+" WHERE "+KEY_USEREMAIL+" = '"+useremail+"' AND "+KEY_USERPASSWORD+" = '"+password+"'";
        Cursor cursor = null;
        cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0){
            cursor.close();
            db.close();
            return true;

        }
        else {
            cursor.close();
            db.close();
            return false;
        }
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getAllEntries(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> entryList = new ArrayList<>();
        String query = "SELECT id, entryDate, entrySubject, entryDescription FROM "+ TABLE_ENTRIES;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            HashMap<String, String> entry = new HashMap<>();
            entry.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            entry.put("date", cursor.getString(cursor.getColumnIndex(KEY_ENTRYDATE)));
            entry.put("subject", cursor.getString(cursor.getColumnIndex(KEY_ENTRYSUB)));
            entry.put("description", cursor.getString(cursor.getColumnIndex(KEY_ENTRYDESC)));
            entryList.add(entry);

        }
        cursor.close();
        return entryList;
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> getActiveTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> taskList = new ArrayList<>();
        String query = "SELECT id, taskSubject, taskDescription, taskDueDate FROM "+ TABLE_TASKS+" WHERE "+KEY_TASKSTATUS+" = 'Active'";
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            HashMap<String, String> task = new HashMap<>();
            task.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            task.put("duedate", cursor.getString(cursor.getColumnIndex(KEY_TASKDUEDATE)));
            task.put("subject", cursor.getString(cursor.getColumnIndex(KEY_TASKSUB)));
            task.put("description", cursor.getString(cursor.getColumnIndex(KEY_TASKDESC)));
            taskList.add(task);

        }
        cursor.close();
        return taskList;
    }

    @SuppressLint("Range")
    public HashMap<String, String> getSingleEntry(int entryID){
        SQLiteDatabase db = this.getWritableDatabase();
        HashMap<String, String> entry = new HashMap<>();
        String query = "SELECT id, entryDate, entrySubject, entryDescription FROM "+TABLE_ENTRIES+" WHERE "+KEY_ID+" = "+entryID;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()){
            entry.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            entry.put("date", cursor.getString(cursor.getColumnIndex(KEY_ENTRYDATE)));
            entry.put("subject", cursor.getString(cursor.getColumnIndex(KEY_ENTRYSUB)));
            entry.put("description", cursor.getString(cursor.getColumnIndex(KEY_ENTRYDESC)));
            cursor.close();
        }
        return entry;
    }

    @SuppressLint("Range")
    public HashMap<String, String> getSingleTask(int taskID){
        SQLiteDatabase db = this.getWritableDatabase();
        HashMap<String, String> task = new HashMap<>();
        String query = "SELECT id, taskDueDate, taskSubject, taskDescription FROM "+TABLE_TASKS+" WHERE "+KEY_ID+" = "+taskID;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()){
            task.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            task.put("date", cursor.getString(cursor.getColumnIndex(KEY_TASKDUEDATE)));
            task.put("subject", cursor.getString(cursor.getColumnIndex(KEY_TASKSUB)));
            task.put("description", cursor.getString(cursor.getColumnIndex(KEY_TASKDESC)));
            cursor.close();
        }
        return task;
    }

    public int updateEntry(int id, String subject, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ENTRYSUB, subject);
        contentValues.put(KEY_ENTRYDESC, description);

        int count = db.update(TABLE_ENTRIES, contentValues, KEY_ID+" = ?", new String[]{String.valueOf(id)});
        return count;
    }
    public int updateTask(int id, String duedate, String subject, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TASKDUEDATE, duedate);
        contentValues.put(KEY_TASKSUB, subject);
        contentValues.put(KEY_TASKDESC, description);

        int count = db.update(TABLE_ENTRIES, contentValues, KEY_ID+" = ?", new String[]{String.valueOf(id)});
        return count;
    }

    public void updateTaskStatus(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TASKSTATUS, "Complete");
        db.update(TABLE_TASKS, contentValues, KEY_ID+" = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteEntry(int entryID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ENTRIES, KEY_ID+" = ?", new String[]{String.valueOf(entryID)});
        db.close();
    }

    public void deleteTask(int taskID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID+" = ?", new String[]{String.valueOf(taskID)});
        db.close();
    }
}
