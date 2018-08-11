package com.example.rashi.inclass13;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ChatActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ArrayList<String> users=new ArrayList<>();
    ArrayList<Messages> messages = new ArrayList<>();
    InboxAdapter adapter;
    static final String MSG_KEY="Message";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(ChatActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            // Do something
            return true;
        }
        if (id == R.id.compose) {
            Intent i = new Intent(ChatActivity.this, ComposeActivity.class);
            CharSequence[] items = users.toArray(new CharSequence[users.size()]);
            i.putExtra("users",items);
            startActivity(i);
            finish();
            // Do something
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setTitle("Inbox");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        messages.clear();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                users.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    if(ds.getKey().equals("users")) {
                        for (DataSnapshot ds1 : ds.getChildren()) {

                            User u = ds1.getValue(User.class);
                            users.add(u.name);
                        }
                    }
                    if(ds.getKey().equals("mailbox")){
                        messages.clear();
                        for(DataSnapshot ds1 : ds.getChildren()) {
                            if (ds1.getKey().equals(mAuth.getUid()) && ds1.hasChildren()) {
                                for (DataSnapshot ds2 : ds1.getChildren()) {
                                    Messages msg = ds2.getValue(Messages.class);
                                     //Collections.sort(messages,msg);
                                     //Collections.sort(messages,Collections.reverseOrder(msg));
                                    messages.add(msg);

                                }

                            }
                        }

                    }

                }
                Collections.reverse(messages);
                displayMessages();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d("demo",""+messages.size());




    }

    public void displayMessages(){
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new InboxAdapter(ChatActivity.this, R.layout.inbox_layout, messages);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Intent intent = new Intent(ChatActivity.this, ReadActivity.class);
                Messages m = messages.get(i);
                m.isRead = true;
                intent.putExtra(MSG_KEY, m);
                startActivity(intent);
            }
        });
    }
}
