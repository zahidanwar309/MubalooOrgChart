package com.mubaloo.org.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.mubaloo.org.chart.R;

    /**
     * <p>
     * Called when the activity is first created.
     * Been used for splash screen purposes, nothing fancy
     * </p>
     */

public class MainActivity extends Activity {
    
    /*
    * <p>
    * set the timmer value (how long the splash screen should be visible) 
    * </p>
    */
    
    private final long timeout = 1000;
    
    /*
    * <p>
    * @param savedInstanceState
    * </p>
    */
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent employeeChart = new Intent(MainActivity.this, EmployeeChartActivity.class);
                startActivity(employeeChart);
            }
        }, timeout);
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
    
    
}
