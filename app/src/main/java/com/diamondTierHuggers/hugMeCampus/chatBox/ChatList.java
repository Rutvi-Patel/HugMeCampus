package com.diamondTierHuggers.hugMeCampus.chatBox;

public class ChatList {
    private String sender;
    private String receiver;
    private String time;
    private String data;

    public ChatList(){

    }

    public ChatList(String sender, String receiver, String time, String data) {
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.data = data;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTime() {
        return time;
    }

    public String getData() {
        return data;
    }
}
