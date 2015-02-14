/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mubaloo.org.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;

/**
 * <p> 
 * Helper class for dealing with internet/network
 * connection.
 * </p>
 * @author Zahid
 */
public class ConnectionHelper {
	/**
	 * TO BE USED AS A <b>SINGLETON</b>
	 */
	private static final ConnectionHelper INSTANCE = new ConnectionHelper();
	
	/**
	 * ANDROID'S BUILT IN CONNECTION MANAGER
	 */
	private ConnectivityManager cm;
	
	/**
	 * Reusable <b><i>AlertDialog</i></b>
	 */
	private AlertDialog alert;
	
	/**
	 * <b><i>private {@link ConnectionHelper}</i></b>
	 * Singleton constructor
	 */
	private ConnectionHelper() {
	}
	
	/**
	 * <b><i>public static {@link ConnectionHelper}</i></b>
	 * 
	 * @param context - The calling {@link HTMLActivity}
	 * @return a singleton instance of {@link ConnectionHelper}
	 */
	public static ConnectionHelper defaultHelper(Context context) {
		INSTANCE.cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		INSTANCE.alert = new AlertDialog.Builder(context).create();
		
		INSTANCE.alert.setTitle("Whoops... Not Connected!");
		
		INSTANCE.alert.setMessage("An active internet connection is required to complete this action");
		
		INSTANCE.alert.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				INSTANCE.alert.dismiss();
			}
		});
		
		return INSTANCE;
	}
	/**
	 * <b><i>public boolean</i></b>
	 * @return <b>true</b> if, and only if, the network
	 * connection is connected &amp; active
	 */
	public boolean isConnected() {
		return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
	}
	/**
	 * Reuse <b><i>AlertDialog</i></b>
	 */
	public void alert() {
		alert.show();
	}
	
}