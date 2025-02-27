package com.example.fakenews.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;

/**
 * Manejador de preferencias de la sesión del usuario
 */
public class SessionPrefs {

    public static final String PREFS_NAME = "FAKENEWS_PREFS";
    public static final String PREF_USER_TOKEN = "PREF_USER_TOKEN";
    public static final String PREF_USER_EMAIL = "PREF_USER_EMAIL";
    public static final String PREF_TOKEN_FIREBASE = "PREF_TOKEN_FIREBASE";

    private SharedPreferences mPrefs;

    private boolean mIsLoggedIn = false;

    private static SessionPrefs INSTANCE;

    public static SessionPrefs get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionPrefs(context);
        }
        return INSTANCE;
    }

    private SessionPrefs(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREF_USER_TOKEN, null));
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void guardarToken(String token, String mail) {
        if (token != null) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREF_USER_TOKEN, token);
            editor.putString(PREF_USER_EMAIL, mail);
            Log.i("guardando token: ", token);
            editor.apply();
            mIsLoggedIn = true;
            timeout();
        }
    }

    public void logOut(){
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_USER_TOKEN, null);
        editor.putString(PREF_USER_EMAIL, null);
        editor.putString(PREF_TOKEN_FIREBASE, null);
        editor.apply();
    }

    private void timeout(){
        new CountDownTimer(59*60000, 1000) {

            public void onTick(long millisUntilFinished) {

                Log.i("[FAKENEWS] timeout: ", Long.toString(millisUntilFinished / 1000) + " segundos");
            }

            public void onFinish() {
                Log.i("[FAKENEWS]timeout: ", "terminado");
                logOut();
            }
        }.start();
    }
}
