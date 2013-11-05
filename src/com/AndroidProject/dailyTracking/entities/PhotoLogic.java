package com.AndroidProject.dailyTracking.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.AndroidProject.dailyTracking.TrackOptionsActivity;
import com.AndroidProject.dailyTracking.DBLayout.DataBaseHandler;
import com.AndroidProject.dailyTracking.services.GPSTracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * Class that provides business logic associated with photos.
 */
public class PhotoLogic {
	
	// current context
	private static Context theThis;
	// folder and file name prefix of all photos
    private static String NameOfFolder = "/dailyTrakingPics"; 
    private static String NameOfFile   = "dTrackImage"; 
	
    /**
     * Gets the user's history of all photos taken.
     * 
     * @param context the current context
     * @return
     */
	public static List<Photo> getPhotoHistory(Context context) {
		DataBaseHandler dbh = new DataBaseHandler(context);
		List<Photo> photos = dbh.getPhotos();
		dbh.close();
		return photos;
	}
    
	/**
	 * Adds a photo to the user's history by both writing it to disk and saving a
	 * reference to it in the database.
	 * 
	 * @param context the current context
	 * @param ImageToSave the image in bmp form
	 * @return the file to which the photo was saved
	 */
	public static File SaveImageToDisk(Context context, Bitmap ImageToSave){ 
		
		theThis = context;
		
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        String CurrentDateAndTime= getCurrentDateAndTime(); 
        File dir = new File(file_path); 
        
        // make the directory
        if(!dir.exists()){ 
            dir.mkdirs(); 
        } 
        
        // get file for photo
        File file = new File(dir, NameOfFile +CurrentDateAndTime+ ".jpg"); 
        try {
        	
            FileOutputStream fOut = new FileOutputStream(file);  
            // compress
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush(); 
            fOut.close();
            // make sure the file was created
            MakeSureFileWasCreatedThenMakeAvabile(file);
            // display success to the user
            AbleToSave();
            
            // store the file path and possible associated location in the
            // database
            DataBaseHandler dbh = new DataBaseHandler(context);
            Location location = null;
        	if (TrackOptionsActivity.pictureEnabled) {
        		GPSTracker gps = new GPSTracker(context);
        		if (gps.LocationExists()) {
        			location = new Location(0, gps.getLatitude(), gps.getLongitude(), null);
        		}
        	}
            dbh.addPhoto(new Photo(0, file.getAbsolutePath(), location, CurrentDateAndTime));
            dbh.close();
            
        }  // display error to the user
        catch (FileNotFoundException e) {UnableToSave();} 
        catch (IOException e){UnableToSave();}
		return file;
           
    }
	
	/**
	 * Deletes the user's entire photo history, which includes photos on disk
	 * and references in the database.
	 * 
	 * @param context the current context
	 */
	public static void deletePhotos(Context context) {
		DataBaseHandler dbh = new DataBaseHandler(context);
		List<Photo> photos = dbh.getPhotos();
		for (Photo photo : photos) {
			File file = new File(photo.getPath());
			file.delete();
		}
		dbh.clearPhotoHistory();
		dbh.close();
	}
	
	private static void MakeSureFileWasCreatedThenMakeAvabile(File file) { 
        MediaScannerConnection.scanFile(theThis, 
                new String[] { file.toString() }, null, 
                new MediaScannerConnection.OnScanCompletedListener() { 
            public void onScanCompleted(String path, Uri uri) { 
                Log.e("ExternalStorage", "Scanned " + path + ":"); 
                Log.e("ExternalStorage", "-> uri=" + uri); 
                 
            } 
        });
          
    } 
    
    private static String getCurrentDateAndTime() { 
        Calendar c = Calendar.getInstance(); 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"); 
        String formattedDate = df.format(c.getTime()); 
        return formattedDate; 
    } 
    
    private static void UnableToSave() { 
	    Toast.makeText(theThis, "We're sorry, but the picture cannot be saved" +
	    		"due to an internal error.Please try again later.", Toast.LENGTH_LONG).show();     
	} 
	  
	private static void AbleToSave() { 
	    Toast.makeText(theThis, "Picture saved.", Toast.LENGTH_LONG).show();       
	} 
	
}
