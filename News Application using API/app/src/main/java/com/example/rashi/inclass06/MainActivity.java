package com.example.rashi.inclass06;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] category= new String[]{"Business","Entertainment","General","Health","Science","Sports","Technology"};
    TextView search;
    TextView title,publishedAT,description,hidden,display;
    ImageButton next,previous;
    ImageView iv;
    ArrayList<Articles> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search);
        title = findViewById(R.id.title);
        publishedAT= findViewById(R.id.publishedAt);
        description= findViewById(R.id.Description);
        hidden = findViewById(R.id.hidden);
        next= findViewById(R.id.next);
        previous= findViewById(R.id.previous);
        display = findViewById(R.id.display);
        iv= findViewById(R.id.imageView);
        findViewById(R.id.Gobtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose Category");
                builder.setItems(category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isConnected()) {
                            search.setText(category[which]);
                            String text = search.getText().toString();
                            RequestParams params = new RequestParams();
                            params.addParameter("category", text);
                            //new GetImageAsync(params).execute("http://dev.theappsdr.com/apis/photos/index.php");*/
                            new GetSimpleAsync(params,MainActivity.this).execute("https://newsapi.org/v2/top-headlines?country=us&apiKey=557753f0eff14f55ac2800976ac27a95");
                        }
                    else
                        {
                            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
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

    public void handleData(final ArrayList<Articles> arrayList)
    {
        this.data = data;
        final int max= arrayList.size()-1;
        hidden.setText("0");
        display.setText("1 out of " + arrayList.size());
        if(arrayList.isEmpty())
        {
            title.setText(" ");
            publishedAT.setText(" ");
            description.setText(" ");

            Toast.makeText(MainActivity.this, "No Data to display", Toast.LENGTH_SHORT).show();
        }
        else
        {
            display(arrayList.get(0));
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count= Integer.parseInt((String) hidden.getText());
                if(count!=max){
                    count++;
                    hidden.setText(count+ "");
                    display.setText(count+1 +" out of " + arrayList.size());
                    display(arrayList.get(count));
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(hidden.getText().toString());
                if (count != 0) {
                    count--;
                    hidden.setText(count + "");
                    display.setText(count+1 +" out of " + arrayList.size());
                    display(arrayList.get(count));
                }
            }
        });
    }
    public void display(Articles article){

        title.setText(article.getTitle());
        publishedAT.setText(article.getPublishedAt());
        description.setText(article.getDescription());
        Picasso.with(MainActivity.this).load(article.getUrlToImage()).into(iv);
    }

    }

