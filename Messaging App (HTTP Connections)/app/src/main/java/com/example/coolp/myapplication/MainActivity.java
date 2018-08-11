package com.example.coolp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Chat Room");
        if(isConnected()) {
            sharedPref = getSharedPreferences("preference_file_key", Context.MODE_PRIVATE);
            String str = sharedPref.getString("TokenObject", null);

            if (str == null || str.isEmpty()) {
                Button b = (Button) findViewById(R.id.button);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText email = (EditText) findViewById(R.id.editText);
                        EditText password = (EditText) findViewById(R.id.editText2);
                        boolean validemail = validEmail(email.getText().toString());
                        if (!validemail) {
                            Toast.makeText(MainActivity.this, "Enter the valid email", Toast.LENGTH_SHORT).show();
                        } else {
                            try {

                                performLogin(email.getText().toString(), password.getText().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


                Button b2 = (Button) findViewById(R.id.button2);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            } else {

                Intent i = new Intent(MainActivity.this, Main4Activity.class);
                startActivity(i);
                finish();

            }
        }else{
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
        }
    }


    public void performLogin(String username, String password) throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("email", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/login")
                .post(formBody)
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

                Gson g = new Gson();
                final TokenResponse tr = g.fromJson(str, TokenResponse.class);
                Log.d("demo", "onResponse " + tr.getToken());




               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       saveToken(str);
                       if(tr.getToken()==null){
                           Toast.makeText(MainActivity.this, "Invalid values", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           Intent i = new Intent(MainActivity.this, Main4Activity.class);
                           //i.putExtra(TT,tr);
                           startActivity(i);
                           finish();
                       }
                   }
               });


                //getThreadList(tr.getToken())
            }
        });
    }

    public void saveToken(String str) {
        SharedPreferences sharedPref = getSharedPreferences("preference_file_key", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TokenObject",str);
        editor.commit();
    }
    public boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
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
