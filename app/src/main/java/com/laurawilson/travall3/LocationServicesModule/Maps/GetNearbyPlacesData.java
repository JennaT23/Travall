package com.laurawilson.travall3.LocationServicesModule.Maps;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];

        DownloadURL downloadURL = new DownloadURL();
        try {
            googlePlacesData = downloadURL.readURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    public void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
        for (int i =0; i < nearbyPlaceList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePLace = nearbyPlaceList.get(i);

            String placeName = googlePLace.get("place_name");
            String vicinity = googlePLace.get("vicinity");
            double lat = Double.parseDouble(Objects.requireNonNull(googlePLace.get("lat")));
            double lng = Double.parseDouble(Objects.requireNonNull(googlePLace.get("lng")));

            LatLng latLng = new LatLng(lat, lng);

            markerOptions.position(latLng);
            markerOptions.title(placeName + " " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        }
    }
    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList = null;
        DataParser parser = new DataParser();

        nearbyPlaceList = parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }
}