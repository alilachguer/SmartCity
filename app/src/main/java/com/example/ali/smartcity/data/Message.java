package com.example.ali.smartcity.data;

public class Message {

    public String sender, text;

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public Message(){

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
