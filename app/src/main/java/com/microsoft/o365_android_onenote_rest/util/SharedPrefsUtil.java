package com.microsoft.o365_android_onenote_rest.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.o365_android_onenote_rest.inject.AppModule;

public class SharedPrefsUtil {

    public static final String PREF_AUTH_TOKEN = "PREF_AUTH_TOKEN";

    public static SharedPreferences getSharedPreferences() {
        return SnippetApp.getApp().getSharedPreferences(AppModule.PREFS, Context.MODE_PRIVATE);
    }

    public static void persistAuthToken(AuthenticationResult result) {
        getSharedPreferences().edit().putString(
                PREF_AUTH_TOKEN, result.getAccessToken())
                .commit();
    }
}
