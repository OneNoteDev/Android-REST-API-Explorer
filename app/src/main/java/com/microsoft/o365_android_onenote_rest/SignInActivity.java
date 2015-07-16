package com.microsoft.o365_android_onenote_rest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.o365_android_onenote_rest.inject.AppModule;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity
        extends BaseActivity
        implements AuthenticationCallback<AuthenticationResult> {

    public static final String PREF_AUTH_TOKEN = "PREF_AUTH_TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.o365_signin)
    public void onSignInClicked(View view) {
        mAuthenticationManager.connect(this);
    }

    @Override
    public void onSuccess(AuthenticationResult authenticationResult) {
        finish();
        persistAuthToken(authenticationResult);
        Intent appLaunch = new Intent(this, SnippetListActivity.class);
        startActivity(appLaunch);
    }

    private void persistAuthToken(AuthenticationResult authenticationResult) {
        SharedPreferences preferences
                = getSharedPreferences(AppModule.PREFS, Context.MODE_PRIVATE);
        preferences
                .edit()
                .putString(
                        PREF_AUTH_TOKEN, authenticationResult.getAccessToken())
                .apply();
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
