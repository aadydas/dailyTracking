package com.AndroidProject.dailyTracking.entities;

/**
 * A class that represents a photo, along with the associated time when
 * it was taken and place where it was taken.
 */
public class Photo {
	
	private int id;
	private String path;
	private String time;
	private Location location;
	
	public Photo(int id, String path, Location location, String time) {
		this.id = id;
		this.path = path;
		this.location = location;
		this.time = time;
	}
	
	/**
	 * Gets the tracking id of this photo.
	 * 
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Gets the absolute file path where this photo is located on disk.
	 * 
	 * @return the file path
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Gets the time when this photo was taken.
	 * 
	 * @return the time
	 */
	public String getTime() {
		return this.time;
	}
	
	/**
	 * Gets the location where this photo was taken.
	 * 
	 * @return the location
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * Sets the location where this photo was taken.
	 * 
	 * @param location the location.
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
}
