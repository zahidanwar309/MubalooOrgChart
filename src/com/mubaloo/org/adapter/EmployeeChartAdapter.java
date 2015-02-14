/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mubaloo.org.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mubaloo.org.activity.EmployeeChartActivity;
import com.mubaloo.org.bean.TeamMember;
import com.mubaloo.org.chart.R;
import com.mubaloo.proxy.utils.Mubaloo;
import java.util.ArrayList;

/**
 * Android base adapter and has all the implemented method, nothing fancy
 * @author Zahid
 */
public class EmployeeChartAdapter extends BaseAdapter {

    public ArrayList<TeamMember> members = new ArrayList<TeamMember>();
    protected Context context;
    protected LayoutInflater inflater;

    public EmployeeChartAdapter(ArrayList<TeamMember> member, Context context) {
        this.context = context;
        this.members = member;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Object getItem(int index) {
        return members.get(index);
    }

    @Override
    public long getItemId(int index) {
        return members.get(index).getUserId();
    }

    @Override
    public View getView(final int index, View convertview, ViewGroup parent) {

        try {
            convertview = inflater.inflate(R.layout.employee_row, parent, false);
            TextView name = (TextView) convertview.findViewById(R.id.employee_name);

            if (getCount() > 0) {

                name.setText(members.get(index).getFirstName() + " " + members.get(index).getLastName()+ " " +((members.get(index)).isTeamLead() ? " (Team Leader)" : ""));
                
                convertview.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                       ((EmployeeChartActivity)context).employeeDetailsView(members.get(index));
                    }
                });

            }
        } catch (Exception e) {
            com.mubaloo.proxy.utils.Logger.d(Mubaloo.TAG.toString(), "" + e);
        }
        return convertview;
    }
}
