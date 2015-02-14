/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mubaloo.org.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.mubaloo.org.bean.TeamMember;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 * <p>
 * Helper database object to handle all database related queries
 *
 * @author Zahid
 * </p>
 */
public class EmployeeDbHelper {

    /**
     * <p>
     * Android context required to create MubalooDatabase instance
     * </p>
     */
    private final Context context;

    /**
     * <p>
     * MubalooDatabase handle all sqlite related queries
     * </p>
     */
    
    private MubalooDatabase db;
    
    /**
     * <p>
     * Constructor, which accept Android context 
     * </p>
     */
    
    public EmployeeDbHelper(Context context) {
        this.context = context;
        db = MubalooDatabase.getDatabase(context);
    }

    /*
     * <p>
     * insert employee record on sqlite database
     * </p>
     */
    public boolean insertUser(JSONObject json, long postionId) throws Exception {
        if (json.has("id") && json.has("firstName")
                && json.has("lastName") && json.has("role")
                && json.has("profileImageURL") && postionId > 0) {
            ContentValues values = new ContentValues();
            values.put("userId", json.getString("id"));
            values.put("firstName", json.getString("firstName"));
            values.put("lastName", json.getString("lastName"));
            values.put("profileImageUrl", json.getString("profileImageURL"));
            values.put("positionId", postionId);
            return db.getWritableDatabase().insert("tblEmployee", null, values) > 0;
        }

        return false;

    }

    /*
     * <p>
     * insert employee branches record on sqlite database
     * </p>
     */
    public long insertBranch(String branchName) {
        ContentValues values = new ContentValues();
        values.put("branchName", branchName);
        return db.getWritableDatabase().insert("tblBranch", null, values);
    }

    /*
     * <p>
     * insert employee position record on sqlite database
     * </p>
     */
    public long insertEmployeePosition(String positionName, int teamLead, long branchId) {
        ContentValues values = new ContentValues();
        values.put("positionName", positionName);
        values.put("teamLead", teamLead);
        values.put("branchId", branchId);
        return db.getWritableDatabase().insert("tblPositions", null, values);
    }

    /*
     * <p>
     * check to see wether we will get record from cache, can be usefull and easily extendable for more advance caching functionalities, 
     * if there is any action on the server side which compare with the employee table and check there is been a record added
     * or deleted, then the response can come back with haschanged =1, means we will fire up the action to get new data otherwise it will
     * return 0 and, app side we can rely on that and populate record from cache
     * </p>
     */
    public boolean previouslyCached() {
        int cachedNumberOfEmployee = numberOfEmployee();
        int totalNumberOfEmployeeOnEmployeeTable = 0;
        Cursor c = db.getReadableDatabase().rawQuery("select count(*) as total from tblEmployee", null);
        if (c.moveToNext()) {
            totalNumberOfEmployeeOnEmployeeTable = c.getInt(c.getColumnIndex("total"));
        }

        c.close();

        if (totalNumberOfEmployeeOnEmployeeTable != 0 && cachedNumberOfEmployee != 0 && totalNumberOfEmployeeOnEmployeeTable == cachedNumberOfEmployee) {
            return true;
        }

        return false;
    }

    /*
     * <p>
     * return number of employees
     * </p>
     */
    private int numberOfEmployee() {
        Cursor c = db.getReadableDatabase().rawQuery("select numberOfEmployees from tblEmployeeCache", null);

        if (c.moveToNext()) {
            return c.getInt(c.getColumnIndex("numberOfEmployees"));
        }

        c.close();

        return 0;
    }

    /*
     * <p>
     * populate data from local sqlite database
     * </p>
     */
    public ArrayList<TeamMember> populate() throws Exception {
        ArrayList<TeamMember> teamLists = new ArrayList<TeamMember>();

        String sql = "Select e.userId, e.firstName, e.lastName, e.profileImageUrl, p.positionName,"
                + " p.teamLead, b.branchName "
                + " from tblEmployee e inner join tblPositions p "
                + " on e.positionId = p.positionId inner join tblBranch b on"
                + " p.branchId = b.branchId";

        System.out.println(sql);

        Cursor c = db.getReadableDatabase().rawQuery(sql, null);

        while (c.moveToNext()) {
            TeamMember tm = new TeamMember(c);
            teamLists.add(tm);

        }

        
        c.close();

        addEmployeeCacheRecord(teamLists.size());

        return teamLists;
    }

    /*
     * <p>
     * insert number of employee on tblEmployeeCache table, this table should always have one record
     * </p>
     */
    public void addEmployeeCacheRecord(int numberOfRecords) {
        db.getWritableDatabase().execSQL("DELETE FROM tblEmployeeCache WHERE _id > 0");
        db.getWritableDatabase().execSQL("INSERT OR REPLACE INTO tblEmployeeCache (numberOfEmployees) VALUES(" + numberOfRecords + ")");
    }
}
