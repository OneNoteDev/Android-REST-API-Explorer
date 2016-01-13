/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.o365_android_onenote_rest.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.o365_android_onenote_rest.inject.AppModule;
import com.microsoft.services.msa.LiveConnectSession;

public class SharedPrefsUtil {

    public static final String PREF_AUTH_TOKEN = "PREF_AUTH_TOKEN";

    public static SharedPreferences getSharedPreferences() {
        return SnippetApp.getApp().getSharedPreferences(AppModule.PREFS, Context.MODE_PRIVATE);
    }

    public static void persistAuthToken(AuthenticationResult result) {
        setAccessToken(result.getAccessToken());
        User.isOrg(true);
    }

    public static void persistAuthToken(LiveConnectSession session) {
        setAccessToken(session.getAccessToken());
        User.isMsa(true);
    }

    private static void setAccessToken(String accessToken) {
        getSharedPreferences().edit().putString(PREF_AUTH_TOKEN, accessToken).commit();
    }
}
