package com.locator_app.playground.mapsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.locator_app.playground.mapsample.BitmapHelper.getRoundBitmap;

@EActivity(R.layout.activity_maps)
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final static String LOGTAG = "Development";

    private GoogleMap googleMap;

    @Bean
    GpsService gpsService;

    @AfterViews
    void initMapFragment() {
        Log.d(LOGTAG, "initMapFragment: called");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        System.gc();
        mapFragment.getMap().clear();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        Log.d(LOGTAG, "onMapReady: called");
        googleMap = gMap;

        // Add a marker in Sydney and move the camera
        Location location = gpsService.getGpsLocation();

        LatLng locationPos = new LatLng(location.getLatitude(), location.getLongitude());
        //mMap.addMarker(new MarkerOptions().position(locationPos));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPos, 15));
        //drawLocations();
        addHeatMap();
    }

    @Background
    public void drawLocations() {
        Log.d(LOGTAG, "drawLocations: called");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.location);
        bitmap = getRoundBitmap(bitmap, 100);

        Location location = gpsService.getGpsLocation();
        LatLng locationPos = new LatLng(location.getLatitude()  + 0.002,
                                        location.getLongitude() + 0.002);

        drawLocation(locationPos, bitmap);
    }

    @UiThread
    public void drawLocation(LatLng latLong, Bitmap bitmap) {
        Log.d(LOGTAG, "drawLocation: called");
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .anchor(0.5f, 0.5f)
                .position(latLong));
    }

    private HeatmapTileProvider heatmapTileProvider;
    private TileOverlay tileOverlay;
    private List<WeightedLatLng> heatPoints;

    @Background
    public void addHeatMap() {
        heatPoints = new LinkedList<>();
        for (int i = 0; i < 10; ++i) {
            heatPoints.add(getRandomLatLng());
        }

        int[] colors = {
                Color.rgb(0, 255, 0),
                Color.rgb(32, 223, 0),
                Color.rgb(64, 191, 0),
                Color.rgb(96, 159, 0),
                Color.rgb(128, 127, 0),
                Color.rgb(164, 91, 0),
                Color.rgb(192, 63, 0),
                Color.rgb(225, 31, 0),
                Color.rgb(255, 0, 0)
        };

        // with automatic distributed startPoints
//        float[] startPoints = new float[colors.length];
//        for (int i = 0; i < colors.length; ++i) {
//            startPoints[i] = i * ( 1 / ((float) colors.length * 2)) + (float) 0.5;
//        }

        float[] startPoints = {
                (float) 0.1,
                (float) 0.3,
                (float) 0.4,
                (float) 0.5,
                (float) 0.6,
                (float) 0.7,
                (float) 0.8,
                (float) 0.9,
                (float) 1
        };

        Gradient gradient = new Gradient(colors, startPoints);

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        heatmapTileProvider = new HeatmapTileProvider.Builder()
                .weightedData(heatPoints)
                .radius(30)
                .opacity(0.3)
                .gradient(gradient)
                .build();

        drawHeatMap(heatmapTileProvider);
    }

    @Click(R.id.addHeatPointButton)
    public void onClick() {
        // Only for testing. heat points should not be added individually for memory reasons
        heatPoints.add(getRandomLatLng());
        heatmapTileProvider.setWeightedData(heatPoints);
        tileOverlay.clearTileCache();
        System.gc();
    }

    @UiThread
    public void drawHeatMap(HeatmapTileProvider mProvider) {
        System.gc();
        tileOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    public WeightedLatLng getRandomLatLng() {
        Location location = gpsService.getGpsLocation();

        double latMin = location.getLatitude() - 0.01;
        double latMax = location.getLatitude() + 0.01;

        double lngMin = location.getLongitude() - 0.01;
        double lngMax = location.getLongitude() + 0.01;

        Random r = new Random();
        double lat = latMin + (latMax - latMin) * r.nextDouble();
        double lng = lngMin + (lngMax - lngMin) * r.nextDouble();
        
        double intensity = r.nextDouble();

        return new WeightedLatLng(new LatLng(lat, lng), intensity);
    }
    @Override
    public void onStop() {
        Log.d(LOGTAG, "onStop: called");
        super.onStop();
        heatPoints.clear();
        googleMap.clear();
    }
}
