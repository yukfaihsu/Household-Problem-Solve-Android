// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.views;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.project_group11.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.project_group11.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng currentLocation;
    private final String TAG = this.getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (this.getIntent() != null){
            this.currentLocation = new LatLng(this.getIntent().getDoubleExtra("EXTRA_LAT", 0.0),
                    this.getIntent().getDoubleExtra("EXTRA_LNG", 0.0));
            Log.e(TAG, "onCreate: currentLocation : Lat: " + currentLocation.latitude  + " Lng : " + currentLocation.longitude);
        }
    }

    // show the location in the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(this.currentLocation).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(this.currentLocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(this.currentLocation, 15.0f));

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
    }
}