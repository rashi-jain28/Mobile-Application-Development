package example.coolp.mainactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String STUDENT_KEY = "value";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");
         Button submitButton= (Button) findViewById(R.id.button);
        final EditText name= (EditText)findViewById(R.id.nameTextEdit);
        final EditText email= (EditText)findViewById(R.id.emailTextEdit);
        final RadioGroup rg=(RadioGroup)findViewById(R.id.dptGroupEdit);
        final String[] dptValue = new String[1];
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 RadioButton rb=(RadioButton) findViewById(checkedId);
                dptValue[0] = rb.getText().toString();
            }
        });


        final SeekBar sb= (SeekBar)findViewById(R.id.seekBar);

       /* final String tv="";

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int moodValue=sb.getProgress();
                    String sName=name.getText().toString();
                    String sEmail=email.getText().toString();

                    String dpt= dptValue[0];
                    Student student=new Student(sName,sEmail,dpt,moodValue);
                    Intent i=new Intent(MainActivity.this,DisplayActivity.class);
                    i.putExtra(STUDENT_KEY,student);
                    startActivity(i);
               }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Please enter all the valid values",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
