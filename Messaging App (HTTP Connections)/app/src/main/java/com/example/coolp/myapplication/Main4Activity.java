package com.example.coolp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main4Activity extends AppCompatActivity {

    public static String ThreadId = "ThreadID";
    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedPref;
    ThreadAdapter adapter;
    ArrayList<Threads> threadList;
    boolean deleteFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        setTitle("Message Threads");
        // final TokenResponse tr= (TokenResponse) getIntent().getExtras().getSerializable(MainActivity.TT);

        sharedPref = getSharedPreferences("preference_file_key", Context.MODE_PRIVATE);
        String str = sharedPref.getString("TokenObject", null);
        Gson g = new Gson();
        final TokenResponse tr = g.fromJson(str, TokenResponse.class);
        getThread(tr);


        ImageButton ib = (ImageButton) findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performLogout(tr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton ib2 = (ImageButton) findViewById(R.id.imageButton2);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        addThread(tr);
                        //adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }




    public void performLogout(TokenResponse tr) throws Exception {

        clearToken();
        Intent i = new Intent(Main4Activity.this, MainActivity.class);
        startActivity(i);
       /* Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/delete/")
                .header("Authorization", "BEARER " + tr.getToken())
                .build();

        *//*addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")*//*

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                clearToken();
                Log.d("demo", "onResponse " + String.valueOf(Thread.currentThread().getId()));


                Intent i = new Intent(Main4Activity.this, MainActivity.class);
                startActivity(i);
                //getThreadList(tr.getToken())
            }
        });*/
    }

    public void clearToken() {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("TokenObject");
        editor.commit();
        editor.clear();
        editor.commit();

    }


    public void addThread(final TokenResponse tr) {
        final EditText et = (EditText) findViewById(R.id.editText3);
        final String tt = et.getText().toString();
        if( tt.length() == 0 ) {
            et.setError("Enter thread first");
        }
        else  {
            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {*/
                    RequestBody formBody = new FormBody.Builder()
                            .add("title", tt)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/add")
                            .header("Authorization", "BEARER " + tr.getToken())
                            .post(formBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                          //  getThread(tr);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("qwe",response.toString());

                                    String str = null;
                                    try {
                                        str = response.body().string();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    JsonParser jsonParser = new JsonParser();
                                    JsonObject jo = (JsonObject) jsonParser.parse(str);
                                    JsonObject newjo=jo.getAsJsonObject("thread");

                                    Gson googleJson = new Gson();
                                    Threads th = googleJson.fromJson(newjo, Threads.class);
                                    threadList.add(0,th);
                                    adapter.add(th);
                                    adapter.setNotifyOnChange(true);

                                    et.setText("");

                                }
                            });

                        }

            });

        }
    }


    public void displayThreadList(final TokenResponse tr){
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ThreadAdapter(Main4Activity.this, R.layout.thread_layout, threadList, tr);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {

                    Intent intent = new Intent(Main4Activity.this, ChatRoomActivity.class);
                    Threads a = threadList.get(i);
                    intent.putExtra(ThreadId, a);
                    startActivity(intent);
                    //finish();

            }
        });

    }


    public void deleteThread(final Threads t,final TokenResponse tr,final int pos){

                Request request = new Request.Builder()
                        .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/delete/" + t.id)
                        .header("Authorization", "BEARER " + tr.getToken())
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //adapter.notifyDataSetChanged();
                        //getThread(tr);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.remove(threadList.get(pos));

                                adapter.setNotifyOnChange(true);

                            }
                        });


                    }
                });



    }
    public void getThread(final TokenResponse tr){

        TextView t = (TextView) findViewById(R.id.nameText);
        t.setText(tr.getUser_fname() + " " + tr.getUser_lname());
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread")
                .header("Authorization", "BEARER " + tr.getToken())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("demo", "onResponse " + String.valueOf(Thread.currentThread().getId()));
                String str = response.body().string();
                Log.d("demo", "onResponse " + str);
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(str);
                JsonArray jsonArr = jo.getAsJsonArray("threads");
                //jsonArr.u
                Gson googleJson = new Gson();
                Threads[] thrd = googleJson.fromJson(jsonArr, Threads[].class);
                threadList = new ArrayList<Threads>(Arrays.asList(thrd));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayThreadList(tr);
                    }
                });

            }
        });

    }
}
