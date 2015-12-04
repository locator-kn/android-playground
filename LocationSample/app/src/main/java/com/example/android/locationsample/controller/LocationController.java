package com.example.android.locationsample.controller;


import com.example.android.locationsample.model.Location;
import com.example.android.locationsample.service.LocationService;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

@EBean
public class LocationController {

    @RestService
    LocationService locationService;

    public List<Location> getAllLocations() {
        List<Location> locations = locationService.allLocations();
        return locations;
    }

    List<Location> getLastLocations(int count) {
        List<Location> locations = locationService.latestLocations(0, count);
        return locations;
    }
}
