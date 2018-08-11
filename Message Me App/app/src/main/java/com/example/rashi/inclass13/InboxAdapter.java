package com.example.rashi.inclass13;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Rashi on 4/23/2018.
 */

public class InboxAdapter extends ArrayAdapter<Messages> {

    private FirebaseAuth mAuth;
    public InboxAdapter(Context context, int resource, List<Messages> objects) {
        super(context, resource, objects);

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final Messages message = getItem(position);
        InboxAdapter.ViewHolder viewHolder;
        if(convertView == null){ //if no view to re-use then inflate a new one
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inbox_layout, parent, false);
            viewHolder = new InboxAdapter.ViewHolder();
            viewHolder.msg = (TextView) convertView.findViewById(R.id.text);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.time = (TextView) convertView.findViewById(R.id.date);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (InboxAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.msg.setText(message.text);
        viewHolder.time.setText(message.created_at);
        viewHolder.name.setText(message.senderName);


        if(!message.isRead) {
            Picasso.with(getContext()).load(R.drawable.circle_blue).into(viewHolder.imageView);
           // Log.d("adapter", "" + viewHolder.name.getText().toString());
        }
        else{
            Picasso.with(getContext()).load(R.drawable.circle_grey).into(viewHolder.imageView);
        }

        //set the data from the email object
      /*  if(user.getUid().equals(message.user_id)){
            viewHolder.cancel.setVisibility(View.VISIBLE);
            viewHolder.name.setText("Me");
        }else{

            viewHolder.cancel.setVisibility(View.INVISIBLE);
            viewHolder.name.setText(message.username);
        }
        viewHolder.msg.setText(message.message);

        if(viewHolder.cancel.getVisibility()==View.VISIBLE) {
            viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ChatRoomActivity) parent.getContext()).deleteMessage(message, position);

                }
            });
        }
        try {
            PrettyTime p = new PrettyTime();
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d2 = df2.parse(message.created_at);
            String textTime = p.format(d2).toString();
            viewHolder.time.setText(textTime);
        }catch(Exception e){}*/

        return convertView;
    }
    //View Holder to cache the views
    private static class ViewHolder{
        TextView msg,name,time;
        ImageView imageView;
    }

}
