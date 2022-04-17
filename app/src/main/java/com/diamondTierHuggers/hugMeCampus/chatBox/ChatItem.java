package com.diamondTierHuggers.hugMeCampus.chatBox;

public class ChatItem {
    public String sender;
    public String data;
    public String coord;
    public String img;
    public String name;

    public ChatItem(){

    }

    public ChatItem(String sender, String data) {
        this.sender = sender;
        this.data = data;
    }
    public ChatItem(String coord, String img, String name, String sender) {
        this.sender = sender;
        this.coord = coord;
        this.img = img;
        this.name = name;
    }

    public String getSender() {
        return sender;
    }

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCoord() {
        return coord;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "sender='" + sender + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
