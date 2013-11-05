package com.AndroidProject.dailyTracking.services;

import com.AndroidProject.dailyTracking.entities.Location;
import com.AndroidProject.dailyTracking.entities.LocationLogic;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * A service that passively updates the user's location history
 * at regular time intervals.
 */
public class LocationService extends Service {
	
	public void onCreate() {
		super.onCreate();
	}

	/* OnStartCommand called every time the service is called */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		this.addCurLocation();
		return START_STICKY;
	}

	/* Stop Service to kill the running service */
	@Override
	public boolean stopService(Intent intent) {
		// TODO Auto-generated method stub

		return super.stopService(intent);

	}
	
	/* Insert into data base of tables LOCATION and TIME,
	 * values generated from GPS Tracker.
	 */
	private void addCurLocation() {
		
		// create class object
		GPSTracker gps = new GPSTracker(LocationService.this);
		
		// check if GPS enabled
		if(gps.LocationExists()) {
			/* Get the latitude and longitude */
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			Location newLoc = new Location(0, latitude, longitude, null);
			LocationLogic.addLocation(LocationService.this, newLoc);
		}
		
	}

	@Override
	public IBinder onBind(Intent arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
