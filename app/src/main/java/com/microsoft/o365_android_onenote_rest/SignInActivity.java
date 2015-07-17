package com.microsoft.o365_android_onenote_rest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.o365_android_onenote_rest.util.SharedPrefsUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity
        extends BaseActivity
        implements AuthenticationCallback<AuthenticationResult> {

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
        SharedPrefsUtil.persistAuthToken(authenticationResult);
        Intent appLaunch = new Intent(this, SnippetListActivity.class);
        startActivity(appLaunch);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
