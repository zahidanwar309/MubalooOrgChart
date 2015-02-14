/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mubaloo.org.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.mubaloo.org.chart.R;
import com.mubaloo.org.utils.ConnectionHelper;
import com.mubaloo.org.utils.ImageDownloader;

/**
 * <p>
 * Root Fragment
 * </p>
 *
 * @author Zahid
 */
public abstract class MubalooFragment extends Fragment {

    /**
     * <p>
     * Top bar left button
     * </p>
     */
    protected Button leftbarbutton;

    /**
     * <p>
     * Top bar right button
     * </p>
     */
    protected Button rightbarbutton;

    /**
     * <p>
     * Top bar right extra button
     * </p>
     */
    protected Button rightbarbuttonExtra;

    /**
     * <p>
     * Top bar title
     * </p>
     */
    protected TextView titlelabel;

    /**
     * <p>
     * Top bar logo
     * </p>
     */
    protected ImageView logo;
    
    
    protected ImageDownloader imageDownloder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageDownloder = new ImageDownloader();
    }

    /**
     * <p>
     * set top bar left button
     * </p>
     */
    protected void setLeftButton(View root, String label) {
        leftbarbutton = (Button) root.findViewById(R.id.left_bar_button);
        leftbarbutton.setVisibility(View.VISIBLE);
        leftbarbutton.setText(label);
    }
    
        /**
     * <p>
     * set top bar left button, default label Back
     * </p>
     */
    protected void setLeftButton(View root) {
        leftbarbutton = (Button) root.findViewById(R.id.left_bar_button);
        leftbarbutton.setVisibility(View.VISIBLE);
        leftbarbutton.setText(getResources().getString(R.string.back_button));
    }

    /**
     * <p>
     * set top bar right button
     * </p>
     */
    protected void setRightButton(View root, String label) {
        rightbarbutton = (Button) root.findViewById(R.id.right_bar_button);
        rightbarbutton.setVisibility(View.VISIBLE);
        rightbarbutton.setText(label);
    }
    
        /**
     * <p>
     * set top bar right button, default label Cancel
     * </p>
     */
    protected void setRightButton(View root) {
        rightbarbutton = (Button) root.findViewById(R.id.right_bar_button);
        rightbarbutton.setVisibility(View.VISIBLE);
        rightbarbutton.setText(getResources().getString(R.string.cancel_button));
    }

    /**
     * <p>
     * set top bar logo
     * </p>
     */
    protected void setLogo(View root) {
        logo = (ImageView) root.findViewById(R.id.image_logo);
        logo.setVisibility(View.VISIBLE);
    }

    /**
     * <p>
     * set top bar title
     * </p>
     */
    protected void setTitle(View root, String label) {
        titlelabel = (TextView) root.findViewById(R.id.title_label);
        titlelabel.setText(label);
    }

    /**
     * <p>
     * An method to check internet connection
     * </p>
     */
    
    public boolean checkInternetConnection() {
        if (!ConnectionHelper.defaultHelper(getActivity()).isConnected()) {
            //Toast.makeText(getActivity(), "Internet connection required to perform this action.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

}
