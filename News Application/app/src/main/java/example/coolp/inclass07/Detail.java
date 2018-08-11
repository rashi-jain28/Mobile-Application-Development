package example.coolp.inclass07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView title=(TextView)findViewById(R.id.textView3);
        TextView pubAt=(TextView)findViewById(R.id.textView4);
        TextView desc=(TextView)findViewById(R.id.textView5);
        ImageView image=(ImageView)findViewById(R.id.imageView2);
        setTitle("Detail");
        if (getIntent() != null && getIntent().getExtras() != null) {

                 Articles aa = (Articles) getIntent().getExtras().getSerializable(Main2Activity.ArticleKey);
                //RequestParams params = new RequestParams();
title.setText(aa.getTitle());
pubAt.setText(aa.publishedAt);
desc.setText(aa.getDescription());
            Picasso.with(getBaseContext()).load(aa.urlToImage).into(image);

            }
        }
}
