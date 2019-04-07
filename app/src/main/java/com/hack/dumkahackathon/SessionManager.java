package com.hack.dumkahackathon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    Context _context;
    public String PREFS = "Aadharlogin";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_UID = "uid";
    public static final String KEY_PIN = "pin";
    public static final String KEY_DIST = "dist";
    public static final String KEY_GEN = "gender";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFS, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String uid, String Pincode, String dist, String gender) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_UID, uid);
        editor.putString(KEY_DIST, dist);
        editor.putString(KEY_PIN, Pincode);
        editor.putString(KEY_GEN, gender);
        editor.commit();
    }

    public void checkLogin() {
        if (!isLoggedIn()) {
            Intent intent = new Intent(_context, QRScan.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intent);
        }
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_UID, pref.getString(KEY_UID, null));
        user.put(KEY_NAME, pref.getString(KEY_GEN, null));
        user.put(KEY_UID, pref.getString(KEY_PIN, null));
        user.put(KEY_NAME, pref.getString(KEY_DIST, null));
        return user;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }


}
