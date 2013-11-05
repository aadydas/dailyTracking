package com.AndroidProject.dailyTracking.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.AndroidProject.dailyTracking.DBLayout.DataBaseHandler;

/**
 * Class that provides business logic associated with transactions.
 */
public class TransactionLogic {
	
	/**
	 * Adds a transaction to the user's history.
	 * 
	 * @param context the current context
	 * @param transaction the transaction
	 */
	public static void addTransaction(Context context, Transaction transaction) {
		DataBaseHandler dbh = new DataBaseHandler(context);
		dbh.addTransaction(transaction);
		dbh.close();
	}
	
	/**
	 * Gets the user's transaction history.
	 * 
	 * @param context the current context
	 * @return the list of transactions
	 */
	public static List<Transaction> getTransactionHistory(Context context) {
		DataBaseHandler dbh = new DataBaseHandler(context);
		List<Transaction> transactions = dbh.getTransactions();
		dbh.close();
		return transactions;
	}
	
	/**
	 * Deletes the user's transaction history.
	 * 
	 * @param context the current context
	 */
	public static void deleteTransactionHistory(Context context) {
		DataBaseHandler dbh = new DataBaseHandler(context);
		dbh.clearTransactionHistory();
		dbh.close();
	}
	
	/**
	 * Gets the total spending of all transactions in the input list.
	 * 
	 * @param transactions the list of transactions
	 * @return the total spending
	 */
	public static double getTotalSpending(List<Transaction> transactions) {
		double totalSpending = 0.0;
		for (Transaction trans : transactions) {
			totalSpending += trans.getAmount();
		}
		return roundTwoDecimals(totalSpending);
	}
	
	/**
	 * Gets the average spending of all transactions in the input list.
	 * 
	 * @param transactions the list of transactions
	 * @return the average spending
	 */
	public static double getAvgSpending(List<Transaction> transactions) {
		if (transactions.size() == 0) {
			return 0.0;
		}
		double totalSpending = 0.0;
		for (Transaction trans : transactions) {
			totalSpending += trans.getAmount();
		}
		return roundTwoDecimals(totalSpending / (double) transactions.size());
	}
	
	// rounds a double to two decimal places
	private static double roundTwoDecimals(double x) {
	    return (double) Math.round(x * 100) / 100;
	}
	
}
