package com.locator_app.playground.mapsample;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.locator_app.playground.mapsample.MapsApplication;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GpsService {

    private Context context;
    private LocationManager locationManager;

    public GpsService() {
        context = MapsApplication.getAppContext();
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    }

    public boolean isGpsEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public android.location.Location getGpsLocation() {
        android.location.Location location;

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        return location;
    }
}