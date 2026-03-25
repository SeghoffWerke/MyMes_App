package com.example.mymesapp;

public class Message {
    private String text;
    private String senderId;
    private String reciverId;

    public Message(String text, String senderId, String reciverId) {
        this.text = text;
        this.senderId = senderId;
        this.reciverId = reciverId;
    }

    public Message() {
    }

    public String getText() {
        return text;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReciverId() {
        return reciverId;
    }
}
