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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.mubaloo.org.activity.EmployeeChartActivity;
import com.mubaloo.org.bean.TeamMember;
import com.mubaloo.org.chart.R;

/**
 *
 * @author Zahid
 */
public class EmployeeDetailsFragment extends MubalooFragment {

    private final TeamMember team;
    
    
    private TextView userId;
    private TextView userFirstName;
    private TextView userLastName;
    private TextView userPosition;
    private TextView userTeamLeader;
    private ImageView userImage;

    
    public EmployeeDetailsFragment(TeamMember team) {
        this.team = team;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.employee_details_fragment, container, false);

        setTitle(root, getResources().getString(R.string.employee_details_top_bar));    //Set title on the titlebar
        setLeftButton(root);
        
        userId = (TextView) root.findViewById(R.id.user_id);
        userFirstName = (TextView) root.findViewById(R.id.first_name);
        userLastName = (TextView) root.findViewById(R.id.last_name);
        userPosition = (TextView) root.findViewById(R.id.position_name);
        userTeamLeader = (TextView) root.findViewById(R.id.team_lead);
        userImage = (ImageView) root.findViewById(R.id.employee_image);
        
        leftbarbutton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                ((EmployeeChartActivity)getActivity()).onBackPressed();
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart(); //To change body of generated methods, choose Tools | Templates.
        setUserDataOnUI();
    }
    
    /*
    * <p>
    * </p>
    */
    private void setUserDataOnUI() {
        if(team != null) {
            userId.setText(String.valueOf(team.getUserId()));
            userFirstName.setText(team.getFirstName());
            userLastName.setText(team.getLastName());
            userPosition.setText(team.getPosition());
            userTeamLeader.setText((team.isTeamLead()) ? "Yes" : "No");
            imageDownloder.download(team.getImageUrl(), userImage);
        }
    }

}
