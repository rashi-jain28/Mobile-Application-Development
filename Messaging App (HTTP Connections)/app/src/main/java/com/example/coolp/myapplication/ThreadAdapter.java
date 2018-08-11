package com.example.coolp.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by coolp on 4/7/2018.
 */

public class ThreadAdapter extends ArrayAdapter<Threads> {

    private final OkHttpClient client = new OkHttpClient();
    TokenResponse tr;
    public ThreadAdapter( Context context, int resource, @NonNull List<Threads> objects,TokenResponse tr) {
        super(context, resource, objects);
        this.tr=tr;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Log.d("thread[0]",""+getItem(position));

       final Threads title = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){ //if no view to re-use then inflate a new one
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.thread_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.textView3);
            viewHolder.ib=(ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //set the data from the email object
        viewHolder.textViewTitle.setText(title.title);
        if(tr.getUser_id().equals(title.user_id)){
            viewHolder.ib.setVisibility(View.VISIBLE);
            viewHolder.ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Main4Activity) parent.getContext()).deleteThread(title, tr,position);

                }
            });
        }else{

            viewHolder.ib.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }




    //View Holder to cache the views
    private static class ViewHolder{
        TextView textViewTitle;
        ImageView ib;


    }
}


