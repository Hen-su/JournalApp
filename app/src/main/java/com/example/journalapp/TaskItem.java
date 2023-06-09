package com.example.journalapp;

import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;

public class TaskItem {
    String date, name, description, status, reminderDate;
    int id, notificationID;
    public TaskItem (String date, String name, String description) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.description = description;
        this.status = status;
    }
    public TaskItem(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }
}
