package com.AndroidProject.dailyTracking.DBLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import com.AndroidProject.dailyTracking.entities.Location;
import com.AndroidProject.dailyTracking.entities.Photo;
import com.AndroidProject.dailyTracking.entities.Transaction;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class that handles all CRUD operations of the database.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

	/* Declare all local Variables */
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "DailyTracking2.db";
	private static final String LOC_TABLE_NAME = "LOCATION";
	private static final String TIME_TABLE_NAME = "TIME";
	private static final String TRANSACTION_TABLE_NAME = "BILLS";
	private static final String PICTURE_TABLE_NAME = "PICTURES";
	private final DateFormat timestampFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String locQuery = "CREATE TABLE IF NOT EXISTS " + LOC_TABLE_NAME + " (TRACK_ID INT,LAT DOUBLE, LON DOUBLE );";
		String timeQuery = "CREATE TABLE IF NOT EXISTS " + TIME_TABLE_NAME + " (TRACK_ID INT,TIME_STAMP VARCHAR );";
		String transQuery = "CREATE TABLE IF NOT EXISTS " + TRANSACTION_TABLE_NAME + 
				" (TRACK_ID INT, AMOUNT DOUBLE, STORE VARCHAR, CATEGORY VARCHAR);";
		String picQuery = "CREATE TABLE IF NOT EXISTS " + PICTURE_TABLE_NAME + " (TRACK_ID INT, PATH VARCHAR);";
		db.execSQL(locQuery);
		db.execSQL(timeQuery);
		db.execSQL(transQuery);
		db.execSQL(picQuery);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + LOC_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TIME_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PICTURE_TABLE_NAME);

		// Create tables again
		onCreate(db);
	}
	
	/**
	 * Adds a transaction to the database. Also adds an associated location
	 * if it exists.
	 * 
	 * @param transaction the transaction
	 */
	public void addTransaction(Transaction transaction) {
		
		// get the current key
		int curID = this.getCurID();
		
		/* Insert Query for TRANSACTION Table */		        
		String insertTransQuery = "INSERT INTO " + TRANSACTION_TABLE_NAME +
				" (TRACK_ID,AMOUNT,STORE,CATEGORY) VALUES (" + "'" +
				curID + "','" +
				transaction.getAmount() + "','" +
				transaction.getStore() + "','" + 
				transaction.getCategory() + "');";
		
		/* Insert Query for TIME Table */
		String insertTimeQuery = "INSERT INTO " + TIME_TABLE_NAME +
				" (TRACK_ID,TIME_STAMP) VALUES " +
				"( " + "'" + curID + "','" +
				this.getCurTime() + "');";
		
		String insertLocQuery = null;
		Location location = transaction.getLocation();
		if (location != null) {
			/* Insert Query for LOCATION Table */	        
			insertLocQuery = "INSERT INTO " + LOC_TABLE_NAME +
					" (TRACK_ID,LAT,LON) VALUES (" + "'" +
					curID + "','" +
					location.getLat() + "','" +
					location.getLon() + "');";
		}
		
		SQLiteDatabase db = this.getWritableDatabase();
		/* Execute the Queries */
		db.execSQL(insertTransQuery);
		db.execSQL(insertTimeQuery);
		if (location != null) {
			db.execSQL(insertLocQuery);
		}
		
	}
	
	/**
	 * Gets the transaction history from the database. If an associated location
	 * exists for a transaction, it is contained within the corresponding
	 * transaction object...otherwise, this location object is null.
	 * 
	 * @return the list of transaction.
	 */
	public List<Transaction> getTransactions() {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		// Select All Query
		String selectQuery = "SELECT * FROM BILLS,TIME WHERE BILLS.TRACK_ID = TIME.TRACK_ID";

		/* Run the Select query and get it to Cursor */
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to array
		if (cursor.moveToFirst()) {
			// loop through all the transactions 
			do {
				int id = cursor.getInt(cursor.getColumnIndex("TRACK_ID"));
				double amount = cursor.getDouble(cursor.getColumnIndex("AMOUNT"));
				String store = cursor.getString(cursor.getColumnIndex("STORE"));
				String category = cursor.getString(cursor.getColumnIndex("CATEGORY"));
				String time = cursor.getString(cursor.getColumnIndex("TIME_STAMP"));
				// add new transaction to the list
				transactions.add(new Transaction(id, amount, store, category, null, time));
			} while (cursor.moveToNext());
		}
		
		// query to get associated locations
		selectQuery = "SELECT * FROM BILLS,LOCATION WHERE BILLS.TRACK_ID = LOCATION.TRACK_ID";
		cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) {
			// loop through the locations
			do {
				int id = cursor.getInt(cursor.getColumnIndex("TRACK_ID"));
				double lat = cursor.getDouble(cursor.getColumnIndex("LAT"));
				double lon = cursor.getDouble(cursor.getColumnIndex("LON"));
				// loop through transactions
				for (Transaction trans : transactions) {
					// if the location and transaction match, add the location
					// to the transaction in the list
					if (trans.getID() == id) {
						trans.setLocation(new Location(id, lat, lon, trans.getTime()));
					}
				}
			} while (cursor.moveToNext());
		}
		
		return transactions;
		
	}
	
	/**
	 * Clears all transaction history from the database.
	 */
	public void clearTransactionHistory() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE_NAME);
		String transQuery = "CREATE TABLE IF NOT EXISTS " + TRANSACTION_TABLE_NAME + 
				" (TRACK_ID VARCHAR, AMOUNT DOUBLE, STORE VARCHAR, CATEGORY VARCHAR);";
		db.execSQL(transQuery);
	}
	
	/**
	 * Adds a photo to the database. Also adds an associated location
	 * if it exists.
	 * 
	 * @param photo the photo
	 */
	public void addPhoto(Photo photo) {
		
		// get the current key
		int curID = this.getCurID();
		
		/* Insert Query for PHOTO Table */		        
		String insertPhotoQuery = "INSERT INTO " + PICTURE_TABLE_NAME +
				" (TRACK_ID,PATH) VALUES (" + "'" +
				curID + "','" + photo.getPath() + "');";
		
		/* Insert Query for TIME Table */
		String insertTimeQuery = "INSERT INTO " + TIME_TABLE_NAME +
				" (TRACK_ID,TIME_STAMP) VALUES " +
				"( " + "'" + curID + "','" +
				this.getCurTime() + "');";
		
		String insertLocQuery = null;
		Location location = photo.getLocation();
		if (location != null) {
			/* Insert Query for LOCATION Table */	        
			insertLocQuery = "INSERT INTO " + LOC_TABLE_NAME +
					" (TRACK_ID,LAT,LON) VALUES (" + "'" +
					curID + "','" +
					location.getLat() + "','" +
					location.getLon() + "');";
		}
		
		SQLiteDatabase db = this.getWritableDatabase();
		/* Execute the Queries */
		db.execSQL(insertPhotoQuery);
		db.execSQL(insertTimeQuery);
		if (location != null) {
			db.execSQL(insertLocQuery);
		}
		
	}
	
	/**
	 * Gets the photo history from the database. If an associated location
	 * exists for a photo, it is contained within the corresponding
	 * photo object...otherwise, this location object is null.
	 * 
	 * @return the list of photos
	 */
	public List<Photo> getPhotos() {
		
		List<Photo> photos = new ArrayList<Photo>();
		// Select All Query
		String selectQuery = "SELECT * FROM PICTURES,TIME WHERE PICTURES.TRACK_ID = TIME.TRACK_ID";

		/* Run the Select query and get it to Cursor */
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to array
		if (cursor.moveToFirst()) {
			// Loop through all the photos
			do {
				int id = cursor.getInt(cursor.getColumnIndex("TRACK_ID"));
				String path = cursor.getString(cursor.getColumnIndex("PATH"));
				String time = cursor.getString(cursor.getColumnIndex("TIME_STAMP"));
				// add new transaction to the list
				photos.add(new Photo(id, path, null, time));
			} while (cursor.moveToNext());
		}
		
		// query to get associated locations
		selectQuery = "SELECT * FROM PICTURES,LOCATION WHERE PICTURES.TRACK_ID = LOCATION.TRACK_ID";
		cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) {
			do {
				// loop through the locations
				int id = cursor.getInt(cursor.getColumnIndex("TRACK_ID"));
				double lat = cursor.getDouble(cursor.getColumnIndex("LAT"));
				double lon = cursor.getDouble(cursor.getColumnIndex("LON"));
				// loop through the photos
				for (Photo photo : photos) {
					if (photo.getId() == id) {
						// if the location and photo match, add the location
						// to the photo in the list
						photo.setLocation(new Location(id, lat, lon, photo.getTime()));
					}
				}
			} while (cursor.moveToNext());
		}
		
		return photos;
		
	}
	
	/**
	 * Deletes all photo history from the database.
	 */
	public void clearPhotoHistory() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + PICTURE_TABLE_NAME);
		String picQuery = "CREATE TABLE IF NOT EXISTS " + PICTURE_TABLE_NAME + " (TRACK_ID INT, PATH VARCHAR);";
		db.execSQL(picQuery);
	}
	
	/**
	 * Adds a location to the database.
	 * 
	 * @param location the location
	 */
	public void addLocation(Location location) {
		
		// get the current key
		int curID = this.getCurID();
		
		/* Insert Query for LOCATION Table */		        
		String insertLocQuery = "INSERT INTO " + LOC_TABLE_NAME +
				" (TRACK_ID,LAT,LON) VALUES (" + "'" +
				curID + "','" +
				location.getLat() + "','" +
				location.getLon() + "');";

		/* Insert Query for TIME Table */
		String insertTimeQuery = "INSERT INTO " + TIME_TABLE_NAME +
				" (TRACK_ID,TIME_STAMP) VALUES " +
				"( " + "'" + curID + "','" +
				this.getCurTime() + "');";
		
		SQLiteDatabase db = this.getWritableDatabase();
		/* Execute the Queries */
		db.execSQL(insertLocQuery);
		db.execSQL(insertTimeQuery);
	}

	/**
	 * Gets a list of the user's location history from the database.
	 * 
	 * @return the list of locations
	 */
	public List<Location> getAllLocations() {
		
		List<Location> locations = new ArrayList<Location>();
		// Select All Query
		String selectQuery = "SELECT * FROM LOCATION,TIME WHERE LOCATION.TRACK_ID = TIME.TRACK_ID";

		/* Run the Select query and get it to Cursor */
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to array
		if (cursor.moveToFirst()) {
			// Loop through all the locations 
			do {
				int id = cursor.getInt(cursor.getColumnIndex("TRACK_ID"));
				double lat = cursor.getDouble(cursor.getColumnIndex("LAT"));
				double lon = cursor.getDouble(cursor.getColumnIndex("LON"));
				String time = cursor.getString(cursor.getColumnIndex("TIME_STAMP"));
				locations.add(new Location(id, lat, lon, time));
			} while (cursor.moveToNext());
		}
		return locations;
		
	}
	
	/**
	 * Deletes all location history from the database.
	 */
	public void clearLocationHistory() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + LOC_TABLE_NAME);
		String locQuery = "CREATE TABLE IF NOT EXISTS " + LOC_TABLE_NAME + " (TRACK_ID INT,LAT DOUBLE, LON DOUBLE );";
		db.execSQL(locQuery);
	}
	
	// gets the current time is a formatted string
	private String getCurTime() {
		
		/* Get the time Component */	
		GregorianCalendar greg = new GregorianCalendar();
		TimeZone tz = greg.getTimeZone();
		int offset = tz.getOffset(System.currentTimeMillis());
		greg.add(Calendar.SECOND, (offset / 1000) * -1);
		return timestampFormat.format(greg.getTime());
		
	}
	
	// gets the current id used as the primary key in all 4 tables
	private int getCurID() {
		
		/* Select Query to get maximum TrackId from TIME Table.
		 * This is also the recent one.
		 */
		String selQuery = "SELECT MAX(TRACK_ID) FROM " + TIME_TABLE_NAME ;
		/* Run the Select query and get it to Cursor */
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selQuery, null);
		if(cursor.moveToFirst()) {
			return cursor.getInt(0) + 1;
		} else {
			return 1;
		}
		
	}
}