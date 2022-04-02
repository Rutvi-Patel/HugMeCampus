package com.diamondTierHuggers.hugMeCampus.location;

public class LocationData{
    String url;
    String name;
    String coord;

    public LocationData(String imageUrl, String title, String coord) {
        this.url= imageUrl;
        this.name = title;
        this.coord = coord;
    }
}
