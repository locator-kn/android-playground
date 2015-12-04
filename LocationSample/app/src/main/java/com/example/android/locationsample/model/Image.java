package com.example.android.locationsample.model;

import com.example.android.locationsample.util.Server;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {

    @JsonProperty("picture")
    String picture;

    public String urlMax() {
        return urlForImage("max");
    }

    public String urlMid() {
        return urlForImage("mid");
    }

    public String urlSmall() {
        return urlForImage("small");
    }

    public String urlUser() {
        return urlForImage("user");
    }

    public String urlMobile() {
        return urlForImage("mobile");
    }

    public String urlMobileThumb() {
        return urlForImage("mobileThumb");
    }

    public String urlUserThumb() {
        return urlForImage("userThumb");
    }

    private String urlForImage(String size) {
        return Server.url + picture + "?size=" + size;
    }
}
