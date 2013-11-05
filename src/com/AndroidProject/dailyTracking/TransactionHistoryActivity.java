package com.AndroidProject.dailyTracking;

import java.util.List;

import com.AndroidProject.dailyTracking.entities.Location;
import com.AndroidProject.dailyTracking.entities.Transaction;
import com.AndroidProject.dailyTracking.entities.TransactionLogic;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * An activity that displays the history of user spending.
 */
public class TransactionHistoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_history);
		
		// get the transaction history
		List<Transaction> transactions = TransactionLogic.getTransactionHistory(this);
		
		/* List View which displays all the transactions */
		ListView listView = (ListView) findViewById(R.id.listView1);
		
		// set custom adapter for the list view
		listView.setAdapter(new TransAdapter(this, transactions));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_history, menu);
		return true;
	}
	
	// custom adapter to display transactions in a list view
	private class TransAdapter extends BaseAdapter {
		
		private Activity activity;
	    private List<Transaction> transactions;
	    private LayoutInflater inflater;
	 
	    public TransAdapter(Activity activity, List<Transaction> transactions) {
	    	this.activity = activity;
	    	this.transactions = transactions;
	        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	 
	    public int getCount() {
	    	// size + 1 because stats about the transactions are displayed
	    	// as the first item
	        return this.transactions.size() + 1;
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
	            view = this.inflater.inflate(R.layout.transaction_row, null);
	        
	        if (position < 1) {
	        	// display total spending and average spending as the first item
	        	TextView tvTime = (TextView)view.findViewById(R.id.dateTime);
	        	tvTime.setText("Total Spending: $" + TransactionLogic.getTotalSpending(this.transactions));
	        	TextView tvStore = (TextView)view.findViewById(R.id.transStore);
	        	tvStore.setText("Average Spending: $" + TransactionLogic.getAvgSpending(this.transactions));
	        	return view;
	        }
	        
	        // Get all of the text views in this item
	        TextView tvTime = (TextView)view.findViewById(R.id.dateTime);
	        TextView tvStore = (TextView)view.findViewById(R.id.transStore);
	        TextView tvAmount = (TextView)view.findViewById(R.id.transAmount);
	        TextView tvCategory = (TextView)view.findViewById(R.id.transCategory);
	        TextView tvLoc = (TextView)view.findViewById(R.id.locationClick);
	        
	        // get the current transaction
	        Transaction transaction = this.transactions.get(position - 1);
	        
	        // Setting all values in listview
	        tvTime.setText("Date/Time of Transaction: " + transaction.getTime());
	        tvStore.setText("Store: " + transaction.getStore());
	        tvAmount.setText("Amount: $" + transaction.getAmount());
	        tvCategory.setText("Category: " + transaction.getCategory());
	        if (transaction.getLocation() != null) {
	        	// if a location exists, set custom click listener for the location information
	        	tvLoc.setText("Click for Location Information");
	        	tvLoc.setOnClickListener(new LocClickListener(this.activity, transaction.getLocation()));
	        }
	        
	        return view;
	    }
	    
	    // custom clock listener for the location information
	    private class LocClickListener implements OnClickListener {

	    	private Location location;
	    	private Activity activity;
	    	
	    	public LocClickListener(Activity activity, Location location) {
	    		this.location = location;
	    		this.activity = activity;
	    	}
	    	
			@Override
			public void onClick(View arg0) {
				// pass coordinates to the map location activity as extras
				Intent intent = new Intent(activity, TransHistoryMapActivity.class);
				intent.putExtra("com.AndroidProject.dailyTracking.LATITUDE", location.getLat());
				intent.putExtra("com.AndroidProject.dailyTracking.LONGITUDE", location.getLon());
				startActivity(intent);
			}
	    	
	    }
	}

}
