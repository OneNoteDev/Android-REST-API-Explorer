/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/
package com.microsoft.o365_android_onenote_rest.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.live.LiveConnectSession;
import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.o365_android_onenote_rest.inject.AppModule;

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
// *********************************************************
//
// Android-REST-API-Explorer, https://github.com/OneNoteDev/Android-REST-API-Explorer
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
// *********************************************************