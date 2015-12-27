package com.locator_app.gpssample;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    TextView city;

    @ViewById
    TextView location;

    @Bean
    GpsService gpsService;

    @Click(R.id.requestGps)
    void onClick() {
        if (gpsService.isGpsEnabled()) {
            requestLocation();
        } else {
            Toast.makeText(getApplicationContext(), "enable gps first", Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    void requestLocation() {
        Location currentLocation = gpsService.getGpsLocation();
        String currentCity = gpsService.getCityByLocation(currentLocation);
        showGpsData(currentLocation, currentCity);
    }

    @UiThread
    void showGpsData(Location loc, String currentCity) {
        if (loc != null) {
            String locationAsString = loc.getLatitude() + "/" + loc.getLongitude();
            location.setText(locationAsString);
            city.setText(currentCity);
        } else  {
            Toast.makeText(getApplicationContext(), "location is null", Toast.LENGTH_SHORT).show();
        }
    }
}
