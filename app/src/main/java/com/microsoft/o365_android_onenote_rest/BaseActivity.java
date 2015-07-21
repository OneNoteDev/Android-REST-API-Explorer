/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/

package com.microsoft.o365_android_onenote_rest;

import com.microsoft.AzureADModule;
import com.microsoft.AzureAppCompatActivity;
import com.microsoft.live.LiveAuthClient;
import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.o365_android_onenote_rest.conf.ServiceConstants;
import com.microsoft.o365_android_onenote_rest.inject.AzureModule;
import com.microsoft.o365_android_onenote_rest.inject.ObjectGraphInjector;
import com.microsoft.o365_android_onenote_rest.model.Scope;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.ObjectGraph;
import timber.log.Timber;

public abstract class BaseActivity
        extends AzureAppCompatActivity
        implements ObjectGraphInjector {

    @Inject
    protected LiveAuthClient mLiveAuthClient;

    public static final Iterable<String> sSCOPES = new ArrayList<String>() {{
        for (Scope.wl scope : Scope.wl.values()) {
            Timber.i("Adding scope: " + scope);
            add(scope.getScope());
        }
        for (Scope.office scope : Scope.office.values()) {
            Timber.i("Adding scope: " + scope);
            add(scope.getScope());
        }
    }};

    @Override
    protected AzureADModule getAzureADModule() {
        AzureADModule.Builder builder = new AzureADModule.Builder(this);
        builder.validateAuthority(true)
                .skipBroker(true)
                .authenticationResourceId(ServiceConstants.AUTHENTICATION_RESOURCE_ID)
                .authorityUrl(ServiceConstants.AUTHORITY_URL)
                .redirectUri(ServiceConstants.REDIRECT_URI)
                .clientId(ServiceConstants.CLIENT_ID);
        return builder.build();
    }

    @Override
    protected Object[] getModules() {
        return new Object[]{new AzureModule()};
    }

    @Override
    protected ObjectGraph getRootGraph() {
        return SnippetApp.getApp().mObjectGraph;
    }

    @Override
    public void inject(Object target) {
        mObjectGraph.inject(target);
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