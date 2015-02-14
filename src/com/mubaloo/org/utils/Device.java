/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mubaloo.org.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.provider.Settings;
import com.mubaloo.org.chart.R;
import com.mubaloo.proxy.utils.Logger;
import com.mubaloo.proxy.utils.Mubaloo;

/**
 * Handle device type, such as phone or tablet
 *
 * @author Zahid
 */
public class Device {

    private final Activity activity;

    public Device(Activity activity) {
        this.activity = activity;
    }

    /**
     * <p>
     * return true if the device is tablet
     * </p>
     * @return 
     */
    public boolean isTablet() {
        return activity.getResources().getBoolean(R.bool.isTablet);
    }

    /**
     * <p>
     * change orientation of the device, depanding on the app and customer requirement
     * By default phone orientation = Portrait
     * By default tablet orientation = Landscape
     * </p>
     */

    public void changeOrientation() {
        /*
         * <p>
         *   1 = override the rotation settings globally 
         *    (whatever settings set by the user on the device), 
         *    then safely call setRequestedOrientation() to set rotation
         *
         *   0 = Follow the user set settings
         *
         *   </p>
         */

        if (android.provider.Settings.System.getInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            Logger.d(Mubaloo.TAG.toString(), "Rotation ON");
            if (isTablet()) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else {
            Logger.d(Mubaloo.TAG.toString(), "Rotation OFF");

            if (isTablet() && activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                Logger.d(Mubaloo.TAG.toString(), "SCREEN_ORIENTATION_PORTRAIT for tablet show the alert");
                final AlertDialog alert = new AlertDialog.Builder(activity).create();
                alert.setTitle("Whoops...wrong orientation!");
                alert.setMessage("Unfortunately our app for tablet version only support lanscape mode, Please either change the orientation of your device to Auto rotation mode or hold the device on landscape mode.");

                alert.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid()); //to kill the app
                    }
                });
                alert.show();
            }
        }

    }

}
