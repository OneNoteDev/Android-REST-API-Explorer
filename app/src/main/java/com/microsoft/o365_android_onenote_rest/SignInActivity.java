package com.microsoft.o365_android_onenote_rest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.o365_android_onenote_rest.conf.ServiceConstants;
import com.microsoft.o365_android_onenote_rest.util.SharedPrefsUtil;
import com.microsoft.o365_android_onenote_rest.util.User;
import com.microsoft.services.msa.LiveAuthException;
import com.microsoft.services.msa.LiveAuthListener;
import com.microsoft.services.msa.LiveConnectSession;
import com.microsoft.services.msa.LiveStatus;

import java.net.URI;
import java.util.UUID;

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
        try {
            authenticateOrganization();
        } catch (IllegalArgumentException e) {
            warnBadClient();
        }
    }

    private void warnBadClient() {
        Toast.makeText(this,
                R.string.warning_clientid_redirecturi_incorrect,
                Toast.LENGTH_LONG)
                .show();
    }

    private void authenticateOrganization() throws IllegalArgumentException {
        validateOrganizationArgs();
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

    private void validateOrganizationArgs() throws IllegalArgumentException {
        UUID.fromString(ServiceConstants.CLIENT_ID);
        URI.create(ServiceConstants.REDIRECT_URI);
    }

    @OnClick(msa_signin)
    public void onSignInMsaClicked() {
        authenticateMsa();
    }

    private void authenticateMsa() {
        try {
            validateMsaArgs();
            mLiveAuthClient.login(this, sSCOPES, this);
        } catch (IllegalArgumentException e) {
            warnBadClient();
        }
    }

    private void validateMsaArgs() throws IllegalArgumentException {
        if (ServiceConstants.MSA_CLIENT_ID.equals("<Your MSA CLIENT ID>")) {
            throw new IllegalArgumentException();
        }
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
        String msg;
        if (null == (msg = e.getLocalizedMessage())) {
            msg = getString(R.string.signin_err);
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthComplete(final LiveStatus status,
                               final LiveConnectSession session,
                               final Object userState) {
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
        if (status == LiveStatus.CONNECTED) {
            start();
        }
    }

    @Override
    public void onAuthError(LiveAuthException exception, Object userState) {
        exception.printStackTrace();
    }
}