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

public class CreateTaskActivity extends AppCompatActivity {

    private TextView date;
    private TextView time;
    private DatePickerDialog.OnDateSetListener datesetListener;
    private TimePickerDialog.OnTimeSetListener timesetListener;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        date=findViewById(R.id.dd);
        final TextView title= findViewById(R.id.task);
        RadioGroup rg= findViewById(R.id.radioGroup);
        final RadioButton[] rb = new RadioButton[1];
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                rb[0] = (RadioButton) findViewById(checkedId);
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
                DatePickerDialog dialog= new DatePickerDialog(CreateTaskActivity.this, android.R.style.Theme_Holo_NoActionBar,
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
            }
        };

        time= findViewById(R.id.tt);
        time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Calendar t = Calendar.getInstance();
                                        int hours = t.get(Calendar.HOUR_OF_DAY);
                                        int min = t.get(Calendar.MINUTE);
                                        int sec = t.get(Calendar.SECOND);
                                        int am=t.get(Calendar.AM_PM);
;                                        TimePickerDialog dialog = new TimePickerDialog(CreateTaskActivity.this,android.R.style.Theme_Holo_Light,
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
                            }
                        };

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentTask currentTask= new CurrentTask(title.getText().toString(), date.getText().toString(),
                        time.getText().toString(),rb[0].getText().toString());
                Intent intent= new Intent(CreateTaskActivity.this,MainActivity.class);
                intent.putExtra(MainActivity.taskKey, (Serializable) currentTask);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
