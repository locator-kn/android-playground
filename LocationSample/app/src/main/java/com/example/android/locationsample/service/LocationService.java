package com.example.android.locationsample.service;

import com.example.android.locationsample.model.Location;
import com.example.android.locationsample.util.Server;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Rest(rootUrl = Server.apiUrl, converters = MappingJackson2HttpMessageConverter.class)
public interface LocationService {

    @Get("/locations/latest")
    List<Location> allLocations();

    @Get("/locations/latest/page={page}&elements={elements}")
    List<Location> latestLocations(int page, int elements);

}
