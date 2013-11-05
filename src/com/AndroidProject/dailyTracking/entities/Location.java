package com.AndroidProject.dailyTracking.entities;

/**
 * Class that represents a user's visit to a geographic location.
 */
public class Location {
	
	private int id;
	private double lat;
	private double lon;
	private String time;
	
	public Location(int id, double lat, double lon, String time) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.time = time;
	}
	
	/**
	 * Gets the tracking id of this location.
	 * 
	 * @return the id
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Gets the latitude of this location.
	 * 
	 * @return the latitude
	 */
	public double getLat() {
		return this.lat;
	}
	
	/**
	 * Gets the longitude of this location.
	 * 
	 * @return the longitude
	 */
	public double getLon() {
		return this.lon;
	}
	
	/**
	 * Gets the time at which this location was visited.
	 * 
	 * @return the time
	 */
	public String getTime() {
		return this.time;
	}
	
}
