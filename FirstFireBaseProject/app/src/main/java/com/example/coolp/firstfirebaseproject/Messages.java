package com.example.coolp.firstfirebaseproject;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by coolp on 4/8/2018.
 */

@IgnoreExtraProperties
public class Messages {
    String username,user_id,message,created_at, message_id;
    public Messages() {
    }

    public Messages(String username, String user_id, String message, String created_at, String message_id) {
        this.username = username;
        this.user_id = user_id;
        this.message = message;
        this.created_at = created_at;
        this.message_id = message_id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }
}