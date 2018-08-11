package com.example.coolp.firstfirebaseproject;

/**
 * Created by coolp on 4/16/2018.
 */

/**
 * Created by Rashi on 4/16/2018.
 */

public class User {
    String email,  password,  fname, lname;
    public User(String email, String password, String fname, String lname) {
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
    }
    public User(String email, String fname, String lname) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}