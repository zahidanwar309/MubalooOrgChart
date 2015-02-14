/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mubaloo.org.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.mubaloo.org.adapter.EmployeeChartAdapter;
import com.mubaloo.org.bean.TeamMember;
import com.mubaloo.org.chart.R;
import com.mubaloo.org.db.EmployeeDbHelper;
import com.mubaloo.proxy.cache.HTTPProxyRequest;
import com.mubaloo.proxy.utils.Logger;
import com.mubaloo.proxy.utils.Mubaloo;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Zahid
 */
public class EmployeeChartListFragment extends MubalooFragment {

    /**
     * <p>
     * Android ListView, to populate employee records
     * </p>
     */
    private ListView employeeList;

    /**
     * <p>
     * EmployeeDbHelper, a helper class to add/modify/delete/populate record
     * from local sqlite database
     * </p>
     */
    private EmployeeDbHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.employee_chart_list_fragment, container, false);

        employeeList = (ListView) root.findViewById(R.id.list);
        setTitle(root, getResources().getString(R.string.employee_list_top_bar));    //Set title on the titlebar
        setLogo(root);  //set logo

        db = new EmployeeDbHelper(getActivity());

        return root;
    }

    @Override
    public void onStart() {
        super.onStart(); //To change body of generated methods, choose Tools | Templates.

        try {
            if (!db.previouslyCached()) {
                if (checkInternetConnection()) {
                    try {
                        //Send Async request to server and get the response on the background
                        AsyncHTTPRequest task = new AsyncHTTPRequest(getActivity().getResources().getString(R.string.url));
                        task.execute();
                    } catch (Exception e) {
                        Logger.e(Mubaloo.TAG.toString(), e + "");
                    }
                } else {
                    alert();

                }
            } else {
                drawUI();   //with offline data
            }

        } catch (Exception e) {
            Logger.e(Mubaloo.TAG.toString(), e + "");
        }

    }

    /**
     * <p>
     * draw UI
     * </p>
     */
    private void drawUI() {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    ArrayList<TeamMember> teamLists = db.populate();

                    final EmployeeChartAdapter adapter = new EmployeeChartAdapter(teamLists, getActivity());
                    employeeList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Logger.e(Mubaloo.TAG.toString(), e + "");
                }

            }
        });

    }

    /**
     * <p>
     * Responsible for server request Asynchronously
     * </p>
     */
    public class AsyncHTTPRequest extends AsyncTask<String, Void, String> {

        /**
         * <p>
         * Request Url
         * </p>
         */
        private final String url;

        /**
         * <p>
         * Server request object to perform HTTP GET or POST actions
         * </p>
         */
        private final HTTPProxyRequest request;

        public AsyncHTTPRequest(String url) {
            this.url = url;
            this.request = new HTTPProxyRequest(url);
        }

        /**
         * <p>
         * Send the request on background
         * </p>
         *
         * @param arg0
         */
        @Override
        protected String doInBackground(String... arg0) {

            String response = request.httpGET().getResponseText();
            //Logger.d(Mubaloo.TAG.toString(), "JSON=" + response);
            try {
                process(response);
                drawUI();
            } catch (Exception e) {
                Logger.e(Mubaloo.TAG.toString(), e + "");
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            //implements any progressbar
        }

    }

    /**
     * <p>
     * Process handle the json string, which we are getting from server
     * </p>
     */
    public void process(String res) throws Exception {

        JSONArray teamarr = new JSONArray(res);

        for (int i = 0; i < teamarr.length(); i++) {
            JSONObject json = teamarr.getJSONObject(i);

            if (i == 0 && json.has("role") && json.getString("role").equals("CEO")) {
                //record for CEO
                long branchId = db.insertBranch("Administrative");
                long positionId = db.insertEmployeePosition(json.getString("role"), 0, branchId);
                db.insertUser(json, positionId);
            }

            if (json.has("members") && json.has("teamName")) {
                long branchId = db.insertBranch(json.getString("teamName"));
                JSONArray arr = json.getJSONArray("members");
                for (int j = 0; j < arr.length(); j++) {
                    JSONObject mem = arr.getJSONObject(j);
                    long positionId = db.insertEmployeePosition(mem.getString("role"), (mem.has("teamLead")) ? (mem.getBoolean("teamLead")) ? 1 : 0 : 0, branchId);
                    db.insertUser(mem, positionId);
                }
            }
        }
    }

    /*
     *<p>
     * Android AlertDialog to warn user, there is no internet connection.
     * By pressing the exit button, will close the app.
     * User can check the internet connection on their device and try again.
     * </p>
     */
    private void alert() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getResources().getString(R.string.internet_connection_alert_title));
        alertDialog.setMessage(getResources().getString(R.string.internet_connection_alert_message));

        alertDialog.setButton("Exit", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //gracefully close the app
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alertDialog.show();

    }
}
