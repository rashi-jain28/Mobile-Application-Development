package example.coolp.homework3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by coolp on 2/20/2018.
 */

public class GetImage extends AsyncTask<String, Void, Void> {
    ImageView imageView;
    Bitmap bitmap = null;
    ProgressBar pb;
    TextView loadingText;
    //Main2Activity activity;
    IData iData;
    public GetImage(ImageView iv,IData iData,ProgressBar pb, TextView loadingText )
    {
        imageView = iv;
       // this.activity=activity;
        this.iData = iData;
        this.pb=pb;
        this.loadingText=loadingText;
    }

    @Override
    protected void onPreExecute() {

        pb.setProgress(0);
        pb.setVisibility(View.VISIBLE);

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
        iData.handleListData();
      //  activity.handleData();
    }

    public static interface IData {
        public void handleListData();
        // public void updateProgress(int progress);
    }
}

