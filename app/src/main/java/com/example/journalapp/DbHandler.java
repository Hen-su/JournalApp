package com.example.journalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;

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

    public void insertTaskDetails(String subject, String description, String dueDate, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TASKSUB, subject);
        contentValues.put(KEY_TASKDESC, description);
        contentValues.put(KEY_TASKDUEDATE, dueDate);
        contentValues.put(KEY_TASKSTATUS, status);

        long newRow = db.insert(TABLE_ENTRIES, null, contentValues);
        db.close();
    }
}
