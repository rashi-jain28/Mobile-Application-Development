package example.coolp.inclass07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by coolp on 2/26/2018.
 */

public class NewsAdapter extends ArrayAdapter<Articles> {
    Context context;
    public NewsAdapter(Context context, int resource, List<Articles> objects) {
        super(context, resource, objects);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        Articles news = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textViewtitle = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.textViewPublishedAt = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(getContext()).load(news.urlToImage).into(viewHolder.imageView);
        viewHolder.textViewtitle.setText(news.title);
        viewHolder.textViewPublishedAt.setText(news.publishedAt);


        return convertView;
    }

    //View Holder to cache the views
    private static class ViewHolder{
        ImageView imageView;
        TextView textViewtitle;
        TextView textViewPublishedAt;
    }
    }
