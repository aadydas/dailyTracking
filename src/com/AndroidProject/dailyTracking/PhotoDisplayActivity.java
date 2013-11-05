package com.AndroidProject.dailyTracking;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

/**
 * A simple class that just displays a full screen photo.
 */
public class PhotoDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_display);
		
		Intent intent = getIntent();
		// get the photos file path
		String path = intent.getStringExtra("com.AndroidProject.dailyTracking.PATH");
		ImageView imageView =(ImageView) findViewById(R.id.photo_display);
		// display the photo
		imageView.setImageBitmap(BitmapFactory.decodeFile(path));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_display, menu);
		return true;
	}

}
