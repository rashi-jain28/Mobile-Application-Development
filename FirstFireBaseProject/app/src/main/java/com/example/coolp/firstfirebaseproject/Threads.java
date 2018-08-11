package com.example.coolp.firstfirebaseproject;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rashi on 4/16/2018.
 */


@IgnoreExtraProperties
public class Threads implements Serializable {
    String username;
    String user_id,threadId;
    String title;
    String created_at;

    public Threads(){}
    public Threads(String username, String user_id, String threadId, String title, String created_at) {
        this.username = username;
        this.user_id = user_id;
        this.threadId = threadId;
        this.title = title;
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}