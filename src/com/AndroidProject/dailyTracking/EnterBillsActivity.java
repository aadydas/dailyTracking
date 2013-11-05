package com.AndroidProject.dailyTracking;

import com.AndroidProject.dailyTracking.entities.Location;
import com.AndroidProject.dailyTracking.entities.Transaction;
import com.AndroidProject.dailyTracking.entities.TransactionLogic;
import com.AndroidProject.dailyTracking.services.GPSTracker;
import com.AndroidProject.dailyTracking.util.Util;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Activity that prompts the user to enter information about an expenditure.
 */
public class EnterBillsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_bills);
		
		// if the user cancels, just send them back to the home page
		Button cancelButton = (Button)findViewById(R.id.cancelValue);
		cancelButton.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View arg0) {
	         Intent intent = new Intent(EnterBillsActivity.this, HomePageActivity.class);
	         startActivity(intent);
	         }
	      });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_bills, menu);
		return true;
	}
	
	// called when the user clicks the 'Save' button
	public void onSubmit(View view) {
		
		try {
			// get the entered store
			EditText editText = (EditText) findViewById(R.id.storeNameValue);
	    	String store = Util.sanitizeInput(editText.getText().toString());
	    	
	    	// get the selected category
	    	Spinner spinner = (Spinner) findViewById(R.id.categorySpin);
	    	String category = String.valueOf(spinner.getSelectedItem());
	    	
	    	// get the bill amount
	    	editText = (EditText) findViewById(R.id.billAmountValue);
	    	double amount = Double.parseDouble(editText.getText().toString());
	    	
	    	Location location = null;
	    	if (TrackOptionsActivity.moneyEnabled) {
	    		// location tracking is enabled, get location
	    		GPSTracker gps = new GPSTracker(EnterBillsActivity.this);
	    		if (gps.LocationExists()) {
	    			location = new Location(0, gps.getLatitude(), gps.getLongitude(), null);
	    		}
	    	}
	    	
	    	// make the new transaction and save it
	    	Transaction trans = new Transaction(0, amount, store, category, location, null);
	    	TransactionLogic.addTransaction(EnterBillsActivity.this, trans);
	    	
	    	Toast.makeText(EnterBillsActivity.this, "Transaction successfully added to history.",
	    			Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			// report error to user
			Toast.makeText(EnterBillsActivity.this, 
					"We're sorry, but there has been a problem saving the transaction. Please try again later.",
	    			Toast.LENGTH_LONG).show();
		}
    	
    	// navigate back to the home page
    	Intent intent = new Intent(EnterBillsActivity.this, HomePageActivity.class);
    	startActivity(intent);
    	
	}

}
