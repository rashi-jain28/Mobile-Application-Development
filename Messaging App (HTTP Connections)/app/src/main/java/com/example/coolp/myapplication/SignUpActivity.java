package com.example.coolp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    public static String abc="ABC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        final TextView fname= findViewById(R.id.fname);
        final TextView lname= findViewById(R.id.lname);
        final TextView email= findViewById(R.id.email);
        final TextView pwd= findViewById(R.id.pwd);
        final TextView rpwd= findViewById(R.id.rpwd);

        Button b=(Button) findViewById(R.id.button4);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean validemail = validEmail(email.getText().toString());
                    if(!validemail){
                        Toast.makeText(SignUpActivity.this, "Enter the valid email", Toast.LENGTH_SHORT).show();
                    }
                    if(!pwd.getText().toString().equals(rpwd.getText().toString())){
                        Toast.makeText(SignUpActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        performSignUp(email.getText().toString(), pwd.getText().toString(), fname.getText().toString(), lname.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button b2=(Button) findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void performSignUp(String email, String password, String fname,String lname) throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("fname",fname)
                .add("lname",lname)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/signup")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "onFailure");
                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("demo", "onResponse " + String.valueOf(Thread.currentThread().getId()));
                final String str = response.body().string();
                Log.d("demo", "onResponse " + str);

                final Gson g = new Gson();
                JsonParser jp = new JsonParser();
                final JsonObject root = (JsonObject) jp.parse(str);
                Object je=root.getAsJsonPrimitive("status");
                //JsonObject je=root.getAsJsonObject("status");
                final String s = g.fromJson(je.toString()
                        , String.class);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (s.equals("error")) {
                                Object j=root.getAsJsonPrimitive("message");
                                String errorMsg=g.fromJson(j.toString(), String.class);
                                Toast.makeText(SignUpActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                TokenResponse tr = g.fromJson(str, TokenResponse.class);
                                Log.d("demo", "onResponse " + tr.getToken());

                                saveToken(str);

                                Intent i = new Intent(SignUpActivity.this, Main4Activity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    });
                }

        } );

    }
    public void saveToken(String str) {
        SharedPreferences sharedPref = getSharedPreferences("preference_file_key", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TokenObject",str);
        editor.commit();
    }
    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}

