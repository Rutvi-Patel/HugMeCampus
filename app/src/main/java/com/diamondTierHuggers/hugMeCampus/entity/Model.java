package com.diamondTierHuggers.hugMeCampus.entity;

public class Model {
    private String imageURL;

    public Model () {

    }

    public Model (String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
