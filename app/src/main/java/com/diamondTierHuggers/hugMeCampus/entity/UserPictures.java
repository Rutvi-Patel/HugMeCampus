package com.diamondTierHuggers.hugMeCampus.entity;

public class UserPictures {

    public String profile;
    public String picture_1;
    public String picture_2;
    public String picture_3;

    public UserPictures() {
    }

    public UserPictures(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPicture_1() {
        return picture_1;
    }

    public void setPicture_1(String picture_1) {
        this.picture_1 = picture_1;
    }

    public String getPicture_2() {
        return picture_2;
    }

    public void setPicture_2(String picture_2) {
        this.picture_2 = picture_2;
    }

    public String getPicture_3() {
        return picture_3;
    }

    public void setPicture_3(String picture_3) {
        this.picture_3 = picture_3;
    }

    @Override
    public String toString() {
        return "UserPictures{" +
                "profile='" + profile + '\'' +
                ", picture_1='" + picture_1 + '\'' +
                ", picture_2='" + picture_2 + '\'' +
                ", picture_3='" + picture_3 + '\'' +
                '}';
    }
}
