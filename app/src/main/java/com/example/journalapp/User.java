package com.example.journalapp;

public class User {
    private String name;
    private String email;
    private String password;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String email, String password) {
        /*if (name == null) {
            throw new Exception("Please enter your name");
        }
        else {
            this.name = name;
        }
        if (email == null) {
            throw new Exception("Please enter your name");
        }
        else {
            this.name = name;
        }
        if (password == null) {
            throw new Exception("Please enter your name");
        }
        else {
            this.name = name;
        }*/
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
