/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/
package com.microsoft.o365_android_onenote_rest;

import android.content.Intent;
import android.os.Bundle;

import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.live.LiveAuthException;
import com.microsoft.live.LiveAuthListener;
import com.microsoft.live.LiveConnectSession;
import com.microsoft.live.LiveStatus;
import com.microsoft.o365_android_onenote_rest.util.SharedPrefsUtil;
import com.microsoft.o365_android_onenote_rest.util.User;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.microsoft.o365_android_onenote_rest.R.id.msa_signin;
import static com.microsoft.o365_android_onenote_rest.R.id.o365_signin;

public class SignInActivity
        extends BaseActivity
        implements AuthenticationCallback<AuthenticationResult>, LiveAuthListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        if (User.isOrg()) {
            mAuthenticationManager.connect(this);
        }
        ButterKnife.inject(this);
    }

    @OnClick(o365_signin)
    public void onSignInO365Clicked() {
        if (!User.isOrg()) {
            mLiveAuthClient.logout(new LiveAuthListener() {
                @Override
                public void onAuthComplete(LiveStatus status,
                                           LiveConnectSession session,
                                           Object userState) {
                    mAuthenticationManager.connect(SignInActivity.this);
                }

                @Override
                public void onAuthError(LiveAuthException exception, Object userState) {
                    mAuthenticationManager.connect(SignInActivity.this);
                }
            });
        } else {
            mAuthenticationManager.connect(this);
        }
    }

    @OnClick(msa_signin)
    public void onSignInMsaClicked() {
        mLiveAuthClient.login(this, sSCOPES, this);
    }

    @Override
    public void onSuccess(AuthenticationResult authenticationResult) {
        finish();
        SharedPrefsUtil.persistAuthToken(authenticationResult);
        start();
    }

    private void start() {
        Intent appLaunch = new Intent(this, SnippetListActivity.class);
        startActivity(appLaunch);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onAuthComplete(LiveStatus status,
                               LiveConnectSession session,
                               Object userState) {
        Timber.d("MSA: Auth Complete...");
        if (null != status) {
            Timber.d(status.toString());
        }
        if (null != session) {
            Timber.d(session.toString());
            SharedPrefsUtil.persistAuthToken(session);
        }
        if (null != userState) {
            Timber.d(userState.toString());
        }
        start();
    }

    @Override
    public void onAuthError(LiveAuthException exception, Object userState) {
        exception.printStackTrace();
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