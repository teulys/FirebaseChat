package com.tjsoft.firebasechat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tjsoft.firebasechat.R;
import com.tjsoft.firebasechat.model.entities.User;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    private List<User> userList;

    public UserAdapter(List<User> users){
        this.userList = users;
    }


    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User user = userList.get(position);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, null);

        TextView tvName = view.findViewById(R.id.tvName);
        ImageView ivPhoto = view.findViewById(R.id.ivProfilePictureMe);

        tvName.setText(user.getEmail());

        Glide
            .with(parent.getContext())
            .load(user.getPhotoUrl())
            .circleCrop()
            .placeholder(R.drawable.google)
            .into(ivPhoto);


        return view;
    }
}
