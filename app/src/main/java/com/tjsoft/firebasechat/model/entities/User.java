package com.tjsoft.firebasechat.model.entities;


import java.io.Serializable;

public class User implements Serializable {

    private String uid;
    private String displayName;
    private String photoUrl;
    private String email;
    private boolean verifyEmail;

    public User(){

    }

    public User(String uid, String displayName, String photoUrl, String email, boolean verifyEmail) {
        this.uid = uid;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.email = email;
        this.verifyEmail = verifyEmail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerifyEmail() {
        return verifyEmail;
    }

    public void setVerifyEmail(boolean verifyEmail) {
        this.verifyEmail = verifyEmail;
    }
}

