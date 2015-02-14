/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mubaloo.org.activity;

import android.os.Bundle;
import com.mubaloo.org.bean.TeamMember;
import com.mubaloo.org.fragments.EmployeeChartLeftMenuFragment;
import com.mubaloo.org.fragments.EmployeeChartListFragment;
import com.mubaloo.org.fragments.EmployeeDetailsFragment;

/**
 * <p>
 * Controller for the Employee chart fragment for UI views Controller define
 * views for phone / tablet
 * </p>
 *
 * @author Zahid
 */
public class EmployeeChartActivity extends MubalooActivity {

    private EmployeeChartListFragment eployeeChartFragment = new EmployeeChartListFragment();
    private EmployeeDetailsFragment detailsFragment;
    private EmployeeChartLeftMenuFragment leftMenuFragment = new EmployeeChartLeftMenuFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //To change body of generated methods, choose Tools | Templates.

        /*
         * <p>
         * Set left content
         * tablet only, has not implemented yet
         * </p>
         */
        setLeftcontent();   //for tablet only
        /*
         * <p>
         * Set middle content
         * for phone middle content
         * form tablet right content
         * </p>
         */
        setMiddlecontent();
    }

    public void setLeftcontent() {
        replaceLeftContent(leftMenuFragment, true);
    }

    /*
     * <p>
     * Set middle fragment
     * By default EmployeeChartListFragment
     * 
     * </p>
     */
    public void setMiddlecontent() {
        replaceMiddleContent(eployeeChartFragment, true);
    }
    /*
     * <p>
     * For employee details view
     * 
     * </p>
     */

    public void employeeDetailsView(TeamMember team) {
        detailsFragment = new EmployeeDetailsFragment(team);
        replaceMiddleContent(detailsFragment, true);
    }

    @Override
    public void onBackPressed() {
        setMiddlecontent();
    }
}
