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

import java.util.List;

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
            ImageView ivPhoto = view.findViewById(R.id.ivProfilePictureMe);

            chatText.setText(message.getText());

            Glide
                    .with(parent.getContext())
                    .load(tranPhoto)
                    .circleCrop()
                    .placeholder(R.drawable.google)
                    .into(ivPhoto);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receiver, null);

            TextView chatText = view.findViewById(R.id.tvTextChat);
            ImageView ivPhoto = view.findViewById(R.id.ivProfilePictureMe);

            chatText.setText(message.getText());

            Glide
                    .with(parent.getContext())
                    .load(rePhoto)
                    .circleCrop()
                    .placeholder(R.drawable.google)
                    .into(ivPhoto);
        }

        return view;
    }
}
