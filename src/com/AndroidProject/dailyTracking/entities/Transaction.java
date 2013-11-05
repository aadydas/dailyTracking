package com.AndroidProject.dailyTracking.entities;

/**
 * A class that represents a transactions, along with the associated time when
 * it took place, the amount it was for, the store where it happened, the
 * category of item purchased, and the location (lat/lon) where the transaction
 * happened.
 */
public class Transaction {
	
	private int id;
	private double amount;
	private String store;
	private String category;
	private Location location;
	private String time;
	
	public Transaction(int id, double amount, String store, String category, Location location, String time) {
		this.id = id;
		this.amount = amount;
		this.store = store;
		this.category = category;
		this.location = location;
		this.time = time;
	}
	
	/**
	 * Gets the tracking id of this transaction.
	 * 
	 * @return the id
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Gets the monetary amount of this transaction.
	 * 
	 * @return the amount
	 */
	public double getAmount() {
		return this.amount;
	}
	
	/**
	 * Gets the store where the transaction happened.
	 * 
	 * @return the store
	 */
	public String getStore() {
		return this.store;
	}
	
	/**
	 * Gets the category of the purchase.
	 * 
	 * @return the category
	 */
	public String getCategory() {
		return this.category;
	}
	
	/**
	 * Gets the location where the transaction happened.
	 * 
	 * @return the location
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * Sets the location where the transaction happened.
	 * 
	 * @param the location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * Gets the time of the transaction.
	 * 
	 * @return the time
	 */
	public String getTime() {
		return this.time;
	}
	
}
