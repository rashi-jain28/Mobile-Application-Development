package com.example.coolp.firstfirebaseproject;

/**
 * Created by coolp on 4/16/2018.
 */

public class MessagesTest {

  String text;
  int priority;
 String uid;

 public MessagesTest(){}
    public MessagesTest(String text, int priority, String uid) {
        this.text = text;
        this.priority = priority;
        this.uid=uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
