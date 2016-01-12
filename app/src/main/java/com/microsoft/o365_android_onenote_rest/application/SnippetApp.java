/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.o365_android_onenote_rest.application;

import android.app.Application;

import com.microsoft.o365_android_onenote_rest.BuildConfig;
import com.microsoft.o365_android_onenote_rest.inject.AppModule;

import javax.inject.Inject;

import dagger.ObjectGraph;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import timber.log.Timber;

public class SnippetApp extends Application {
    /**
     * The {@link dagger.ObjectGraph} used by Dagger to fulfill <code>@inject</code> annotations
     *
     * @see javax.inject.Inject
     * @see dagger.Provides
     * @see javax.inject.Singleton
     */
    public ObjectGraph mObjectGraph;

    private static SnippetApp sSnippetApp;

    @Inject
    protected String endpoint;

    @Inject
    protected Converter converter;

    @Inject
    protected RestAdapter.LogLevel logLevel;

    @Inject
    protected RequestInterceptor requestInterceptor;

    @Override
    public void onCreate() {
        super.onCreate();
        sSnippetApp = this;
        mObjectGraph = ObjectGraph.create(new AppModule());
        mObjectGraph.inject(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static SnippetApp getApp() {
        return sSnippetApp;
    }

    public RestAdapter getRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setLogLevel(logLevel)
                .setConverter(converter)
                .setRequestInterceptor(requestInterceptor)
                .build();
    }
}