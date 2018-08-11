package example.coolp.homework3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity implements GetImage.IData {

    final static String CorrectAnswer="CorrectAnswer";
    int correctCount=0;
    TextView qno;
    TextView ques;
    Button next;
    RadioGroup rg;
    ProgressBar pb;
    TextView loadingText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final TextView mTextField = findViewById(R.id.timeTextView);
        pb= (ProgressBar)findViewById(R.id.progressBar2);
        loadingText=(TextView)findViewById(R.id.loadText);
        qno= findViewById(R.id.qnoTextView);
        ques= findViewById(R.id.quesTextView);
        next=(Button)findViewById(R.id.nextButton);
        final Button quit=(Button)findViewById(R.id.quitbutton);
        rg=(RadioGroup)findViewById(R.id.radioGroup);
        final ArrayList<Question> quesList= (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.QuestionList);
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("Time left: " + millisUntilFinished / 1000);
                mTextField.setText( "Time left: "+String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                mTextField.setText("Time Up!");
                callStatActivity(quesList);
            }

        }.start();

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1= new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i1);
            }
        });

        Log.d("demo",quesList.toString());
        displayQuestion(quesList.get(0));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedAns=-1;
               if(rg.getCheckedRadioButtonId()!=-1) {
                    selectedAns = rg.getCheckedRadioButtonId();
                }
                Log.d("demo1",rg.getCheckedRadioButtonId()+"");
                int currentQues=Integer.parseInt(qno.getText().toString().split("Q")[1])-1;
                if(selectedAns==Integer.parseInt(quesList.get(currentQues).getAns())){
                    correctCount++;
                }
                if(currentQues<(quesList.size()-1)) {
                    displayQuestion(quesList.get(currentQues + 1));
                }
                else{
                    callStatActivity(quesList);
                }
            }
        });



    }

    void callStatActivity(ArrayList<Question> quesList){
        Intent statIntent= new Intent(Main2Activity.this,StatsActivity.class);
        statIntent.putExtra(CorrectAnswer,correctCount);
        statIntent.putExtra(MainActivity.QuestionList,quesList);
        startActivity(statIntent);
    }
    void displayQuestion(Question question){
        int quesNoDisplay=Integer.parseInt(question.getQuesNo())+1;
        qno.setText("Q"+quesNoDisplay);
        ques.setText(question.getQuestion());
        new GetImage((ImageView)findViewById(R.id.imageView2),Main2Activity.this,pb,loadingText).execute(question.getUrl());
        int number = question.getOptions().size();
        rg.removeAllViews();
        for (int i = 0; i < number; i++) {
            RadioButton rdbtn = new RadioButton(Main2Activity.this);
            rdbtn.setId(i);
            rdbtn.setText(question.getOptions().get(i).toString());
            rg.addView(rdbtn);
        }
    }

    @Override
    public void handleListData() {

    }

   /* private class GetImage extends AsyncTask<String, Void, Void> {
        ImageView imageView;
        Bitmap bitmap = null;

        public GetImage(ImageView iv) {
            imageView = iv;
        }
        ProgressBar pb;
        TextView loadingText;
        @Override
        protected void onPreExecute() {
            pb= (ProgressBar)findViewById(R.id.progressBar2);
            pb.setProgress(0);
            pb.setVisibility(View.VISIBLE);
            loadingText=(TextView)findViewById(R.id.loadText);
            loadingText.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... params) {

            HttpURLConnection connection = null;
            bitmap = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            pb.setVisibility(View.GONE);
            loadingText.setVisibility(View.GONE);
            if (bitmap != null && imageView != null) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
*/
}
