package com.example.rashi.inclass13;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ComposeActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseUser sender;
    TextView toName;
    EditText et;
    String recipientKey="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        sender=mAuth.getCurrentUser();
        setTitle("Compose Message");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        //// to set the image on the right side of the title bar /////
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(R.drawable.home);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        //layoutParams.rightMargin = 10;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ComposeActivity.this,ChatActivity.class);
                startActivity(i);
                finish();
            }

        });*/
       ////////////////////////////////////////////////////////////////

        final CharSequence[] items= (CharSequence[])getIntent().getCharSequenceArrayExtra("users");
        toName=  findViewById(R.id.textView2);
        findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ComposeActivity.this);
                builder.setTitle( Html.fromHtml("<font color='#0000FF'>Users</font>"));

                builder.setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        toName.setText(items[which]+"");


                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });


        Button sendBtn=(Button)findViewById(R.id.button5);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et=(EditText)findViewById(R.id.tt);
                final String msg=et.getText().toString();
                if((toName.getText().toString()).equals("")){
                    Toast.makeText(ComposeActivity.this, "No Recipient entered", Toast.LENGTH_SHORT).show();
                }else {
                    mDatabase.child("users").
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    sendMsg(msg,dataSnapshot);
                                    Intent i=new Intent(ComposeActivity.this,ChatActivity.class);
                                    startActivity(i);
                                    finish();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }
            }
        });

        ///////////////////////////////////////////////
        Messages msg = (Messages) getIntent().getExtras().getSerializable(ReadActivity.READ_MSG);
        if(msg!= null && msg.senderName!=null){
            toName.setText(msg.senderName);
        }

    }

    public void sendMsg(final String msg,DataSnapshot dataSnapshot){


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        for(DataSnapshot ds: dataSnapshot.getChildren()) {
            User u = new User();
            u = ds.getValue(User.class);
            if (u.name.equals(toName.getText())) {
                recipientKey = ds.getKey();

                String key = mDatabase.child("mailbox").child(recipientKey).push().getKey();
                Messages m=new Messages(sender.getDisplayName(),sender.getUid(),msg,formattedDate,false,key);
                mDatabase.child("mailbox").child(recipientKey).child(key).setValue(m);
                Toast.makeText(ComposeActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                break;
            }

        }
        toName.setText("");
        et.setText("");
    }


}
