package com.sparklinktech.stech.syncadaptertestdemo.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

/** * Created by CLUEBIX on 20/08/2016.
 */
public class SessionManager {


    public void setPreferences(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("status", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();


    }



    public  String getPreferences(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("status",	Context.MODE_PRIVATE);
        return prefs.getString(key, "");



    }
}