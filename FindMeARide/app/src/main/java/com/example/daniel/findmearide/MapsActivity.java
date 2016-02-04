package com.example.daniel.findmearide;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.daniel.findmearide.R.id;
import com.example.daniel.findmearide.R.layout;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Console;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnMapClickListener, RoutingListener {

    private GoogleMap mMap;
    LatLng myLocationLatLng;
    GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) this.getFragmentManager()
                .findFragmentById(id.map);
        mapFragment.getMapAsync(this);

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
        this.mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        this.mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        this.mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //Set up listener
        this.mMap.setOnMapClickListener(this);

        //Following code for getting user location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        Location myLocation = locationManager.getLastKnownLocation(provider);
        myLocationLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(myLocationLatLng).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocationLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

    }

    @Override
    public void onMapClick(LatLng point) {
        //Add marker where user clicked
        this.mMap.addMarker(new MarkerOptions().position(point));
        String directionsStr = getDirectionsURL(myLocationLatLng, point);
        Log.d("URL", directionsStr);


        //Routing routing = new Routing.Builder().travelMode(AbstractRouting.TravelMode.DRIVING).withListener(this).waypoints(myLocationLatLng, point).key("AIzaSyDTFYpJ0EarcLgyJUxeGfrmdPnnumMrJco").build();
        //routing.execute();

        GetDirections getDirections = new GetDirections();
        getDirections.execute(directionsStr);

        //new GetDirections().execute(directionsStr);

//        Intent mapCall = new Intent(Intent.ACTION_VIEW, Uri.parse(directionsStr));
//        this.startActivity(mapCall);
    }

    public String getDirectionsURL(LatLng origin, LatLng dest) {
        String strOrigin = "origin=" + origin.latitude + "," + origin.longitude;
        String strDest = "destination=" + dest.latitude + "," + dest.longitude;

        String url = "https://maps.googleapis.com/maps/api/directions/json?" + strOrigin + "&" + strDest + "&key=AIzaSyC4BW2LTPGyTQUqV8Z47JQaFaBi-xn3c_w";
        return url;
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {
        int sdfgsdfg = 0;
    }

    @Override
    public void onRoutingCancelled() {

    }
}
