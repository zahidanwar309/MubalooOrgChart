/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mubaloo.org.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mubaloo.proxy.utils.Mubaloo;
import com.mubaloo.proxy.utils.Logger;

/**
 * <p>
 * Extends SQLiteOpenHelper to handle all sqlite related queries This object can
 * be re-use to modify any record on the table A helper object to create new
 * table or indexes Can be easily extend to support dirty(), where you can
 * define which table to drop on upgrade
 * </p>
 * @author Zahid
 */
public class MubalooDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mubaloo_org_chart.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s(%s);";
    private static MubalooDatabase instance;

    private MubalooDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MubalooDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = new MubalooDatabase(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        initialiseTables(db);
        initialiseIndexes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            initialiseTables(db);
            initialiseIndexes(db);
        }

    }

    /**
     * <p>
     * Initialise all the table here
     * </p>
     */
    private void initialiseTables(SQLiteDatabase db) {
        db.beginTransaction();
        {
            createTable(db, "tblEmployee", "_id INTEGER PRIMARY KEY", "userId INTEGER", "firstName TEXT", "lastName TEXT", "profileImageUrl TEXT", "positionId INTEGER");

            createTable(db, "tblBranch", "branchId INTEGER PRIMARY KEY", "branchName TEXT");

            createTable(db, "tblPositions", "positionId INTEGER PRIMARY KEY", "positionName TEXT", "teamLead INTEGER DEFAULT 0", "branchId INTEGER");

            createTable(db, "tblEmployeeCache", "_id INTEGER PRIMARY KEY", "numberOfEmployees INTEGER");

        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * <p>
     * Initialise all the indexes
     * </p>
     */
    private void initialiseIndexes(SQLiteDatabase db) {
        db.beginTransaction();
        {
            createIndex(db, "user_id", "tblEmployee(userId, positionId)", true);

            //createIndex(db, "branch_id", "tblPositions(branchId)", true);
            
            createIndex(db, "number_of_employees", "tblEmployeeCache(numberOfEmployees)", true);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * <p>
     * A private method to create indexes
     * </p>
     */
    private boolean createIndex(SQLiteDatabase db, String indexname, String on, boolean unique) {
        if (indexname == null || on == null) {
            return false;
        }

        String index;
        if (unique) {
            index = "CREATE UNIQUE INDEX IF NOT EXISTS " + indexname + " ON " + on + ";";
        } else {
            index = "CREATE INDEX IF NOT EXISTS " + indexname + " ON " + on + ";";
        }

        try {
            if (db == null) {
                getWritableDatabase().execSQL(index);
            } else {
                db.execSQL(index);
            }
        } catch (Exception e) {
            Logger.d(Mubaloo.TAG.toString(), "" + e);
            return false;
        }

        return true;
    }

    /**
     * <p>
     * A private method to create table
     * </p>
     */
    
    private boolean createTable(SQLiteDatabase db, String table, String... columndefs) {
        if (table != null) {
            String columns = "";

            String columndef;
            for (int i = 0; i < columndefs.length; i++) {
                columndef = columndefs[i].trim();
                if (i < columndefs.length - 1 && !columndef.endsWith(",")) {
                    columndef += ",";
                }

                columns += columndef;
            }

            try {
                if (db == null) {
                    getWritableDatabase().execSQL(String.format(CREATE_TABLE, table, columns));
                } else {
                    String sql = "CREATE TABLE IF NOT EXISTS " + table + "(" + columns + ");";
                    db.execSQL(sql);
                }
            } catch (Exception e) {
                Logger.d(Mubaloo.TAG.toString(), "" + e);
                return false;
            }

            return true;
        }

        return false;
    }

}
