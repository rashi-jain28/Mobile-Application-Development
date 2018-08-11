package example.coolp.inclass07;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
ArrayList<Articles> news= new ArrayList<Articles>();
    public static final String ArticleKey= "ak";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (isConnected()) {
                String text = getIntent().getStringExtra(MainActivity.CategoryKey);
                RequestParams params = new RequestParams();
                params.addParameter("category", text);
                setTitle(text);
                new GetSimpleAsync(params).execute("https://newsapi.org/v2/top-headlines?country=us&apiKey=557753f0eff14f55ac2800976ac27a95");

            } else {
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }


    private class GetSimpleAsync extends AsyncTask<String, Void, ArrayList<Articles>> {
        RequestParams mparams;
        //public static final String ArticleKey="ArticleKey";
        private ProgressDialog dialog;

        public GetSimpleAsync(RequestParams params) {
            mparams = params;
            dialog = new ProgressDialog(Main2Activity.this);
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
                //URL url = new URL(params[0]);
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

            news.clear();
            news.addAll(arrayList);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            ListView listView = (ListView) findViewById(R.id.listView2);
            NewsAdapter adapter = new NewsAdapter(Main2Activity.this, R.layout.news_item, news);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Intent i= new Intent
                    Intent intent=new Intent(Main2Activity.this,Detail.class);
                    Articles a = news.get(i);
                    intent.putExtra(ArticleKey,a);
                    startActivity(intent);
                }
            });


        }
    }
    }

