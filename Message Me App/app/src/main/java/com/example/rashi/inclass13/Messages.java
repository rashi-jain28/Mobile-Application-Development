package com.example.rashi.inclass13;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 4/23/2018.
 */

public class Messages implements Serializable, Comparator<Messages>{

    String senderName, senderEmail, text, created_at, messageId;
    Boolean isRead = false;

    public Messages(String senderName, String senderEmail, String text, String created_at, Boolean isRead, String messageId) {
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.text = text;
        this.created_at = created_at;
        this.isRead = isRead;
        this.messageId = messageId;
    }
    public Messages(){
    }
    @Override
    public int compare(Messages msg1, Messages msg2) {
        Date date1=null;
        Date date2 = null;
        try {
             date1=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(msg1.created_at);
             date2=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(msg2.created_at);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date1.compareTo(date2);
       /* if (value1 != 0) {
            return value1;
        }
        else
        {*//*
            return msg1.created_at.compareTo(msg2.created_at);
       // }*/
    }


}