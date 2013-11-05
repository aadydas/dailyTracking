package com.AndroidProject.dailyTracking;

import java.util.Calendar;

import com.AndroidProject.dailyTracking.services.LocationService;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

/**
 * Activity to show Tracking Options and enable or disable them
 * Submit will take to home Page.
 */
public class TrackOptionsActivity extends Activity 
{

	/* Declaration of local variables */
	private static boolean locationEnabled = false;
	public static boolean pictureEnabled = false;
	protected static boolean moneyEnabled = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracking_options);

		/* Define all radio buttons */
		RadioButton locEnabled = (RadioButton)findViewById(R.id.locationEnableButton);
		RadioButton locDisabled = (RadioButton)findViewById(R.id.locationDisableButton);
		RadioButton picEnabled = (RadioButton)findViewById(R.id.pictureEnableButton);
		RadioButton picDisabled = (RadioButton)findViewById(R.id.pictureDisableButton);
		RadioButton monEnabled = (RadioButton)findViewById(R.id.moneyEnableButton);
		RadioButton monDisabled = (RadioButton)findViewById(R.id.moneyDosableButton);


		/* If the location Tracking was previously enabled.
		 * Set the Enable button to checked.
		 * Other Wise set the disabled button to checked.
		 */
		if(locationEnabled == true)
		{	
			((RadioButton)findViewById(R.id.locationEnableButton)).setChecked(true);
		}
		else
		{	
			((RadioButton)findViewById(R.id.locationDisableButton)).setChecked(true);
		}

		/* Set the value of locationEnabled depending on the 
		 * value set */
		locEnabled.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					//Go to the activity for button 1 here
					locationEnabled = true;
				}
			}
		});

		/* Set the value of locationEnabled depending on the 
		 * value set for other radio button*/
		locDisabled.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					//Go to the activity for button 1 here
					locationEnabled = false;
				}
			}
		});


		/* If the picture Tracking was previously enabled.
		 * Set the Enable button to checked.
		 * Other Wise set the disabled button to checked.
		 */
		if(pictureEnabled == true)
		{	
			((RadioButton)findViewById(R.id.pictureEnableButton)).setChecked(true);
		}
		else
		{	
			((RadioButton)findViewById(R.id.pictureDisableButton)).setChecked(true);
		}

		/* Set the value of pictureEnabled depending on the 
		 * value set */
		picEnabled.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					//Go to the activity for button 1 here
					pictureEnabled = true;
				}
			}
		});

		/* Set the value of pictureEnabled depending on the 
		 * value set */
		picDisabled.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					//Go to the activity for button 1 here
					pictureEnabled = false;
				}
			}
		});


		/* If the money Tracking was previously enabled.
		 * Set the Enable button to checked.
		 * Other Wise set the disabled button to checked.
		 */
		if(moneyEnabled == true)
		{	
			((RadioButton)findViewById(R.id.moneyEnableButton)).setChecked(true);
		}
		else
		{	
			((RadioButton)findViewById(R.id.moneyDosableButton)).setChecked(true);
		}

		/* Set the value of moneyEnabled depending on the 
		 * value set */
		monEnabled.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					//Go to the activity for button 1 here
					moneyEnabled = true;
				}
			}
		});


		/* Set the value of moneyEnabled depending on the 
		 * value set */
		monDisabled.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					//Go to the activity for button 1 here
					moneyEnabled = false;
				}
			}
		});


		/* On click on the SUBMIT button,
		 * depending on the values set for radio buttons
		 * Perform the action.
		 */
		Button SaveButton = (Button)findViewById(R.id.saveValue);
		SaveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) 
			{

				/* Alarm manager instantiated for starting services periodically */
				AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				Calendar cal = Calendar.getInstance();
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),LocationService.class);
				PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);

				/* Set Alarm if radio Button for location Enabled is set to true */
				if(locationEnabled == true)
				{	
					alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10*1000, pintent);   
				}
				/* Set Alarm if radio Button for location disabled is set to true */
				else if(locationEnabled == false)
				{
					alarm.cancel(pintent);
					stopService(new Intent(TrackOptionsActivity.this, LocationService.class));
				}

				/* After all the actions are done,
				 * route back to Home Page
				 */
				Intent i = new Intent(TrackOptionsActivity.this, HomePageActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main2, menu);
		return true;
	}

}
