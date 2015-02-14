/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mubaloo.org.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.mubaloo.org.chart.R;
import com.mubaloo.proxy.utils.Mubaloo;
import com.mubaloo.org.interfaces.ControllerViews;
import com.mubaloo.org.utils.Device;
import com.mubaloo.org.utils.FragmentStack;
import com.mubaloo.proxy.utils.Logger;

/**
 * <p>
 * Root controller
 * </p>
 *
 * @author Zahid
 */
public abstract class MubalooActivity extends FragmentActivity implements ControllerViews {

    public static String ROOT_URL;
    protected final int right_content = R.id.content_right;
    protected final int left_content = R.id.content_left;
    protected int middle_content = 0;
    private Device device = new Device(this);
    private FragmentManager fragmentmanager;
    private final FragmentStack stack = new FragmentStack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        middle_content = (device.isTablet()) ? right_content : R.id.content_layout;
        stack.clear(); //clear any existing fragment on the stack, if it's been saved from the previous controller
        setContentView(R.layout.mubaloo_activity);

        device.changeOrientation();
        this.fragmentmanager = getSupportFragmentManager();

    }

    /**
     * <p>
     * Gateway to replace tablet's left content
     * </p>
     */
    protected void replaceLeftContent(Fragment f, boolean anim) {
        if(device.isTablet())
            replaceFragment(f, left_content, anim);
    }

    /**
     * <p>
     * Gateway to replace tablet's right content
     * </p>
     */
    protected void replaceRightContent(Fragment f, boolean anim) {
        replaceFragment(f, right_content, anim);
    }

    /**
     * <p>
     * Gateway to replace phone's/tablet's middle content
     * </p>
     */
    
    protected void replaceMiddleContent(Fragment f, boolean anim) {
        replaceFragment(f, middle_content, anim);
    }

    /**
     * <p>
     * Replace any fragment, and save the fragment on the stack for future use
     * Accept contentfragment as fragment, id of the layout, to replace anim =
     * true, will have visual animation effect, anim = false, will replace
     * without any animation
     * </p>
     *
     */
    private int replaceFragment(Fragment contentfragment, int layout, boolean anim) {

        int result = 0;
        try {

            FragmentTransaction transaction = fragmentmanager.beginTransaction();
            if (anim) {
                transaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
            transaction.replace(layout, contentfragment);
            transaction.addToBackStack(null);
            result = transaction.commitAllowingStateLoss();
            getSupportFragmentManager().executePendingTransactions();
            stack.push(contentfragment);

        } catch (Exception e) {
            Logger.e(Mubaloo.TAG.toString(), e.toString() + "");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * <p>
     * Replace fragment and save on fragmentstack for future use Accept
     * contentfragment as fragment, id of the layout, to replace defaultFrag =
     * if any chance fragment stack return null, set the default fragment to
     * avoid black screen this method is useful for header back/cancel button
     *
     * </p>
     *
     */
    private int replaceFragment(Fragment contentfragment, int layout, Fragment defaultFrag) {

        int result = 0;
        try {

            FragmentTransaction transaction = fragmentmanager.beginTransaction();
            if (contentfragment != null) {
                transaction.replace(layout, contentfragment);
            } else {
                transaction.replace(layout, defaultFrag);
                stack.clear();
            }
            transaction.addToBackStack(null);
            result = transaction.commitAllowingStateLoss();
            //fragmentmanager.popBackStack();
            //result = transaction.commit();
            getSupportFragmentManager().executePendingTransactions();
            stack.push(contentfragment);

        } catch (Exception e) {
            Logger.e(Mubaloo.TAG.toString(), e.toString() + "");
        }

        return result;
    }
}
