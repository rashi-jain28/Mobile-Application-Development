package com.example.coolp.myapplication;

import java.io.Serializable;

/**
 * Created by coolp on 4/7/2018.
 */

public class Threads implements Serializable {
    String user_fname, user_lname;
    String user_id,id;
    String title;
    String created_at;

    public Threads(String user_fname, String user_lname, String user_id, String id, String title, String created_at) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_id = user_id;
        this.id = id;
        this.title = title;
        this.created_at = created_at;
    }
}
