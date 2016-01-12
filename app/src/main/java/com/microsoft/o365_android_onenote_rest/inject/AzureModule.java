package com.microsoft.o365_android_onenote_rest.inject;

import com.microsoft.AzureADModule;
import com.microsoft.o365_android_onenote_rest.SignInActivity;
import com.microsoft.o365_android_onenote_rest.SnippetDetailActivity;
import com.microsoft.o365_android_onenote_rest.SnippetDetailFragment;
import com.microsoft.o365_android_onenote_rest.SnippetListActivity;

import dagger.Module;

@Module(includes = AzureADModule.class,
        complete = false,
        injects = {
                SignInActivity.class,
                SnippetListActivity.class,
                SnippetDetailActivity.class,
                SnippetDetailFragment.class
        }
)
public class AzureModule {
}