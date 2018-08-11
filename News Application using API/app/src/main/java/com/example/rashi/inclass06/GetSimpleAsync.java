package com.example.rashi.inclass06;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Rashi on 2/19/2018.
 */

public class GetSimpleAsync extends AsyncTask<String, Void, ArrayList<Articles>> {

    MainActivity activity;
    RequestParams mparams;
    private ProgressDialog dialog;

    public GetSimpleAsync(RequestParams params, MainActivity activity) {
        mparams = params;
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading News...");
        dialog.show();
    }

    @Override
    protected ArrayList<Articles> doInBackground(String... params) {
        HttpURLConnection connection;
        connection = null;
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader reader = null;
        ArrayList<Articles> result = new ArrayList<>();

        try {
            URL url = new URL(mparams.getEncodedUrl(params[0]));
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String json = stringBuilder.toString();
                JSONObject root = null;
                try {
                    root = new JSONObject(json);
                    JSONArray articles = root.getJSONArray("articles");
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject articleJson = articles.getJSONObject(i);
                        Articles article = new Articles();
                        article.setAuthor(articleJson.getString("author"));
                        article.setTitle(articleJson.getString("title"));
                        article.setDescription(articleJson.getString("description"));
                        article.setUrl(articleJson.getString("url"));
                        article.setUrlToImage(articleJson.getString("urlToImage"));
                        article.setPublishedAt(articleJson.getString("publishedAt"));
                        Sources source = new Sources();
                        JSONObject sourceJson = articleJson.getJSONObject("source");
                        source.setId(sourceJson.getString("id"));
                        source.setName(sourceJson.getString("name"));
                        article.setSource(source);
                        result.add(article);
                    }
                    for (int i = 0; i < 100000; i++) {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(final ArrayList<Articles> arrayList) {
        Log.d("Demo", arrayList.toString());
        //int count= Integer.parseInt((String) hidden.getText());
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        activity.handleData(arrayList);
    }
}

