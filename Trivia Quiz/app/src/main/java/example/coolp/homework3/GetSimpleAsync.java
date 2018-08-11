package example.coolp.homework3;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by coolp on 2/20/2018.
 */


public class GetSimpleAsync extends AsyncTask<String, Void, ArrayList<Question>> {

    // MainActivity activity;
    IData iData;
    ProgressBar pb;

    public GetSimpleAsync(IData iData, ProgressBar pb) {
        this.iData = iData;
        this.pb = pb;

    }

    @Override
    protected void onPreExecute() {
        pb.setProgress(0);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        ArrayList<Question> quiz = new ArrayList<>();
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    Question question = new Question();
                    String[] arr = line.split(";");
                    int qno = Integer.parseInt(arr[0]);
                    question.quesNo = qno + "";
                    question.question = arr[1];
                    question.url = arr[2];
                    for (int i = 3; i < arr.length - 1; i++) {
                        question.options.add(arr[i]);
                    }
                    question.ans = arr[arr.length - 1];
                    quiz.add(question);
                    pb.setMax(quiz.size());
                    //stringBuilder.append(line);
                    if (pb.getProgress() < quiz.size()) {
                        pb.incrementProgressBy(1);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return quiz;
    }

    @Override
    protected void onPostExecute(final ArrayList<Question> result) {
        pb.setVisibility(View.GONE);
        iData.handleListData(result);
        //activity.handleData(result);

    }

    public static interface IData {
        public void handleListData(ArrayList<Question> data);
       // public void updateProgress(int progress);
    }
}