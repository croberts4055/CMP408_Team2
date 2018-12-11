package com.example.abrahamlaragranados.team3proj;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    public static final String PREFS_ID_NAME = "PREFERENCE_ID";
    public static final String APP_KEY = "APP_SUPER_USER";

    public static void saveAppSuperUser(Context context, String super_user) {
        SharedPreferences.Editor preference = context.getSharedPreferences(PREFS_ID_NAME, Context.MODE_PRIVATE).edit();
        preference.putString(APP_KEY, super_user);
        preference.apply();
    }

    public static String getSuperUserPreference(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_ID_NAME, Context.MODE_PRIVATE);
        String super_user = preferences.getString(APP_KEY,"");
        if(super_user.equals(""))
            return null;

        return super_user;
    }

}