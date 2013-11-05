package com.AndroidProject.dailyTracking.entities;

import java.util.List;

import android.content.Context;

import com.AndroidProject.dailyTracking.DBLayout.DataBaseHandler;

/**
 * Class that provides business logic associated with locations.
 */
public class LocationLogic {
	
	/**
	 * Adds a location to the user's history.
	 * 
	 * @param context the current context
	 * @param location the location
	 */
	public static void addLocation(Context context, Location location) {
		DataBaseHandler dbh = new DataBaseHandler(context);
		dbh.addLocation(location);
		dbh.close();
	}
	
	/**
	 * Gets the user's location history.
	 * 
	 * @param context the current context
	 * @return the list of locations
	 */
	public static List<Location> getLocationHistory(Context context) {
		DataBaseHandler dbh = new DataBaseHandler(context);
		List<Location> locations = dbh.getAllLocations();
		dbh.close();
		return locations;
	}
	
	/**
	 * Deletes all of the user's location history.
	 * 
	 * @param context the current context.
	 */
	public static void deleteLocationHistory(Context context) {
		DataBaseHandler dbh = new DataBaseHandler(context);
		dbh.clearLocationHistory();
		dbh.close();
	}
	
}
