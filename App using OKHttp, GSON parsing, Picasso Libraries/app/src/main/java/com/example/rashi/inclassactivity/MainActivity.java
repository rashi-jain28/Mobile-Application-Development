package com.example.rashi.inclassactivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<Hits> hitArrayLst = new ArrayList<>();
    EditText ss;
    TextView hidden;
    ImageView iv;
    float initialX,initialY ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id){
            case R.id.item1: {
                ss.setText("");
                iv.setImageResource(0);
                hitArrayLst.clear();
                hidden.setText(""+0);
            }
        }
        return true;
    }

    @Override
     protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ss= (EditText) findViewById(R.id.editText);
        iv= findViewById(R.id.iv);
        hidden = findViewById(R.id.textView);
        setTitle("Search Images");
        iv.setOnTouchListener(new View.OnTouchListener() {
           @Override
            public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = event.getX();
                            initialY = event.getY();

                            Log.d("demo", "Action was DOWN");
                            break;

                        case MotionEvent.ACTION_MOVE:
                            Log.d("demo", "Action was MOVE");
                            break;

                        case MotionEvent.ACTION_UP:
                            float finalX = event.getX();
                            float finalY = event.getY();

                            Log.d("demo", "Action was UP");

                            if (initialX < finalX) {
                                Log.d("demo", "Left to Right swipe performed");
                                int count = Integer.parseInt((String) hidden.getText());
                                count--;
                                if (count >=0) {
                                    hidden.setText(count + "");
                                    Picasso.with(getBaseContext()).load(hitArrayLst.get(count).previewURL).into(iv);
                                }
                            }

                            if (initialX > finalX) {
                                Log.d("demo", "Right to Left swipe performed");
                                int count = Integer.parseInt((String) hidden.getText());
                                count++;
                                if (count <= hitArrayLst.size()-1) {
                                    hidden.setText(count + "");
                                    Picasso.with(getBaseContext()).load(hitArrayLst.get(count).previewURL).into(iv);
                                }
                            }

                            if (initialY < finalY) {
                                Log.d("demo", "Up to Down swipe performed");
                            }

                            if (initialY > finalY) {
                                Log.d("demo", "Down to Up swipe performed");
                            }

                            break;

                        case MotionEvent.ACTION_CANCEL:
                            Log.d("demo","Action was CANCEL");
                            break;

                        case MotionEvent.ACTION_OUTSIDE:
                            Log.d("demo", "Movement occurred outside bounds of current screen element");
                            break;

                    }
                    return true;
                }
            });
            findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  if(isConnected()){
                    apiCall();}
                    else{
                      Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                  }
                }
            });
        }
        public void apiCall(){
            String enteredValue= ss.getText().toString();
            enteredValue=enteredValue.replace(" ","+");
            Request request = new Request.Builder()
                    .url("https://pixabay.com/api/?key=8642282-20e129269ea3036b3d7ce97e1&image_type=photo&q="+enteredValue)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("demo", "onFailure");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("demo", "onResponse " + String.valueOf(Thread.currentThread().getId()));
                    final String str = response.body().string();
                    Log.d("demo", "onResponse " + str);

                    JsonParser jsonParser = new JsonParser();
                    JsonObject jo = (JsonObject) jsonParser.parse(str);
                    JsonArray jsonArr = jo.getAsJsonArray("hits");

                    Gson googleJson = new Gson();
                    Hits[] hits = googleJson.fromJson(jsonArr, Hits[].class);
                    hitArrayLst = new ArrayList<Hits>(Arrays.asList(hits));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(hitArrayLst.size()==0) {
                                Toast.makeText(MainActivity.this, "No result found", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Picasso.with(getBaseContext()).load(hitArrayLst.get(0).previewURL).into(iv);
                            hidden.setText(""+0);
                        }
                    });
                }
            });
        }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
}

