
package com.example.coolp.firstfirebaseproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatRoomActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    MessageAdapter adapter;
    TokenResponse tr;
    Threads thread;
    ArrayList<Messages> messages=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        setTitle("Chatroom");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showMessages(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

        try {
            thread= (Threads) getIntent().getExtras().getSerializable(Main4Activity.ThreadKey);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView text = findViewById(R.id.thread);
                    text.setText(thread.title);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageButton addBtn = (ImageButton) findViewById(R.id.imageButton4);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        addMessage(mAuth.getCurrentUser(),thread);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        ImageButton homeBtn = (ImageButton) findViewById(R.id.imageButton3);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showMessages(DataSnapshot dataSnapshot){
        messages.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            for (DataSnapshot ds1 : ds.getChildren()) {
                if (ds1.getKey().equals(thread.getThreadId())) {
                    for (DataSnapshot ds2 : ds1.getChildren()) {
                        if (ds2.getKey().equals("messages")) {
                            for(DataSnapshot ds3 : ds2.getChildren()){
                                Messages th = new Messages();
                                th = ds3.getValue(Messages.class);
                                messages.add(th);
                            }}
                    }
                }

            }
        }
        displayMessages();

    }


    private void displayMessages() {
        ListView listView = (ListView) findViewById(R.id.listView2);
        adapter = new MessageAdapter(ChatRoomActivity.this, R.layout.chatting_layout, messages);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

    }
    public void addMessage(FirebaseUser user, Threads t) {
        Log.d("demo", "" + user.getDisplayName());
        final EditText et = (EditText) findViewById(R.id.editText4);
        final String tt = et.getText().toString();
        if (tt.length() == 0) {
            et.setError("Enter message first");
        } else {
            String username,user_id,message,created_at, message_id;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            //String date= DateFormat.getDateTimeInstance().format(new Date());
            //Log.d("demo", date);
            Messages msg = new Messages(user.getDisplayName(), user.getUid(),tt,formattedDate,"");
            String key = mDatabase.child("Threads").child(t.getThreadId()).child("messages").push().getKey();
            msg.message_id=key;
            mDatabase.child("Threads").child(t.getThreadId()).child("messages").child(key).setValue(msg);
            et.setText("");
            //messages.add();
        }
    }
    public void deleteMessage(final Messages t, final int pos){

        mDatabase.child("Threads").child(thread.getThreadId()).child("messages").child(t.getMessage_id()).removeValue();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.remove(messages.get(pos));
                adapter.setNotifyOnChange(true);
            }
        });


    }

}

