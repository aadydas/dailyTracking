package com.AndroidProject.dailyTracking;

import com.AndroidProject.dailyTracking.entities.LocationLogic;
import com.AndroidProject.dailyTracking.entities.PhotoLogic;
import com.AndroidProject.dailyTracking.entities.TransactionLogic;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * This activity presents the user with options to either view
 * tracking history (all 3 types) or delete tracking history (all 3 types).
 */
public class TrackHistoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_history);
		
		// view location history
		Button locHistoryButton = (Button)findViewById(R.id.Location);
		locHistoryButton.getBackground().setColorFilter(0x7f18e99f, PorterDuff.Mode.MULTIPLY);
		locHistoryButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (LocationLogic.getLocationHistory(TrackHistoryActivity.this).size() <= 0) {
					// no locations were found, so we go to the no history activity
					Intent i = new Intent(TrackHistoryActivity.this, NoHistoryActivity.class);
					i.putExtra("com.AndroidProject.dailyTracking.HISTORY", "location");
					startActivity(i);
				} else {
					// display location history using one or the map activities
					Intent i = new Intent(TrackHistoryActivity.this, LocHistoryMapActivity.class);
					startActivity(i);
				}
			}
		});
		
		// clear location history button
		Button clearLocHistory = (Button)findViewById(R.id.ClearLocation);
		clearLocHistory.getBackground().setColorFilter(0x7f18e99f, PorterDuff.Mode.MULTIPLY);
		
		// view transaction history
		Button transHistoryButton = (Button)findViewById(R.id.Money);
		transHistoryButton.getBackground().setColorFilter(0x7ff47835, PorterDuff.Mode.MULTIPLY);
		transHistoryButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (TransactionLogic.getTransactionHistory(TrackHistoryActivity.this).size() <= 0) {
					// no transactions were found, so we go to the no history activity
					Intent i = new Intent(TrackHistoryActivity.this, NoHistoryActivity.class);
					i.putExtra("com.AndroidProject.dailyTracking.HISTORY", "spending");
					startActivity(i);
				} else {
					// display transaction history in a new activity
					Intent i = new Intent(TrackHistoryActivity.this, TransactionHistoryActivity.class);
					startActivity(i);
				}
			}
		});
		
		// clear transaction history button
		Button clearTransHistory = (Button)findViewById(R.id.ClearMoney);
		clearTransHistory.getBackground().setColorFilter(0x7ff47835, PorterDuff.Mode.MULTIPLY);
		
		// view photo history
		Button photoHistoryButton = (Button)findViewById(R.id.Picture);
		photoHistoryButton.getBackground().setColorFilter(0x7f8250e5, PorterDuff.Mode.MULTIPLY);
		photoHistoryButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (PhotoLogic.getPhotoHistory(TrackHistoryActivity.this).size() <= 0) {
					// no photos were found, so we go to the no history activity
					Intent i = new Intent(TrackHistoryActivity.this, NoHistoryActivity.class);
					i.putExtra("com.AndroidProject.dailyTracking.HISTORY", "picture");
					startActivity(i);
				} else {
					// display photos in a new activity
					Intent i = new Intent(TrackHistoryActivity.this, PhotoHistoryActivity.class);
					startActivity(i);
				}
			}
		});
		
		// clear photo history button
		Button clearPhotoHistory = (Button)findViewById(R.id.ClearPicture);
		clearPhotoHistory.getBackground().setColorFilter(0x7f8250e5, PorterDuff.Mode.MULTIPLY);

		/* On click of BACK button go to home page */
		Button backButton = (Button)findViewById(R.id.Back);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(TrackHistoryActivity.this,HomePageActivity.class);
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.track_history, menu);
		return true;
	}
	
	// called when the clear location history button is clicked
	public void clearLocationHistory(View view) {
		// make a confirmation box
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Clear History");
		adb.setMessage("Are you sure you want to delete all of your location history?");
		adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			// if the user confirms, delete the location history
			public void onClick(DialogInterface dialog, int whichButton) {
				LocationLogic.deleteLocationHistory(TrackHistoryActivity.this);
		        Toast.makeText(TrackHistoryActivity.this, "Location History Deleted.", Toast.LENGTH_LONG).show();
		    }});
		adb.setNegativeButton(android.R.string.no, null).show();
	}
	
	// called when the clear photo history button is clicked
	public void clearPhotoHistory(View view) {
		// make a confirmation box
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Clear History");
		adb.setMessage("Are you sure you want to delete all of your picture history?");
		adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			// if the user confirms, delete the photo history
			public void onClick(DialogInterface dialog, int whichButton) {
				PhotoLogic.deletePhotos(TrackHistoryActivity.this);
		        Toast.makeText(TrackHistoryActivity.this, "Picture History Deleted.", Toast.LENGTH_LONG).show();
		    }});
		adb.setNegativeButton(android.R.string.no, null).show();
	}
	
	// called when the clear transaction history button is clicked
	public void clearSpendingHistory(View view) {
		// make a confirmation box
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setTitle("Clear History");
		adb.setMessage("Are you sure you want to delete all of your spending history?");
		adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			// if the user confirms, delete the transaction history
			public void onClick(DialogInterface dialog, int whichButton) {
				TransactionLogic.deleteTransactionHistory(TrackHistoryActivity.this);
		        Toast.makeText(TrackHistoryActivity.this, "Spending History Deleted.", Toast.LENGTH_LONG).show();
		    }});
		adb.setNegativeButton(android.R.string.no, null).show();
	}

}
