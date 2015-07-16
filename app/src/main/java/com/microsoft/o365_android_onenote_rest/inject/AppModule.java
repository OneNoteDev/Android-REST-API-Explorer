package com.microsoft.o365_android_onenote_rest.inject;

import android.content.Context;
import android.content.SharedPreferences;

import com.microsoft.o365_android_onenote_rest.SignInActivity;
import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.onenoteapi.service.GsonDateTime;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

@Module(library = true,
        injects = {SnippetApp.class})
public class AppModule {

    public static final String PREFS = "com.microsoft.o365_android_onenote_rest";

    @Provides
    public String providesRestEndpoint() {
        return "https://www.onenote.com/api";
    }

    @Provides
    public RestAdapter.LogLevel providesLogLevel() {
        return RestAdapter.LogLevel.FULL;
    }

    @Provides
    public Converter providesConverter() {
        return new GsonConverter(GsonDateTime.getOneNoteBuilder()
                .create());
    }

    @Provides
    public RequestInterceptor providesRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                // apply the Authorization header if we had a token...
                final SharedPreferences preferences
                        = SnippetApp.getApp().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
                final String token =
                        preferences.getString(SignInActivity.PREF_AUTH_TOKEN, null);
                if (null != token) {
                    request.addHeader("Authorization", "Bearer " + token);
                }
            }
        };
    }
}