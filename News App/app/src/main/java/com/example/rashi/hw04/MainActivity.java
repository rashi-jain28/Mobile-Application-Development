package com.example.rashi.hw04;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetNewsAsync.IData{
    String[] category = new String[]{"Top Stories", "World", "U.S.", "Business", "Politics", "Technology",
            "Health", "Entertainment", "Travel", "Living", "Most Recent"};
    TextView search;
    TextView title, publishedAT, description, hidden, display;
    ImageButton next, previous;
    ArrayList<Items> data;
    ImageView iv;
    ProgressDialog dialog;

    //ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.categories);
        title= findViewById(R.id.Title);
        publishedAT= findViewById(R.id.publishedAt);
        description= findViewById(R.id.description);
        next= findViewById(R.id.next);
        previous= findViewById(R.id.previous);
        hidden = findViewById(R.id.hidden);
        display = findViewById(R.id.show);
        iv= findViewById(R.id.imageView);
        dialog = new ProgressDialog(MainActivity.this);
        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle( Html.fromHtml("<font color='#0000FF'>Choose Category</font>"));
                //builder.setTitle("Choose Category");
                builder.setItems(category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isConnected()) {
                            search.setText(category[which]);
                            String text = search.getText().toString();
                            switch(text){
                                case "Most Recent":
                                    new GetNewsAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_latest.rss");
                                    break;
                                case "U.S.":
                                    new GetNewsAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_us.rss");
                                    break;
                                case "Business":
                                    new GetNewsAsync(MainActivity.this).execute("http://rss.cnn.com/rss/money_latest.rss");
                                    break;
                                case "Politics":
                                    new GetNewsAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_allpolitics.rss");
                                    break;
                                case "Entertainment":
                                    new GetNewsAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_showbiz.rss");
                                    break;
                                case "Technology":
                                    new GetNewsAsync(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_tech.rss");
                                    break;
                                default:
                                    String setUrl="http://rss.cnn.com/rss/cnn_".concat(text.toLowerCase().replaceAll(" ","").concat(".rss"));
                                    new GetNewsAsync(MainActivity.this).execute(setUrl);
                                    break;
                            }
                        } else {
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

    public void handleData(final ArrayList<Items> result) {
        this.data = result;
    }
    @Override
    public void handleListData(final ArrayList<Items> arrayList) {
       this.data= arrayList;
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if(arrayList!=null && !arrayList.isEmpty()) {
            final int max = arrayList.size() - 1;
            if(arrayList.size()<2) {
                next.setEnabled(false);
                previous.setEnabled(false);
            }
            else
            {
                next.setEnabled(true);
                previous.setEnabled(true);
            }
            hidden.setText("0");
            display.setText("1 out of " + arrayList.size());
            display(arrayList.get(0));
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt((String) hidden.getText());
                    count++;
                    if (count <= max) {
                        hidden.setText(count + "");
                        display.setText(count + 1 + " out of " + arrayList.size());
                        display(arrayList.get(count));
                    }
                    if(count > max) {
                        count=0;
                        hidden.setText(count + "");
                        display.setText(count + 1 + " out of " + arrayList.size());
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
                        display.setText(count + 1 + " out of " + arrayList.size());
                        display(arrayList.get(count));
                    }
                    if(count == 0) {
                        count= max;
                        hidden.setText(count + "");
                        display.setText(count + 1 + " out of " + arrayList.size());
                        display(arrayList.get(count));
                    }
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this, "No News Found", Toast.LENGTH_SHORT).show();
            title.setText(" ");
            publishedAT.setText(" ");
            description.setText(" ");
            iv.setImageResource(0);
            display.setText(" ");
            next.setEnabled(false);
            previous.setEnabled(false);
        }
   }
    public void display(final Items items){

        String URL = items.link;
        //final String text="<a href='"+URL+"'>"+items.getTitle()+"</a>";
        //title.setText(Html.fromHtml(text));
        title.setText(items.getTitle());
        title.setClickable(true);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(items.link));
                startActivity(i);*/
                displayBrowser(items);
            }
        });
        //title.setMovementMethod (LinkMovementMethod.getInstance());
        publishedAT.setText(items.getPublishedAt());
        description.setText(items.getDescription());
        Picasso.with(MainActivity.this).load(items.getImageURL()).into(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(items.link));
                startActivity(i);*/
                displayBrowser(items);
            }
        });
    }

    @Override
    public void dialogProgress() {
        dialog.setMessage("Loading News...");
        dialog.show();
    }
    public void displayBrowser(Items item) {
        if(item.link!=null){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(item.link));
            startActivity(i);
        }
        else
            Toast.makeText(MainActivity.this, "No Link to show", Toast.LENGTH_SHORT).show();
    }
}

