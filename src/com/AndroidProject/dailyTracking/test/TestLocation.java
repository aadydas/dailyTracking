package com.AndroidProject.dailyTracking.test;

import com.AndroidProject.dailyTracking.entities.Location;

import junit.framework.TestCase;

public class TestLocation extends TestCase {

	public void testConstruct() {
		Location loc = new Location(0, 0.0, 0.0, null);
		assertNotNull(loc);
	}
	
	public void testGetId() {
		Location loc = new Location(5, 0.0, 0.0, null);
		assertEquals(5, loc.getID());
	}
	
	public void testGetLat() {
		Location loc = new Location(5, 10.0, -5.0, null);
		assertEquals(10.0, loc.getLat());
	}
	
	public void testGetLon() {
		Location loc = new Location(5, 10.0, -5.0, null);
		assertEquals(-5.0, loc.getLon());
	}
	
	public void testGetTime() {
		Location loc = new Location(5, 10.0, -5.0, "10-03-2014");
		assertEquals("10-03-2014", loc.getTime());
	}
	
}
