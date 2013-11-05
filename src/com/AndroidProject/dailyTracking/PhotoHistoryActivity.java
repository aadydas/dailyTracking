package com.AndroidProject.dailyTracking;

import java.util.List;

import com.AndroidProject.dailyTracking.entities.Location;
import com.AndroidProject.dailyTracking.entities.Photo;
import com.AndroidProject.dailyTracking.entities.PhotoLogic;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A class that displays the user's photo history as a list of photo
 * thumbnails and info about each photo.
 */
public class PhotoHistoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_history);
		
		// get photo history
		List<Photo> photos = PhotoLogic.getPhotoHistory(this);
		
		/* List View which displays all the photos */
		ListView listView = (ListView) findViewById(R.id.listView1);
		
		// custom adapter for our photo list
		PhotoAdapter adapter = new PhotoAdapter(this, photos);
		
		listView.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_history, menu);
		return true;
	}
	
	// custom adapter for the photo list
	private class PhotoAdapter extends BaseAdapter {
		
		private Activity activity;
	    private List<Photo> photos;
	    private LayoutInflater inflater;
	 
	    public PhotoAdapter(Activity activity, List<Photo> photos) {
	    	this.activity = activity;
	        this.photos = photos;
	        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	 
	    public int getCount() {
	        return this.photos.size();
	    }
	 
	    public Object getItem(int position) {
	        return position;
	    }
	 
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    public View getView(int position, View convertView, ViewGroup parent) {
	        
	    	View view = convertView;
	        if(convertView == null)
	            view = this.inflater.inflate(R.layout.photo_row, null);
	 
	        // get time, location, and thumbnail
	        TextView tvTime = (TextView)view.findViewById(R.id.dateTime);
	        TextView tvLoc = (TextView)view.findViewById(R.id.location);
	        ImageView ivThumb =(ImageView)view.findViewById(R.id.list_image);
	        
	        // get the current photo
	        Photo photo = this.photos.get(position);
	 
	        // display the time
	        tvTime.setText("Date/Time of Photo: " + photo.getTime());
	        if (photo.getLocation() != null) {
	        	// if the location is available, display a link to view it on a map
	        	tvLoc.setText("Click for Location Information");
	        	tvLoc.setOnClickListener(new LocClickListener(this.activity, photo.getLocation()));
	        } else {
	        	tvLoc.setText("");
	        	tvLoc.setOnClickListener(null);
	        }
	        // display thumbnail and make it clickable to view the full size image
	        ivThumb.setImageBitmap(BitmapFactory.decodeFile(photo.getPath()));
	        ivThumb.setOnClickListener(new ThumbClickListener(this.activity, photo.getPath()));
	        
	        return view;
	    }
	    
	    // custom click listener for the image thumbnail
	    private class ThumbClickListener implements OnClickListener {

	    	// the file path to the photo
	    	private String path;
	    	private Activity activity;
	    	
	    	public ThumbClickListener(Activity activity, String path) {
	    		this.path = path;
	    		this.activity = activity;
	    	}
	    	
			@Override
			public void onClick(View arg0) {
				// pass the file path to the photo display activity as an extra
				Intent intent = new Intent(activity, PhotoDisplayActivity.class);
				intent.putExtra("com.AndroidProject.dailyTracking.PATH", this.path);
				startActivity(intent);
			}
	    	
	    }
	    
	    // custom click listener for the location link
	    private class LocClickListener implements OnClickListener {
	    	
	    	// the location
	    	private Location location;
	    	private Activity activity;
	    	
	    	public LocClickListener(Activity activity, Location location) {
	    		this.location = location;
	    		this.activity = activity;
	    	}
	    	
			@Override
			public void onClick(View arg0) {
				// pass the latitude and longitude to the map activity as extras
				Intent intent = new Intent(activity, PhotoHistoryMapActivity.class);
				intent.putExtra("com.AndroidProject.dailyTracking.LATITUDE", location.getLat());
				intent.putExtra("com.AndroidProject.dailyTracking.LONGITUDE", location.getLon());
				startActivity(intent);
			}
	    	
	    }
	}

}
