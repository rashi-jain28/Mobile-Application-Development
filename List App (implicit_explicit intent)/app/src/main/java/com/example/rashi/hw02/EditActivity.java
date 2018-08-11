package com.example.rashi.hw02;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private TextView date;
    private TextView time;
    private DatePickerDialog.OnDateSetListener datesetListener;
    private TimePickerDialog.OnTimeSetListener timesetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        date=findViewById(R.id.ddEdit);
        final TextView title= findViewById(R.id.taskEdit);
        RadioGroup rg= findViewById(R.id.radioGroupEdit);
        final RadioButton[] rb = new RadioButton[1];
        final RadioButton[] rb1 = new RadioButton[rg.getChildCount()];
        time= findViewById(R.id.ttEdit);
        final CurrentTask ct= (CurrentTask)getIntent().getExtras().getSerializable(MainActivity.taskKey);
        title.setText(ct.getTitle());
        date.setText(ct.getDate());
        time.setText(ct.getTime());
        int count= rg.getChildCount();
        for(int i=0;i<count;i++){
            rb1[i]=(RadioButton)rg.getChildAt(i);
            if(rb1[i].getText().toString().equals(ct.getPriority()))
                rb1[i].setChecked(true);
        }
        /*
        if(rb[0].getText().toString().equals(ct.getPriority())){
            rb[0].setChecked(true);
        }*/
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                rb[0] = (RadioButton) findViewById(checkedId);
                ct.setPriority(rb[0].getText().toString());
            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar calender= Calendar.getInstance();
                int year=calender.get(Calendar.YEAR);
                int month= calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog= new DatePickerDialog(EditActivity.this, android.R.style.Theme_Holo_NoActionBar,
                        datesetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        datesetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                String date_final= month + "/" +day+ "/" +year;
                date.setText(date_final);
                ct.setDate(date_final);
            }
        };


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar t = Calendar.getInstance();
                int hours = t.get(Calendar.HOUR_OF_DAY);
                int min = t.get(Calendar.MINUTE);
                int sec = t.get(Calendar.SECOND);
                int am=t.get(Calendar.AM_PM);
                ;       TimePickerDialog dialog = new TimePickerDialog(EditActivity.this,android.R.style.Theme_Holo_Light,
                        timesetListener, hours, min, false);
                dialog.show();
            }
        });
        timesetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                String time_final;
                if(hour<12) {
                    time_final = hour + ":" + min + " A.M";
                }
                else if(hour==12){
                       time_final = (hour) + ":" + min + " P.M";
                    }
                else {
                       time_final = (hour - 12) + ":" + min + " P.M";
                   }
                time.setText(time_final);
                ct.setTime(time_final);
            }
        };

        findViewById(R.id.saveEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView title1= findViewById(R.id.taskEdit);
                ct.setTitle(title1.getText().toString());
                Intent intent= new Intent(EditActivity.this,MainActivity.class);
                intent.putExtra(MainActivity.taskKey, (Serializable) ct);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
