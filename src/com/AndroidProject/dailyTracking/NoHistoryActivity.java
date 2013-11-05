package com.AndroidProject.dailyTracking;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

/**
 * An activity that informs the user that no tracking history is available
 * for the selected category and gives the user suggestions on how to
 * better utilize the app.
 */
public class NoHistoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_no_history);
		
		Intent intent = getIntent();
		// get selected type of tracking
		String historyType = intent.getStringExtra("com.AndroidProject.dailyTracking.HISTORY");
		TextView textView = (TextView)findViewById(R.id.noHistoryText);
		
		StringBuffer sb = new StringBuffer();
		// inform user that no history is available
		sb.append("Sorry, but there is currently no " + historyType + " history available. ");
		
		// give suggestions
		if (historyType.equals("location")) {
			sb.append("Try turning on location tracking from the 'Edit Your Tracking Preferences' Page");
		} else if (historyType.equals("spending")) {
			sb.append("Try entering some spending history in the 'Enter Bills' page.");
		} else if (historyType.equals("picture")) {
			sb.append("Try taking some pictures with the 'Take a Picture' feature.");
		}
		
		textView.setText(sb.toString());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.no_history, menu);
		return true;
	}

}
