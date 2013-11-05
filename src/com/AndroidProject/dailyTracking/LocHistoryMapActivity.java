package com.AndroidProject.dailyTracking;

import java.util.List;

import com.AndroidProject.dailyTracking.entities.Location;
import com.AndroidProject.dailyTracking.entities.LocationLogic;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

/**
 * This activity displays the user's location history using Google Maps.
 */
public class LocHistoryMapActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loc_history_map);
		
		// the map
		GoogleMap mMap;
		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		// get the location history
		List<Location> locations = LocationLogic.getLocationHistory(this);
		for (Location loc : locations) {
			// add a marker for each location
			mMap.addMarker(new MarkerOptions()
				.position(new LatLng(loc.getLat(), loc.getLon()))
				.title(loc.getTime()));
		}
		// move the camera to the most recently visited location
		Location lastLoc = locations.get(locations.size() - 1);
		LatLng latLon = new LatLng(lastLoc.getLat(), lastLoc.getLon());
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLon, 10));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loc_history_map, menu);
		return true;
	}

}
