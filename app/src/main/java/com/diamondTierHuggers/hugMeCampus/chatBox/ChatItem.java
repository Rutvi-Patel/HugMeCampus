package com.diamondTierHuggers.hugMeCampus.chatBox;

public class ChatItem {
    public String sender;
    public String data;

    public ChatItem(){

    }

    public ChatItem(String sender, String data) {
        this.sender = sender;
        this.data = data;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "sender='" + sender + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
