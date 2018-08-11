package com.example.coolp.firstfirebaseproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main4Activity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ArrayList<Threads> threadList=new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    ThreadAdapter adapter;
    public static String ThreadKey="Thread";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final TextView t = findViewById(R.id.nameText);

        String username= getIntent().getStringExtra("username");
        t.setText(username);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showThreads(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ImageButton ib = findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Main4Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        ImageButton ib2 = findViewById(R.id.imageButton2);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addThread(user);
            }

        });
    }

        public void addThread(FirebaseUser user) {
            Log.d("demo", "" + user.getDisplayName());
            final EditText et = findViewById(R.id.editText3);
            final String tt = et.getText().toString();

            if (tt.length() == 0) {
                et.setError("Enter thread first");
            } else {
                Threads thread = new Threads(user.getDisplayName(), user.getUid(), "", tt, "fjs");
                String key = mDatabase.child("Threads").push().getKey();
                thread.threadId = key;
                mDatabase.child("Threads").child(key).setValue(thread);
                et.setText("");
              //  threadList.add(thread);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayThreadList();
                    }
                });
            }
        }



    public void displayThreadList(){
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ThreadAdapter(Main4Activity.this, R.layout.thread_layout, threadList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                Intent intent = new Intent(Main4Activity.this, ChatRoomActivity.class);
                Threads a = threadList.get(i);
                intent.putExtra(ThreadKey, a);
                startActivity(intent);
            }
        });

    }

    public void deleteThread(final Threads t,final int pos){

        mDatabase.child("Threads").child(t.getThreadId()).removeValue();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.remove(threadList.get(pos));

                adapter.setNotifyOnChange(true);

            }
        });




    }



 private void showThreads(DataSnapshot dataSnapshot){

    threadList.clear();
     for(DataSnapshot ds: dataSnapshot.getChildren()){

             for (DataSnapshot ds1 : ds.getChildren()) {
                 String key = ds1.getKey();
                 Threads th = new Threads();
                 th = ds1.getValue(Threads.class);
                 threadList.add(th);
             }

     }
     displayThreadList();

 }
}