package com.example.rashi.class08;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener,DisplayFragment.OnFragmentInteractionListener,EditFragment.OnFragmentInteractionListener {

    public static final String ARG_PARAM3 = "param3";
    public static int REQ_CODE_NAME=100;
    public static int REQ_CODE_EMAIL=101;
    public static int REQ_CODE_DPT=102;
    public static int REQ_CODE_MOOD=103;
    public static int REQ_CODE=1000;
    public static String KEY_STUDENT="STUDENT_KEY";
    public static String KEY_REQ_TYPE="REQ_TYPE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MainActivity");
                    /*Intent i=new Intent(MainActivity.this,DisplayActivity.class);
                    i.putExtra(STUDENT_KEY,student);
                    startActivity(i);*/
               /* }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Please enter all the valid values",Toast.LENGTH_SHORT).show();
                }*/
        getSupportFragmentManager().beginTransaction().
                add(R.id.container,new MainFragment(),"main")
                .commit();
        //MainFragment fragment= (MainFragment) getSupportFragmentManager().findFragmentByTag("main");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void display(Student student) {
        setTitle("Display Activity");
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM3, student);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container,fragment,"display")
                .commit();
    }

    @Override
    public void editType(int reqCode,Student s ){
        setTitle("Edit Activity");
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_STUDENT,s);
        args.putInt(KEY_REQ_TYPE,reqCode);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container,fragment,"edit")
                .commit();
    }

    @Override
    public void updatedData(Student stu1){
        setTitle("Display Activity");
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM3, stu1);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container,fragment,"display")
                .commit();

    }

}

