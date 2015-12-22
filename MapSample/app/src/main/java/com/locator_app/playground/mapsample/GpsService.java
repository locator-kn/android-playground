package com.locator_app.playground.mapsample;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SystemService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@EBean(scope = EBean.Scope.Singleton)
public class GpsService implements LocationListener {

    @RootContext
    Context context;

    @SystemService
    LocationManager locationManager;

    Location lastLocation;
    String lastCity = "";

    @AfterInject
    void init() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  5000, 10, this);
    }

    public boolean isGpsEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public Location getGpsLocation() {
        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastLocation == null) {
            lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return lastLocation;
    }

    public String getCityByLocation(Location location) {
        if (location == null)
            return lastCity;
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (!addresses.isEmpty()) {
                lastCity = addresses.get(0).getLocality();
                return lastCity;
            }
        } catch (IOException ex) {
        }
        return lastCity;
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        Toast.makeText(context, "on location changed: " + location.toString(), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }
}
