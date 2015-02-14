/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mubaloo.org.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mubaloo.org.chart.R;

/**
 *
 * @author Zahid
 */
public class EmployeeChartLeftMenuFragment extends MubalooFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.employee_chart_left_menu, container, false);
        
        setTitle(root, getResources().getString(R.string.employee_chart_menu_title));
        return root;
    }
    
}
