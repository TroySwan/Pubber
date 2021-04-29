package com.example.pubber;

/*
 * Map.java 1.0 2019/05/03
 *
 * Copyright (c) 2019 Aberystwyth University.
 * All rights reserved.
 *
 */

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;
/**
 * Extends OnMapReadyCallback, uses the Google maps API and displays a map with a marker at a pub.
 * @Author Troy Swan
 * @Version 1.0
 */
public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String pubName;
    private double pubLat;
    private double pubLong;


    /**
     * Reads in the pub and displays the map
     * @param savedInstanceState the current instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_window);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        pubName = (String) getIntent().getSerializableExtra("pubName");
        pubLat = (double) getIntent().getSerializableExtra("pubLat");
        pubLong = (double) getIntent().getSerializableExtra("pubLong");
    }

    /**
     * Displays a map marker at the pubs location
     * @param googleMap the googleMap instance.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location = new LatLng(pubLat, pubLong);
        mMap.addMarker(new MarkerOptions().position(location).title(pubName));
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
    }
}
