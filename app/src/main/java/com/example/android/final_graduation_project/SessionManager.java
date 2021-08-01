package com.example.android.final_graduation_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {
    static SharedPreferences userSession;
    static SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "isloggedin";
    private static final String KEY_REFRESH_TOKEN = "refreshToken";
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_HASACCOUNT = "hasAccount";

    public SessionManager(Context context) {
        this.context = context;
        userSession = this.context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }
    public static void setAccessToken(String accessToken){
        editor.putString(KEY_ACCESS_TOKEN , accessToken);
        editor.apply();

    }

    public static void createLoginSession(String refreshToken ,String accessToken ,  boolean hasAccount) {
     //   editor.putBoolean(IS_LOGIN, true);
        editor.putBoolean(KEY_HASACCOUNT, hasAccount);
        editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        Log.i("Intro + refresh", refreshToken);
        Log.i("Intro + access", accessToken);
        Log.i("Intro + hasAccount", hasAccount+"");
        editor.apply();
    }

    public static HashMap<Object, Object> setLoginUserData() {
        HashMap<Object, Object> userData = new HashMap<>();
        userData.put(KEY_REFRESH_TOKEN, userSession.getString(KEY_REFRESH_TOKEN, null));
        userData.put(KEY_REFRESH_TOKEN, userSession.getString(KEY_ACCESS_TOKEN, null));
        userData.put(KEY_HASACCOUNT, userSession.getBoolean(KEY_HASACCOUNT, false));
        return userData;
    }

    public static String getAccessToken() {
        return userSession.getString(KEY_ACCESS_TOKEN, "");
    }
    public static String getRefreshToken() {
        return userSession.getString(KEY_REFRESH_TOKEN, null);
    }
    public static boolean hasAccount() {
        return userSession.getBoolean(KEY_HASACCOUNT, false);
    }

    public static void logoutUserSession() {
        editor.clear();
        Log.i("Intro from logout : ", userSession.getString(KEY_ACCESS_TOKEN, null));
        Log.i("Intro from logout : ", userSession.getString(KEY_REFRESH_TOKEN, null));
        Log.i("Intro from logout : ", userSession.getBoolean(KEY_HASACCOUNT, false)+"");
        editor.commit();
        Log.i("Intro from logout : ", userSession.getString(KEY_ACCESS_TOKEN, null)+"");
        Log.i("Intro from logout : ", userSession.getString(KEY_REFRESH_TOKEN, null)+"");
        Log.i("Intro from logout : ", userSession.getBoolean(KEY_HASACCOUNT, false)+"");
    }
}
