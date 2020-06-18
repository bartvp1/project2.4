package com.example.meetup;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static PrefsManager manager=null;
    private PrefsManager(Context context){
       pref= context.getSharedPreferences("meetup-pref",0);
        editor=pref.edit();
    }
    public static PrefsManager getInstance(Context context){
        if(manager==null){
            manager=new PrefsManager(context);
        }
        return manager;
    }
    public String getToken(){
        return pref.getString("token",null);
    }
    public int getExpiration(){
        return pref.getInt("expiration",0);
    }
    public SharedPreferences getPrefs(){
        return pref;
    }
    public SharedPreferences.Editor getEditor(){
        return editor;
    }
    public boolean isLoggedIn() {
        //return true;
        //remove line above
        return !(manager.getToken() == null || ((System.currentTimeMillis() / 1000) > manager.getExpiration()));
    }
}
