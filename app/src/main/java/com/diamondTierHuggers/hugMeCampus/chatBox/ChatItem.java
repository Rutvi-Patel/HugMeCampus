package com.diamondTierHuggers.hugMeCampus.chatBox;

import android.net.Uri;

public class ChatItem {
    public String sender;
    public String data;
    public Double lat, lng;
    public String image;
    public String name;

    public ChatItem(){

    }

    public ChatItem(String sender, String data) {
        this.sender = sender;
        this.data = data;
    }
    public ChatItem(Double lat, Double lng, String img, String name, String sender) {
        this.sender = sender;
        this.lat = lat;
        this.lng = lng;
        this.image = img;
        this.name = name;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "sender='" + sender + '\'' +
                ", data='" + data + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", image=" + image +
                ", name='" + name + '\'' +
                '}';
    }
}
