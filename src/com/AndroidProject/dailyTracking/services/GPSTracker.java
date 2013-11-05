package com.AndroidProject.dailyTracking.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * A service that allows the system to get the current location of the
 * device.
 */
public class GPSTracker extends Service implements LocationListener {
 
    private final Context mContext;
 
    // flag for GPS status
    boolean typeGPS = false;
 
    // flag for network status
    boolean typeNetwork = false;
 
    // flag for GPS status
    boolean LocationExists = false;
 
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
 
    // Declaring a Location Manager
    protected LocationManager locationManager;
 
    public GPSTracker(Context context) {
        this.mContext = context;
        checkLocation();
    }
    
    public Location checkLocation() 
    {
        try 
        {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
 
            // getting GPS status
            typeGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
 
            // getting network status
            typeNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
             
            if(typeGPS || typeNetwork)
            {
                this.LocationExists = true;
                // First get location from Network Provider
                if (typeNetwork) 
                {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,60000,10, this);
                    if (locationManager != null) 
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) 
                        {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (typeGPS) 
                {
                    if (location == null) 
                    {
                        // The time of locationUpdates is mentioned here 
                    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,60000,10, this);
                        if (locationManager != null) 
                        {
                        	/* Get Last known location */
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) 
                            {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
 
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
 
        return location;
    }
 
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }
 
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
 
        // return latitude
        return latitude;
    }
 
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
 
        // return longitude
        return longitude;
    }
 
    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean LocationExists() {
        return this.LocationExists;
    }
 
    @Override
    public void onLocationChanged(Location location) {
    }
 
    @Override
    public void onProviderDisabled(String provider) {
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}