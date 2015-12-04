package com.example.android.locationsample.model;

import com.example.android.locationsample.view.Viewable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements Viewable {

    @JsonProperty("title")
    public String title;

    @JsonProperty("city")
    City city;

    @JsonProperty("geotag")
    GeoTag geoTag;

    @JsonProperty("images")
    Image image;

    @Override
    public String text() {
        return title;
    }

    @Override
    public String description() {
        return city.title;
    }

    @Override
    public String image() {
        return image.urlMobileThumb();
    }
}
