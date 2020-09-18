package com.tjsoft.firebasechat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tjsoft.firebasechat.R;
import com.tjsoft.firebasechat.model.entities.Message;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChatAdapter extends BaseAdapter {

    private List<Message> messageList;
    private String tranPhoto = "";
    private String rePhoto = "";

    public ChatAdapter(List<Message> messageList, String tranPhoto, String rePhoto){
        this.messageList = messageList;
        this.tranPhoto = tranPhoto;
        this.rePhoto = rePhoto;
    }


    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message message = messageList.get(position);

        View view = null;

        if (message.isTransmitter()){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transmitter, null);

            TextView chatText = view.findViewById(R.id.tvTextChat);
            TextView chatTime = view.findViewById(R.id.tvTime);
            ImageView ivPhoto = view.findViewById(R.id.ivProfilePictureMe);

            chatText.setText(message.getText());
            chatTime.setText(timeToShow(message.getTime()));

            Glide
                    .with(parent.getContext())
                    .load(tranPhoto)
                    .circleCrop()
                    .placeholder(R.drawable.google)
                    .into(ivPhoto);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receiver, null);

            TextView chatText = view.findViewById(R.id.tvTextChat);
            TextView chatTime = view.findViewById(R.id.tvTime);
            ImageView ivPhoto = view.findViewById(R.id.ivProfilePictureMe);

            chatText.setText(message.getText());
            chatTime.setText(timeToShow(message.getTime()));

            Glide
                    .with(parent.getContext())
                    .load(rePhoto)
                    .circleCrop()
                    .placeholder(R.drawable.google)
                    .into(ivPhoto);
        }

        return view;
    }

    private String timeToShow(String time){
        try
        {
            Date past = new Date(Long.parseLong(time) * 1000);
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
//
//          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
//          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
//          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
//          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");

            if(seconds<60)
            {
                return (seconds+" seconds ago");
            }
            else if(minutes<60)
            {
                return  (minutes+" minutes ago");
            }
            else if(hours<24)
            {
                return (hours+" hours ago");
            }
            else
            {
                return (days+" days ago");
            }
        }
        catch (Exception j){
            j.printStackTrace();
            return "";
        }
    }
}
