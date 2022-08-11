// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.models;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class LocationHelper {

    private final String TAG = this.getClass().getCanonicalName();
    private static final LocationHelper instance = new LocationHelper();

    public static LocationHelper getInstance(){
        return instance;
    }

    public LatLng performGeocoding(Context context, String address){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try{
            List<Address> locationList = geocoder.getFromLocationName(address, 1);
            if(locationList.size() > 0){
                LatLng obtainedCoordinates = new LatLng(locationList.get(0).getLatitude(), locationList.get(0).getLongitude());
                Log.d(TAG, "performGeocoding: Obtained Coordinates: " + obtainedCoordinates.toString());
                return obtainedCoordinates;
            }
        }catch (Exception ex){
            Log.e(TAG, "performGeocoding: Couldn't get the coordinates, " + ex.getLocalizedMessage());
        }
        return null;
    }
}
