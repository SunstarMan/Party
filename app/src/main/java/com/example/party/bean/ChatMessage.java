package com.example.party.bean;

public class ChatMessage {
    private String message;
    private int type;
    public final static int RECEIVE = 1;
    public final static int SEND = 2;

    public ChatMessage(String message, int type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
