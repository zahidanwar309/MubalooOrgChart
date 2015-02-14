/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mubaloo.org.utils;

import android.support.v4.app.Fragment;
import com.mubaloo.org.bean.FragmentStackBean;
import java.util.ArrayList;

/**
 * <p>
 * save record for fragment/fragments
 *
 * @author Zahid
 * </p>
 */
public class FragmentStack {

    /**
     * <p>
     * fragments id
     * </p>
     */
    private int id = 0;
    /**
     * <p>
     * FragmentStackBean bean
     * </p>
     */
    private FragmentStackBean stack;

    /**
     * <p>
     * List of FragmentStackBean
     * </p>
     */
    private ArrayList<FragmentStackBean> al = new ArrayList();

    /**
     * <p>
     * add fragment to stack
     * </p>
     */
    public void push(Fragment f) {

        id++;
        stack = new FragmentStackBean(f, id);
        al.add(stack);
    }

    /**
     * <p>
     * get last fragment and remove it from stack
     * </p>
     */

    public Fragment pop() {
        if (al != null) {
            int size = al.size();
            if (size > 0) {
                id--;
                al.remove(size - 1);
                size = al.size();
                return (size <= 0) ? null : al.get(size - 1).getFragment();
            }
        }
        return null;
    }

    /**
     * <p>
     * remove all the fragment from the list
     * </p>
     */

    public void clear() {
        if (al != null && al.size() > 0) {
            for (int i = 0; i < al.size(); i++) {
                al.remove(i);
                id = 0;
            }
        }
    }
    
    public int size() {
        return al.size();
    }
}
