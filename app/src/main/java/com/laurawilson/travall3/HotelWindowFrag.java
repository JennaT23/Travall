package com.laurawilson.travall3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.laurawilson.travall3.databinding.AttractionWindowFragmentBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.laurawilson.travall3.databinding.FoodWindowFragmentBinding;

import java.io.IOException;
import java.util.List;

public class HotelWindowFrag extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private FoodWindowFragmentBinding binding;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 50000;
    double latitude = 42.1292;
    double longitude = 80.0851;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hotel_window_fragment, container, false);

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission is granted
                    if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else //permission is denied
                {
                    Toast.makeText(this.getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng latLng = new LatLng(latitude, longitude);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.addMarker(new MarkerOptions());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.addMarker(new MarkerOptions());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

//    @SuppressLint("NonConstantResourceId")
//    public void onClick(View v) {
//
//        Object[] dataTransfer =  new Object[2];
//        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
//        EditText searchBox = v.findViewById(R.id.SearchBox);
//        String location = searchBox.getText().toString();
//        MarkerOptions mo = new MarkerOptions();
//        Geocoder geocoder = new Geocoder(this.getActivity());
//        Address myAddress;
//
//        if (v.getId() == R.id.SearchButton) {
//                List<Address> addresses = null;
//                if (!location.equals("")) {
//                    try {
//                        addresses = geocoder.getFromLocationName(location, 5);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    for (int i = 0; i < addresses.size(); i++) {
//                        myAddress = addresses.get(i);
//                        LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
//                        mo.position(latLng);
//                        mMap.addMarker(mo);
//                        mMap.moveCamera(CameraUpdateFactory.zoomBy(5));
//                    }
//                }
//            }
//        switch (v.getId()) {
//
//            case R.id.Food:
//                mMap.clear();
//                String food = "restaurant";
//                String url = getUrl(latitude, longitude, food);
//                dataTransfer[0] = mMap;
//                dataTransfer[1] = url;
//                getNearbyPlacesData.execute(dataTransfer);
//                Toast.makeText(FoodWindowFrag.this, "Showing nearby food locations", Toast.LENGTH_LONG).show();
//                break;
//
//            case R.id.Hotel:
//                mMap.clear();
//                String hotel = "lodging";
//                url = getUrl(latitude, longitude, hotel);
//                dataTransfer[0] = mMap;
//                dataTransfer[1] = url;
//                getNearbyPlacesData.execute(dataTransfer);
//                Toast.makeText(FoodWindowFrag.this.getActivity(), "Showing nearby hotels", Toast.LENGTH_LONG).show();
//                break;
//
//            case R.id.Attract:
//                mMap.clear();
//                String attraction = "tourist_attraction";
//                url = getUrl(latitude, longitude, attraction);
//                dataTransfer[0] = mMap;
//                dataTransfer[1] = url;
//                getNearbyPlacesData.execute(dataTransfer);
//                Toast.makeText(FoodWindowFrag.this.getActivity(), "Showing nearby attractions", Toast.LENGTH_LONG).show();
//                break;
//
//            case R.id.Transport:
//                mMap.clear();
//                String transport = "subway_station";
//                url = getUrl(latitude, longitude, transport);
//                dataTransfer[0] = mMap;
//                dataTransfer[1] = url;
//                getNearbyPlacesData.execute(dataTransfer);
//                Toast.makeText(FoodWindowFrag.this.getActivity(), "Showing nearby transportation", Toast.LENGTH_LONG).show();
//                break;
//        }
//    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location" + latitude + "," + longitude);
        System.out.println("hello " + latitude + " " + longitude);
        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type=" + nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=" + "AIzaSyChL7myQenYOoaA5uVdSEqC-Ko_614uGUc");

        return googlePlaceUrl.toString();
    }

    protected synchronized void buildGoogleApiClient()
    {
        client = new GoogleApiClient.Builder(this.getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lastLocation = location;

        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }

        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new com.google.android.gms.location.LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                .setMinUpdateIntervalMillis(500)
                .setMaxUpdateDelayMillis(1000)
                .build();

        if(ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    public boolean checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}