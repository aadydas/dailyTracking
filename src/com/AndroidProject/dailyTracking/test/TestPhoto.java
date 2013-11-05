package com.AndroidProject.dailyTracking.test;

import com.AndroidProject.dailyTracking.entities.Location;
import com.AndroidProject.dailyTracking.entities.Photo;

import junit.framework.TestCase;

public class TestPhoto extends TestCase {
	
	public void testContruct() {
		Photo photo = new Photo(1, "file/filename", null, "03-04-2005");
		assertNotNull(photo);
	}
	
	public void testGetId() {
		Photo photo = new Photo(1, "file/filename", null, "03-04-2005");
		assertEquals(1, photo.getId());
	}
	
	public void testGetPath() {
		Photo photo = new Photo(1, "file/filename", null, "03-04-2005");
		assertEquals("file/filename", photo.getPath());
	}
	
	public void testGetLocation() {
		Location loc = new Location(0, 0.0, 0.0, null);
		Photo photo = new Photo(1, "file/filename", loc, "03-04-2005");
		assertEquals(loc, photo.getLocation());
	}
	
	public void testSetLocation () {
		Location loc1 = new Location(0, 0.0, 0.0, null);
		Photo photo = new Photo(1, "file/filename", loc1, "03-04-2005");
		Location loc2 = new Location(1, 0.0, 0.0, null);
		photo.setLocation(loc2);
		assertEquals(loc2, photo.getLocation());
	}
	
	public void testGetTime() {
		Photo photo = new Photo(1, "file/filename", null, "03-04-2005");
		assertEquals("03-04-2005", photo.getTime());
	}
	
}

