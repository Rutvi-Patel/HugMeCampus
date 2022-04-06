package com.diamondTierHuggers.hugMeCampus.location;

import java.io.Serializable;

public class LocationData implements Serializable {
    String url;
    String name;
    String coord;

    public LocationData(String imageUrl, String title, String coord) {
        this.url= imageUrl;
        this.name = title;
        this.coord = coord;
    }
}
