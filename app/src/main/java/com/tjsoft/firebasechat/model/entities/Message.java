package com.tjsoft.firebasechat.model.entities;

public class Message {

    private String id;
    private String text;
    private String time;
    private boolean transmitter;

    public Message(){

    }

    public Message(String id, String text, String time, boolean transmitter) {
        this.id = id;
        this.text = text;
        this.time = time;
        this.transmitter = transmitter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTransmitter() {
        return transmitter;
    }

    public void setTransmitter(boolean transmitter) {
        this.transmitter = transmitter;
    }
}
