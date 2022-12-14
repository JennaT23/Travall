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

    protected String googlePlacesData;
    protected GoogleMap mMap;
    protected String url;

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

            String placeName = googlePLace.get("name");
            String address = googlePLace.get("formatted_address");
            double lat = Double.parseDouble(Objects.requireNonNull(googlePLace.get("lat")));
            double lng = Double.parseDouble(Objects.requireNonNull(googlePLace.get("lng")));

            LatLng latLng = new LatLng(lat, lng);

            markerOptions.position(latLng);
            markerOptions.title(placeName + " " + address);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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