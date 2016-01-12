package com.microsoft;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.microsoft.aad.adal.AuthenticationContext;

import javax.inject.Inject;

import dagger.ObjectGraph;

public abstract class AzureAppCompatActivity extends AppCompatActivity {

    protected ObjectGraph mObjectGraph;

    @Inject
    protected AuthenticationManager mAuthenticationManager;

    @Inject
    protected AuthenticationContext mAuthenticationContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Object[] modules = new Object[getModules().length + 1];
        int ii = 0;
        modules[ii++] = getAzureADModule();
        for (Object module : getModules()) {
            modules[ii++] = module;
        }

        mObjectGraph = getRootGraph();
        if (null == mObjectGraph) {
            // create a new one
            mObjectGraph = ObjectGraph.create(modules);
        } else {
            // extend the existing one
            mObjectGraph = mObjectGraph.plus(modules);
        }

        mObjectGraph.inject(this);
    }

    protected abstract AzureADModule getAzureADModule();

    protected abstract Object[] getModules();

    protected ObjectGraph getRootGraph() {
        return null;
    }

}