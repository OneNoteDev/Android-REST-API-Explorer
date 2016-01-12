/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.microsoft.aad.adal.ADALError;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationException;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.PromptBehavior;

import static com.microsoft.aad.adal.AuthenticationResult.AuthenticationStatus.Succeeded;

public class AuthenticationManager {

    private static final String USER_ID_VAR_NAME = "userId";

    private static final int PREFERENCES_MODE = Context.MODE_PRIVATE;

    private final Activity mActivity;

    private final AuthenticationContext mAuthenticationContext;

    private final String
            mAuthenticationResourceId,
            mSharedPreferencesFilename,
            mClientId,
            mRedirectUri;

    AuthenticationManager(
            Activity activity,
            AuthenticationContext authenticationContext,
            String authenticationResourceId,
            String sharedPreferencesFilename,
            String clientId,
            String redirectUri) {
        mActivity = activity;
        mAuthenticationContext = authenticationContext;
        mAuthenticationResourceId = authenticationResourceId;
        mSharedPreferencesFilename = sharedPreferencesFilename;
        mClientId = clientId;
        mRedirectUri = redirectUri;
    }

    private SharedPreferences getSharedPreferences() {
        return mActivity.getSharedPreferences(mSharedPreferencesFilename, PREFERENCES_MODE);
    }

    public void connect(AuthenticationCallback<AuthenticationResult> authenticationCallback) {
        if (isConnected()) {
            authenticateSilent(authenticationCallback);
        } else {
            authenticatePrompt(authenticationCallback);
        }
    }

    /**
     * Disconnects the app from Office 365 by clearing the token cache, setting the client objects
     * to null, and removing the user id from shred preferences.
     */
    public void disconnect() {
        // Clear tokens.
        if (mAuthenticationContext.getCache() != null) {
            mAuthenticationContext.getCache().removeAll();
        }

        // Forget the user
        removeUserId();
    }

    private void authenticatePrompt(
            final AuthenticationCallback<AuthenticationResult> authenticationCallback) {
        mAuthenticationContext
                .acquireToken(
                        mAuthenticationResourceId,
                        mClientId,
                        mRedirectUri,
                        null,
                        PromptBehavior.Always,
                        null,
                        new AuthenticationCallback<AuthenticationResult>() {
                            @Override
                            public void onSuccess(final AuthenticationResult authenticationResult) {
                                if (Succeeded == authenticationResult.getStatus()) {
                                    setUserId(authenticationResult.getUserInfo().getUserId());
                                    authenticationCallback.onSuccess(authenticationResult);
                                } else {
                                    onError(
                                            new AuthenticationException(ADALError.AUTH_FAILED,
                                                    authenticationResult.getErrorCode()));
                                }
                            }

                            @Override
                            public void onError(Exception e) {
                                disconnect();
                                authenticationCallback.onError(e);
                            }
                        }
                );
    }

    private void authenticateSilent(
            final AuthenticationCallback<AuthenticationResult> authenticationCallback) {
        mAuthenticationContext.acquireTokenSilent(
                mAuthenticationResourceId,
                mClientId,
                getUserId(),
                new AuthenticationCallback<AuthenticationResult>() {
                    @Override
                    public void onSuccess(AuthenticationResult authenticationResult) {
                        authenticationCallback.onSuccess(authenticationResult);
                    }

                    @Override
                    public void onError(Exception e) {
                        authenticatePrompt(authenticationCallback);
                    }
                });
    }

    private boolean isConnected() {
        return getSharedPreferences().contains(USER_ID_VAR_NAME);
    }

    private String getUserId() {
        return getSharedPreferences().getString(USER_ID_VAR_NAME, "");
    }

    private void setUserId(String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(USER_ID_VAR_NAME, value);
        editor.apply();
    }

    private void removeUserId() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(USER_ID_VAR_NAME);
        editor.apply();
    }

}