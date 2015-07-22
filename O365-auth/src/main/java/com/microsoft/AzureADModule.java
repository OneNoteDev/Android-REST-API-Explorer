/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft;

import android.app.Activity;

import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationSettings;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class AzureADModule {

    private final Builder mBuilder;

    protected AzureADModule(Builder builder) {
        mBuilder = builder;
    }

    public static class Builder {

        private static final String SHARED_PREFS_DEFAULT_NAME = "AzureAD_Preferences";

        private Activity mActivity;

        private String
                mAuthorityUrl, // the authority used to authenticate
                mAuthenticationResourceId, // the resource id used to authenticate
                mSharedPreferencesFilename = SHARED_PREFS_DEFAULT_NAME,
                mClientId,
                mRedirectUri;

        private boolean mValidateAuthority = true;

        public Builder(Activity activity) {
            mActivity = activity;
        }

        public Builder authorityUrl(String authorityUrl) {
            mAuthorityUrl = authorityUrl;
            return this;
        }

        public Builder authenticationResourceId(String authenticationResourceId) {
            mAuthenticationResourceId = authenticationResourceId;
            return this;
        }

        public Builder validateAuthority(boolean shouldEvaluate) {
            mValidateAuthority = shouldEvaluate;
            return this;
        }

        public Builder skipBroker(boolean shouldSkip) {
            AzureADModule.skipBroker(shouldSkip);
            return this;
        }

        public Builder sharedPreferencesFilename(String filename) {
            mSharedPreferencesFilename = filename;
            return this;
        }

        public Builder clientId(String clientId) {
            mClientId = clientId;
            return this;
        }

        public Builder redirectUri(String redirectUri) {
            mRedirectUri = redirectUri;
            return this;
        }

        public AzureADModule build() {
            if (null == mAuthorityUrl) {
                throw new IllegalStateException("authorityUrl() is unset");
            }
            if (null == mAuthenticationResourceId) {
                throw new IllegalStateException("authenticationResourceId() is unset");
            }
            if (null == mSharedPreferencesFilename) {
                mSharedPreferencesFilename = SHARED_PREFS_DEFAULT_NAME;
            }
            if (null == mClientId) {
                throw new IllegalStateException("clientId() is unset");
            }
            if (null == mRedirectUri) {
                throw new IllegalStateException("redirectUri() is unset");
            }
            return new AzureADModule(this);
        }

    }

    public static void skipBroker(boolean shouldSkip) {
        AuthenticationSettings.INSTANCE.setSkipBroker(shouldSkip);
    }

    @Provides
    public AuthenticationContext providesAuthenticationContext() {
        try {
            return new AuthenticationContext(
                    mBuilder.mActivity,
                    mBuilder.mAuthorityUrl,
                    mBuilder.mValidateAuthority);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    public AuthenticationManager providesAuthenticationManager(
            AuthenticationContext authenticationContext) {
        return new AuthenticationManager(
                mBuilder.mActivity,
                authenticationContext,
                mBuilder.mAuthenticationResourceId,
                mBuilder.mSharedPreferencesFilename,
                mBuilder.mClientId,
                mBuilder.mRedirectUri);
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