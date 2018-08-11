package example.coolp.inclass04;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.RecoverySystem;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
Group 14- Priyanka Taneja and Rashi Jain
InClass04
 */
public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int minimumValuePwdCount=1;
    int minimumValuePwdLength=8;
    ExecutorService threadPool;
    Handler handler;
    Integer count=1;
    ArrayList<String> passwords=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("InClass4");
        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        final SeekBar seekLen= (SeekBar) findViewById(R.id.seekBarlen);
        final TextView seekLenValue= (TextView)findViewById(R.id.pwdLenthProgress);
        final SeekBar seekCount= (SeekBar) findViewById(R.id.seekBarCount);
        final TextView seekCountValue= (TextView)findViewById(R.id.pwdCountProgress);
        final TextView pwdText=(TextView)findViewById(R.id.pwdText);
        seekCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = minimumValuePwdCount;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = minimumValuePwdCount+ progress;
                seekCountValue.setText(progressChanged+"");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekLen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = minimumValuePwdLength;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressChanged = minimumValuePwdLength+ progress;
                seekLenValue.setText(progressChanged+"");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        threadPool= Executors.newFixedThreadPool(2);
        findViewById(R.id.threadButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                passwords.clear();
                if(seekLenValue.getText().toString().isEmpty() ||seekCountValue.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please choose the values", Toast.LENGTH_SHORT).show();
                }
                else
                {
                int countPwd = Integer.parseInt(seekCountValue.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(countPwd);
                for (int i = 0; i < countPwd; i++) {
                    threadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            String pw = Util.getPassword(Integer.parseInt(seekLenValue.getText().toString()));
                            Message msg = new Message();
                            msg.obj = pw;
                            handler.sendMessage(msg);
                        }
                    });
                }
            }
        }
        });

        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg){
                    passwords.add((String) msg.obj);
                    progressBar.incrementProgressBy(1);
                    if (passwords.size() == Integer.parseInt(seekCountValue.getText().toString())) {
                        progressBar.setVisibility(View.GONE);// setProgress(0);
                        final String[] strings = passwords.toArray(new String[passwords.size()]);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pwdText.setText(strings[which]);

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }

                return false;
            }

        });


        findViewById(R.id.asyncButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwords.clear();
                if(seekLenValue.getText().toString().isEmpty() ||seekCountValue.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please choose the values", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    count = 1;
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(0);
                    new MyTask().execute(Integer.parseInt(seekCountValue.getText().toString()));
                }
            }
        });
    }


   private class MyTask extends AsyncTask<Integer,Integer,ArrayList<String>>{

       final TextView seekLenValue= (TextView)findViewById(R.id.pwdLenthProgress);
       final TextView seekCountValue= (TextView)findViewById(R.id.pwdCountProgress);
       final TextView pwdText=(TextView)findViewById(R.id.pwdText);
        @Override
        protected void onPreExecute() {
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            progressBar.setVisibility(View.GONE);
            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
           // final ArrayList<String> sList=s;
            final String[] strings=s.toArray(new String[s.size()]);
            builder.setItems(strings, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pwdText.setText(strings[which]);
                }
            });
            AlertDialog alert=builder.create();
            alert.show();


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);

        }

        @Override
        protected ArrayList<String> doInBackground(Integer... integers) {
            progressBar.setMax(integers[0]);

            TextView len = (TextView) findViewById(R.id.pwdLenthProgress);
            int pwdlength = Integer.parseInt(seekLenValue.getText().toString());
            int pwdCount = Integer.parseInt(seekCountValue.getText().toString());
            for (int  pCount=0 ; pCount < integers[0]; pCount++) {
                try {
                    Thread.sleep(100);
                    String pwd = Util.getPassword(pwdlength);
                    if (passwords.size() <= pwdCount) {
                        passwords.add(pwd);
                        progressBar.incrementProgressBy(1);
                    }
                    publishProgress(pCount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return passwords;

        }
    }
}

