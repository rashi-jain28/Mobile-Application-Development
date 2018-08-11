package example.coolp.homework3;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetSimpleAsync.IData {

    ArrayList<Question> data;
    final static String QuestionList="Question_List";
    Button startButton;
    ImageView iv;
    TextView tv;
    ProgressBar pb;
    TextView ready;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton= (Button)findViewById(R.id.startButton);
        startButton.setEnabled(false);
        iv=(ImageView)findViewById(R.id.imageView);
        iv.setVisibility(View.GONE);
        tv=(TextView)findViewById(R.id.textView2);
        ready=(TextView)findViewById(R.id.readyText);
        pb= (ProgressBar)findViewById(R.id.progressBar);
        if(isConnected()) {
            new GetSimpleAsync(MainActivity.this,pb).execute("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");
        }else{
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
        Button exit= (Button)findViewById(R.id.exitButton);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
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

    public void handleData(final ArrayList<Question> result){
        this.data= result;
        iv.setVisibility(View.VISIBLE);
        tv.setVisibility(View.GONE);
        ready.setVisibility(View.VISIBLE);
        startButton.setEnabled(true);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                i.putExtra(QuestionList,result);
                startActivity(i);
            }
        });
    }

    @Override
    public void handleListData(final ArrayList<Question> result) {
        this.data= result;
        iv.setVisibility(View.VISIBLE);
        tv.setVisibility(View.GONE);
        ready.setVisibility(View.VISIBLE);
        startButton.setEnabled(true);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                i.putExtra(QuestionList,result);
                startActivity(i);
            }
        });
    }
}


