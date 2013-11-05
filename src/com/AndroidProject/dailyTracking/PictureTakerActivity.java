package com.AndroidProject.dailyTracking;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.AndroidProject.dailyTracking.entities.PhotoLogic;

/**
 * An activity that allows the user to take pictures using the app.
 */
public class PictureTakerActivity extends Activity {

	/* Declartion of local variables */
	private ImageView imageView;
	private static final int CAMERA_REQUEST = 1888;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_picture_taker);
			
			imageView = (ImageView) findViewById(R.id.imageView);
			Button cameraButton = (Button) findViewById(R.id.takePictureButton);
			
			// listen for camera button click
			cameraButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent,CAMERA_REQUEST);
				}
			});
		} catch (Exception e) {
			Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();
		}		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
			Bitmap bmp = (Bitmap) data.getExtras().get("data");	
			// save the image to disk and store file path in database
			File lastSavedFile = PhotoLogic.SaveImageToDisk(this, bmp);
			if(lastSavedFile != null){
				Bitmap myBitmap = BitmapFactory.decodeFile(lastSavedFile.getAbsolutePath());
				// display the image that was just stored
				imageView.setImageBitmap(myBitmap);
			}
				
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_taker, menu);
		return true;
	}

}
