package com.example.coolp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatRoomActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Messages> messages= new ArrayList<>();
    SharedPreferences sharedPref;
    MessageAdapter adapter;
    TokenResponse tr;
    Threads thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        setTitle("Chatroom");
        sharedPref = getSharedPreferences("preference_file_key", Context.MODE_PRIVATE);
        String str = sharedPref.getString("TokenObject", null);
        Gson g = new Gson();
          tr = g.fromJson(str, TokenResponse.class);
         thread= (Threads) getIntent().getExtras().getSerializable(Main4Activity.ThreadId);
        try {
            showChats(thread);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageButton addBtn = (ImageButton) findViewById(R.id.imageButton4);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        addMessage(tr);
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
    public void showChats(final Threads thread) throws Exception {

        Request request = new Request.Builder()
               .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/messages/"+thread.id)
                //.url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/messages/1306")
                .header("Authorization", "BEARER " + tr.getToken())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "onFailure");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("demo", "onResponse " + String.valueOf(Thread.currentThread().getId()));
                String str = response.body().string();
                Log.d("demo", "onResponse " + str);
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(str);
                JsonArray jsonArr = jo.getAsJsonArray("messages");
                Gson g = new Gson();
                final Messages[] me = g.fromJson(jsonArr, Messages[].class);
                messages = new ArrayList<Messages>(Arrays.asList(me));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView text = findViewById(R.id.thread);
                        text.setText(thread.title);
                        displayMessages(messages);
                    }
                });
                //getThreadList(tr.getToken())
            }
        });
    }
    private void displayMessages(ArrayList<Messages> messages) {
        ListView listView = (ListView) findViewById(R.id.listView2);
        adapter = new MessageAdapter(ChatRoomActivity.this, R.layout.chatting_layout, messages,tr);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //Intent i= new Intent
                *//*deleteFlag=false;
                ImageButton ib= (ImageButton)findViewById(R.id.imageButton5);
                ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteFlag=true;
                        deleteThread(threadList.get(i), tr);
                    }
                });*//*
                *//*if(!deleteFlag) {*//*
                Intent intent = new Intent(Main4Activity.this, ChatRoomActivity.class);
                Threads a = threadList.get(i);
                intent.putExtra(ThreadId,  a);
                startActivity(intent);
                // }
            }
        });*/
    }
    public void addMessage(final TokenResponse tr) {
        final EditText et = (EditText) findViewById(R.id.editText4);
        final String tt = et.getText().toString();
        if( tt.length() == 0 ) {
            et.setError("Enter message first");
        }
        else{
            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {*/
            RequestBody formBody = new FormBody.Builder()
                    .add("message", tt)
                    .add("thread_id", thread.id)
                    .build();
            Request request = new Request.Builder()
                    .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/message/add")
                    .header("Authorization", "BEARER " + tr.getToken())
                    .post(formBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String str = null;
                            try {
                                str = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            JsonParser jsonParser = new JsonParser();
                            JsonObject jo = (JsonObject) jsonParser.parse(str);
                            JsonObject newjo = jo.getAsJsonObject("message");

                            Gson googleJson = new Gson();
                            Messages th = googleJson.fromJson(newjo, Messages.class);
                           // messages.add(0, th);
                            adapter.add(th);
                            adapter.setNotifyOnChange(true);
                            et.setText("");

                        }
                    });

                }
                   /* });
                }*/

            });

        }
    }
    public void deleteMessage(final Messages t,final TokenResponse tr, final int pos){

        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/message/delete/" + t.id)
                .header("Authorization", "BEARER " + tr.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //adapter.notifyDataSetChanged();
               /* try {
                    showChats(thread);
                }catch(Exception e){}*/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.remove(messages.get(pos));
                        adapter.setNotifyOnChange(true);

                    }
                });

            }
        });



    }

}
