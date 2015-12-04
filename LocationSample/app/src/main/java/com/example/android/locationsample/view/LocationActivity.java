package com.example.android.locationsample.view;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.android.locationsample.R;
import com.example.android.locationsample.controller.LocationController;
import com.example.android.locationsample.model.Location;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.UiThread;
import org.springframework.web.client.RestClientException;

import java.util.List;

@EActivity(R.layout.activity_location)
public class LocationActivity extends AppCompatActivity {

    @ViewById
    ListView listView;

    @ViewById
    Button loadLocations;

    @ViewById
    ProgressBar progressBar;

    @Bean
    LocationController locationController;

    @Bean
    Adapter adapter;

    @AfterViews
    void bindAdapter() {
        listView.setAdapter(adapter);
    }

    @Click
    void loadLocationsClicked() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.clear();
        loadLocations();
    }

    @Background
    void loadLocations() {
        try {
            List<Location> locations = locationController.getAllLocations();
            updateLocations(locations);
        } catch (RestClientException ex) {
            handleLoadLocationsError();
        }
    }

    @UiThread
    void updateLocations(List<Location> locations) {
        adapter.setViewables(locations);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "loaded " + locations.size() + " locations", Toast.LENGTH_LONG).show();
    }

    @UiThread
    void handleLoadLocationsError() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "uuups, something went wrong :-(", Toast.LENGTH_LONG).show();
    }
}
