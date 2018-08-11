package com.example.coolp.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by coolp on 4/8/2018.
 */

public class MessageAdapter extends ArrayAdapter<Messages> {
    TokenResponse tr;
    public MessageAdapter(Context context, int resource, List<Messages> objects,TokenResponse tr) {
        super(context, resource, objects);
        this.tr=tr;
    }
    /*public MessageAdapter(Context context, int resource, @NonNull List<Messages> objects) {
        super();
    }*/
    @Override
    public View getView(final int position, View convertView,final ViewGroup parent) {
        final Messages message = getItem(position);
        MessageAdapter.ViewHolder viewHolder;
        if(convertView == null){ //if no view to re-use then inflate a new one
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chatting_layout, parent, false);
            viewHolder = new MessageAdapter.ViewHolder();
            viewHolder.msg = (TextView) convertView.findViewById(R.id.msg);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.cancel = (ImageView) convertView.findViewById(R.id.cancel);


            convertView.setTag(viewHolder);
        } else{
            viewHolder = (MessageAdapter.ViewHolder) convertView.getTag();
        }
        //set the data from the email object
        if(tr.getUser_id().equals(message.user_id)){
            viewHolder.cancel.setVisibility(View.VISIBLE);
            viewHolder.name.setText("Me");
        }else{

            viewHolder.cancel.setVisibility(View.INVISIBLE);
            viewHolder.name.setText(message.user_fname+""+message.user_lname);
        }
        viewHolder.msg.setText(message.message);

        if(viewHolder.cancel.getVisibility()==View.VISIBLE) {
            viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ChatRoomActivity) parent.getContext()).deleteMessage(message, tr, position);

                }
            });
        }
        try {
            PrettyTime p = new PrettyTime();
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d2 = df2.parse(message.created_at);
            String textTime = p.format(d2).toString();
            viewHolder.time.setText(textTime);
        }catch(Exception e){}

        return convertView;
    }
    //View Holder to cache the views
    private static class ViewHolder{
        TextView msg,name,time;
        ImageView cancel;
    }
}