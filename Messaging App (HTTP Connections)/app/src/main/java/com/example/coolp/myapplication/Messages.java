package com.example.coolp.myapplication;

/**
 * Created by coolp on 4/8/2018.
 */

public class Messages {
    String user_fname,user_lname,user_id,id,message,created_at;

    public Messages() {
    }

    public Messages(String user_fname, String user_lname, String user_id, String id, String message, String created_at) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_id = user_id;
        this.id = id;
        this.message = message;
        this.created_at = created_at;
    }
}
