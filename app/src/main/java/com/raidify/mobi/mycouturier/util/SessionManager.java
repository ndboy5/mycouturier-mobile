package com.raidify.mobi.mycouturier.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {
    /** Shared Preferences*/
     SharedPreferences pref;

     /**Editor for Shared preferences*/
    SharedPreferences.Editor editor;

    /** Context*/
    Context _context;

    /** Shared pref mode*/
    int PRIVATE_MODE = 0;

/** Sharedpref file name*/
private static final String PREF_NAME = "AcctPref";

    /** All Shared Preferences Keys*/
    private static final String IS_LOGIN = "isLoggedIn";

    /** User name (make variable public to access from outside)*/
    public static final String KEY_NAME = "name";

    /** Email address (make variable public to access from outside)*/
    public static final String KEY_EMAIL = "email";
    public  static final String  KEY_ROLE = "role";
    public static final String KEY_ID = "id";
    public static final String KEY_TOKEN = "token";

/** Constructor*/
public SessionManager(Context context){

    this._context = context;
    pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    editor = pref.edit();
}

    public void createLoginSession(String id, String name, String email, String role, String token){

// Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

// Storing name in pref
        editor.putString(KEY_NAME, name);

// Storing email in pref
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_TOKEN, token);

// commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
// user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
// user email id, id and role
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_ROLE, pref.getString(KEY_ROLE, null));
// return user
        return user;
    }

    public boolean isLogin(){
// Check login status
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser(){
// Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

}

