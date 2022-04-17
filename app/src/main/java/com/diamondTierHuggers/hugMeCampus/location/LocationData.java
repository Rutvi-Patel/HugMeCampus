package com.diamondTierHuggers.hugMeCampus.location;

public class LocationData{
    String coord;
    String name;
    String image;
    String sender;


    public LocationData (String sender, String name, String coord, String img) {
        this.sender = sender;
        this.coord = coord;
        this.name = name;
        this.image = img;
    }
    public LocationData () {
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "sender='" + sender + '\'' +
                ", coord='" + coord + '\'' +
                '}';
    }
}
