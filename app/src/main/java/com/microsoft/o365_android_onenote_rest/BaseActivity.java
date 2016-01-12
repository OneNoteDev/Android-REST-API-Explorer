package com.microsoft.o365_android_onenote_rest;

import com.microsoft.AzureADModule;
import com.microsoft.AzureAppCompatActivity;
import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.o365_android_onenote_rest.conf.ServiceConstants;
import com.microsoft.o365_android_onenote_rest.inject.AzureModule;
import com.microsoft.o365_android_onenote_rest.inject.ObjectGraphInjector;
import com.microsoft.o365_android_onenote_rest.model.Scope;
import com.microsoft.services.msa.LiveAuthClient;

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