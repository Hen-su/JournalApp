package com.example.journalapp;

public class TaskItem {
    String date, name, description;
    boolean status;

    public TaskItem (String date, String name, String description, boolean status) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.status = status;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
