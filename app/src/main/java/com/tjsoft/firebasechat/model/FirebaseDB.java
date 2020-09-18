package com.tjsoft.firebasechat.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tjsoft.firebasechat.model.entities.User;

public class FirebaseDB {

    private FirebaseDatabase database;

    public FirebaseDB(){
        database = FirebaseDatabase.getInstance();
    }

    public void saveUser(FirebaseUser user){
        User usr = new User();
        usr.setDisplayName(user.getDisplayName());
        usr.setEmail(user.getEmail());
        usr.setPhotoUrl((user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png");
        usr.setVerifyEmail(user.isEmailVerified());
        usr.setUid(user.getUid());

        DatabaseReference myRef = database.getReference("users");

        myRef.child(user.getUid()).setValue(usr);
    }

}
