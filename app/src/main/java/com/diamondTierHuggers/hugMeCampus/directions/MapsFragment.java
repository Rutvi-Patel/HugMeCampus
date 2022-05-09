package com.diamondTierHuggers.hugMeCampus.directions;

import static com.diamondTierHuggers.hugMeCampus.main.AppUser.lat;
import static com.diamondTierHuggers.hugMeCampus.main.AppUser.lng;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diamondTierHuggers.hugMeCampus.BuildConfig;
import com.diamondTierHuggers.hugMeCampus.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapsFragment extends Fragment {
    LatLng dest = new LatLng(30.705493, 76.801256);
    Double destLat, destLng;
    LatLng csulb = new LatLng(33.77490865020179, -118.12386059997482);
    ProgressDialog progressDialog;
    GoogleMap mGoogleMap;

    private static final String ARG_PARAM1 = "lat";
    private static final String ARG_PARAM2 = "lng";

    public static MapsFragment newInstance(Double param1, Double param2) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_PARAM1, param1);
        args.putDouble(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
//            mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

            mGoogleMap = googleMap;
            googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_no_lables)
            );
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
            mGoogleMap.getUiSettings().setCompassEnabled(true);

            LatLng destination = new LatLng(destLat, destLng);
            mGoogleMap.addMarker(new MarkerOptions()
                .position(destination)
            );

            mGoogleMap.addGroundOverlay(new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.csulbmap)).anchor(0, 1)
                    .position(csulb, 1475f, 1550f));

            //Initialize Google Play Services
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mGoogleMap.setMyLocationEnabled(true);
                }
            } else {
                mGoogleMap.setMyLocationEnabled(true);
            }
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lng))
                    .zoom(17)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            destLat = getArguments().getDouble(ARG_PARAM1);
            destLng = getArguments().getDouble(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
            drawPolylines();
        }
    }


    private void drawPolylines() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait, Loading Directions...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Checks, whether start and end locations are captured
        // Getting URL to the Google Directions API
        String url = getDirectionsUrl();
        DownloadTask downloadTask = new DownloadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                System.out.println("Background Task" + " " + e.toString());
            }
            return data;
        }
//
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse2(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            System.out.println(routes);
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            progressDialog.dismiss();
//            Log.d("result", result.toString());
            ArrayList points = null;
            PolylineOptions lineOptions = null;

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(20);
                lineOptions.color(Color.BLUE);
                lineOptions.geodesic(true);

            }

            // Drawing polyline in the Google Map for the i-th route
            mGoogleMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl() {
        String url = "https://api.openrouteservice.org/v2/directions/foot-walking?api_key=" + BuildConfig.OPEN_ROUTE_SERVICE_KEY + "&start=" + Double.toString(lng) + "," + Double.toString(lat) + "&end=" + destLng + "," + destLat;
        return url;
    }


    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}