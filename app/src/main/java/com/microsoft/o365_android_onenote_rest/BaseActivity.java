package com.microsoft.o365_android_onenote_rest;

import com.microsoft.AzureADModule;
import com.microsoft.AzureAppCompatActivity;
import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.o365_android_onenote_rest.inject.AzureModule;
import com.microsoft.o365_android_onenote_rest.inject.ObjectGraphInjector;

import dagger.ObjectGraph;

public abstract class BaseActivity extends AzureAppCompatActivity implements ObjectGraphInjector {

    @Override
    protected AzureADModule getAzureADModule() {
        AzureADModule.Builder builder = new AzureADModule.Builder(this);
        builder.validateAuthority(true)
                .skipBroker(true)
                .authenticationResourceId("https://onenote.com")
                .authorityUrl("https://login.microsoftonline.com/common")
                .redirectUri("https://www.patsoldemo4.com/")
                .clientId("52607527-c2f8-4685-a106-4f8e103067b6");
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
