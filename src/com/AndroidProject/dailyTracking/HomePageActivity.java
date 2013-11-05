package com.AndroidProject.dailyTracking;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * The home page activity.
 */
public class HomePageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		
		// navigate to the tracking options page if button is clicked
		Button saveButton = (Button)findViewById(R.id.saveValue);
		saveButton.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View arg0) {
	         Intent i = new Intent(HomePageActivity.this, TrackOptionsActivity.class);
	         startActivity(i);
	         }
	      });
		
		// navigate to the enter bill page if button is clicked
		Button EnterBillButton = (Button)findViewById(R.id.enterBillValue);
		EnterBillButton.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View arg0) {
	         Intent i = new Intent(HomePageActivity.this, EnterBillsActivity.class);
	         startActivity(i);
	         }
	      });
		
		// navigate to the camera if button is clicked
		Button takePhotoButton = (Button)findViewById(R.id.takePhoto);
		takePhotoButton.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View arg0) {
	         Intent i = new Intent(HomePageActivity.this, PictureTakerActivity.class);
	         startActivity(i);
	         }
	      });
		
		// navigate to tracking history page if button is clicked
		Button trackHistoryButton = (Button)findViewById(R.id.history);
		trackHistoryButton.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View arg0) {
	         Intent i = new Intent(HomePageActivity.this, TrackHistoryActivity.class);
	         startActivity(i);
	         }
	      });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
