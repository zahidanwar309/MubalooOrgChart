/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mubaloo.org.bean;

import android.support.v4.app.Fragment;

/**
 *
 * @author Zahid
 */
public class FragmentStackBean {

    /**
     * <p>
     * To save as Fragment
     * </p>
     */
    private Fragment fragment;

    /**
     * <p>
     * Provide id to every fragment
     * </p>
     */
    
    private int id;

    public FragmentStackBean(Fragment fragment, int id) {
        this.fragment = fragment;
        this.id = id;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public int getId() {
        return id;
    }
}
