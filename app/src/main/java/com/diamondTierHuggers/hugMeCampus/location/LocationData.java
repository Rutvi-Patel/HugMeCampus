package com.diamondTierHuggers.hugMeCampus.location;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;

import android.net.Uri;

import java.io.Serializable;

public class LocationData implements Serializable {
    Double lat;
    Double lng;
    String name;
    String image;
    String sender = appUser.getAppUser().getUid();


    public LocationData (String name, Double lat, Double lng, String img) {
        this.name = name;
        this.image = img;
        this.lat = lat;
        this.lng = lng;
    }
    public LocationData () {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", sender='" + sender + '\'' +
                '}';
    }
}
