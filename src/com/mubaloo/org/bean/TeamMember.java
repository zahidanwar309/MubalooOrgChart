/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mubaloo.org.bean;

import android.database.Cursor;

/**
 *
 * @author Zahid
 */
public class TeamMember {
    /*
    * employeeId
    */
    private int userId;
    
    /*
    * Employee's first name
    */
    
    private String firstName;
    
    /*
    * Employee's last name
    */
    
    private String lastName;
    
    /*
    * Employee's role/position
    */
    
    private String position;
    
    /*
    * Employee's profile image
    */
    
    private String imageUrl;
    
    
    /*
    * Employee's team name
    */
    
    private String teamName;
    
    /*
    * is Employee team lead, 1=yes/true, 0=no/false
    */
    
    private boolean teamLead;
    
    /*
    * Cursor to get the value from statement
    */
    
    private final Cursor curser;

    public TeamMember(Cursor curser) throws Exception {
        this.curser = curser;
        this.userId = curser.getInt(curser.getColumnIndex("userId"));
        this.firstName = curser.getString(curser.getColumnIndex("firstName"));
        this.lastName = curser.getString(curser.getColumnIndex("lastName"));        
        this.imageUrl = curser.getString(curser.getColumnIndex("profileImageUrl"));       
        this.teamLead = curser.getInt(curser.getColumnIndex("teamLead")) == 1;
        this.position = curser.getString(curser.getColumnIndex("positionName"));
        this.teamName = curser.getString(curser.getColumnIndex("branchName"));
    }
    

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isTeamLead() {
        return teamLead;
    }
    
}
