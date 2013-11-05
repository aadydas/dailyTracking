package com.AndroidProject.dailyTracking.test;

import com.AndroidProject.dailyTracking.entities.Location;
import com.AndroidProject.dailyTracking.entities.Transaction;

import junit.framework.TestCase;

public class TestTransaction extends TestCase {
	
	public void testContruct() {
		Transaction trans = new Transaction(2, 15.1, "Wawa", "Gas", null, "03-04-2005");
		assertNotNull(trans);
	}
	
	public void testGetId() {
		Transaction trans = new Transaction(2, 15.1, "Wawa", "Gas", null, "03-04-2005");
		assertEquals(2, trans.getID());
	}
	
	public void testGetAmount() {
		Transaction trans = new Transaction(2, 15.1, "Wawa", "Gas", null, "03-04-2005");
		assertEquals(15.1, trans.getAmount());
	}
	
	public void testGetStore() {
		Transaction trans = new Transaction(2, 15.1, "Wawa", "Gas", null, "03-04-2005");
		assertEquals("Wawa", trans.getStore());
	}
	
	public void testGetCategory() {
		Transaction trans = new Transaction(2, 15.1, "Wawa", "Gas", null, "03-04-2005");
		assertEquals("Gas", trans.getCategory());
	}
	
	public void testGetLocation() {
		Location loc = new Location(0, 0.0, 0.0, null);
		Transaction trans = new Transaction(2, 15.1, "Wawa", "Gas", loc, "03-04-2005");
		assertEquals(loc, trans.getLocation());
	}
	
	public void testSetLocation () {
		Location loc1 = new Location(0, 0.0, 0.0, null);
		Transaction trans = new Transaction(2, 15.1, "Wawa", "Gas", loc1, "03-04-2005");
		Location loc2 = new Location(1, 0.0, 0.0, null);
		trans.setLocation(loc2);
		assertEquals(loc2, trans.getLocation());
	}
	
	public void testGetTime() {
		Transaction trans = new Transaction(2, 15.1, "Wawa", "Gas", null, "03-04-2005");
		assertEquals("03-04-2005", trans.getTime());
	}
	
}
