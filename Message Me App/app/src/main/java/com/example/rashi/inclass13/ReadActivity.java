package com.example.rashi.inclass13;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ReadActivity extends AppCompatActivity {
    TextView from;
    TextView text;
    Messages msg;
    static final String READ_MSG="readMsg";
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        setTitle("Read Message");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        from = findViewById(R.id.from);
        text = findViewById(R.id.text);
        msg = (Messages) getIntent().getExtras().getSerializable(ChatActivity.MSG_KEY);
        from.setText(msg.senderName);
        text.setText(msg.text);


        ///// update the isRead field in the database //////

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        //String key =  myRef.child("mailbox").child(mAuth.getUid()).child(msg.messageId).getKey();
        /*myRef.child("mailbox").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> postValues = new HashMap<String, Object>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.getKey().equals(msg.messageId)){
                        for(DataSnapshot ds: snapshot.getChildren()){
                        postValues.put(snapshot.getKey(), snapshot.getValue());
                    }
                        postValues.put("isRead", true);
                        myRef.child("mailbox").child(mAuth.getUid()).child(msg.messageId).updateChildren(postValues);
                }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

*/

                myRef.child("mailbox").child(mAuth.getUid()).child(msg.messageId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> postValues = new HashMap<String, Object>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //Messages msg = snapshot.getValue(Messages.class);
                            postValues.put(snapshot.getKey(), snapshot.getValue());
                        }
                        if(dataSnapshot.getKey().equals(msg.messageId)&& dataSnapshot.getValue()!=null){
                        postValues.put("isRead", true);

                        myRef.child("mailbox").child(mAuth.getUid()).child(msg.messageId).updateChildren(postValues);}
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

       /* String key = myRef.child("mailbox").child(mAuth.getUid()).child(msg.messageId).getKey();
        Messages post = new Messages(true);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key,true);

        myRef.updateChildren(childUpdates);
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.read_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {

            myRef.child("mailbox").child(mAuth.getUid()).child(msg.messageId).removeValue();

            /*FirebaseAuth.getInstance().signOut();*/
            Intent i = new Intent(ReadActivity.this, ChatActivity.class);
            startActivity(i);
            finish();
            // Do something
            return true;
        }
        if (id == R.id.reply) {
            Intent i = new Intent(ReadActivity.this, ComposeActivity.class);
            //CharSequence[] items = users.toArray(new CharSequence[users.size()]);
            i.putExtra(READ_MSG,msg);
            startActivity(i);
            finish();
            // Do something*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
