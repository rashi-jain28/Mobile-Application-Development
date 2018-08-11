package com.example.coolp.firstfirebaseproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        final TextView fname= findViewById(R.id.fname);
        final TextView lname= findViewById(R.id.lname);
        final TextView email= findViewById(R.id.email);
        final TextView pwd= findViewById(R.id.pwd);
        final TextView rpwd= findViewById(R.id.rpwd);
        Button b=(Button) findViewById(R.id.button4);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

                Intent i=new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

    public void performSignUp(String email, String password, final String fname,final String lname) throws Exception {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("demo", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fname + " "+ lname).build();
                            user.updateProfile(profileUpdates);
                            onAuthSuccess(user,fname+" "+lname);
                        } else {
                            Log.w("demo", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                       /* Intent i = new Intent(SignUpActivity.this, Main4Activity.class);
                        startActivity(i);
                        finish();*/
                    }
                });

    }
    private void onAuthSuccess(FirebaseUser user,String name) {

        Intent i= new Intent(SignUpActivity.this, Main4Activity.class);
        i.putExtra("username",name);
        startActivity(i);

        finish();
    }


    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}