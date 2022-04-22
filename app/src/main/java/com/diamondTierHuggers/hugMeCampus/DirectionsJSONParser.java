package com.diamondTierHuggers.hugMeCampus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionsJSONParser {

    public List<List<HashMap<String, String>>> parse2(JSONObject jObject) throws JSONException {

        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
        JSONArray json = ((JSONObject) jObject.getJSONArray("features").get(0)).getJSONObject("geometry").getJSONArray("coordinates");

        List path = new ArrayList<HashMap<String, String>>();

        String[] latLng = {"lng", "lat"};

        for (int i = 0; i < json.length(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            for (int j = 0; j < 2; j++) {
                hm.put(latLng[j], Double.toString((Double) ((JSONArray) json.get(i)).get(j)));
            }
            path.add(hm);
        }
        routes.add(path);

        return routes;
    }

}