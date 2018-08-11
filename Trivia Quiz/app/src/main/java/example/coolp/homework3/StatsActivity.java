package example.coolp.homework3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Button quit=(Button)findViewById(R.id.quitButton2);
        Button tryAgain=(Button)findViewById(R.id.tryAgainButton);
        TextView correctCount= (TextView)findViewById(R.id.correctCount);
        TextView rT=(TextView)findViewById(R.id.responseText);
        final ArrayList<Question> quesList= (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.QuestionList);
        final int correctAnswers= (Integer) getIntent().getExtras().getInt(Main2Activity.CorrectAnswer);
        float per= (float) ((1.0*correctAnswers)/quesList.size());
        int percentage= (int) (per*100);
        rT.setText("");
        correctCount.setText(percentage+"%");

        if(percentage!=100){
            rT.setText("Try again and see if you can get all the correct answers!");
        }
        final ProgressBar sb=(ProgressBar) findViewById(R.id.progressBar3);
        sb.setMax(100);
        sb.setProgress(percentage);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1= new Intent(StatsActivity.this,MainActivity.class);
                //i1.putExtra(MainActivity.QuestionList,quesList);
                startActivity(i1);
            }
        });
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1= new Intent(StatsActivity.this,Main2Activity.class);
                i1.putExtra(MainActivity.QuestionList,quesList);
                startActivity(i1);
            }
        });

    }
}
