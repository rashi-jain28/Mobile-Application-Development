package com.example.rashi.hw04;

import android.os.AsyncTask;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Rashi on 2/24/2018.
 */

public class GetNewsAsync extends AsyncTask<String, Void, ArrayList<Items>> {

    IData iData;

    public GetNewsAsync(IData iData) {
        this.iData = iData;
    }

    protected void onPreExecute() {

        super.onPreExecute();
        iData.dialogProgress();
    }

    @Override
    protected void onPostExecute(ArrayList<Items> items) {
        //super.onPostExecute(items);
        iData.handleListData(items);
    }

    @Override
    protected ArrayList doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        ArrayList<Items> result = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
               result= ItemParser.ItemsSAXParser.parseItems(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
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
        return result;

    }
    public static interface IData {
        public void handleListData(ArrayList<Items> data);
        // ipublic void updateProgress(int progress);
        public void dialogProgress();

    }
}
