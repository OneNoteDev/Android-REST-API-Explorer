/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.o365_android_onenote_rest.snippet;

import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.o365_android_onenote_rest.util.User;
import com.microsoft.onenoteapi.service.NotebooksService;
import com.microsoft.onenoteapi.service.PagesService;
import com.microsoft.onenoteapi.service.SectionGroupsService;
import com.microsoft.onenoteapi.service.SectionsService;

import static com.microsoft.o365_android_onenote_rest.snippet.SnippetCategory.notebookSnippetCategory;
import static com.microsoft.o365_android_onenote_rest.snippet.SnippetCategory.pagesSnippetCategory;
import static com.microsoft.o365_android_onenote_rest.snippet.SnippetCategory.sectionGroupsSnippetCategory;
import static com.microsoft.o365_android_onenote_rest.snippet.SnippetCategory.sectionsSnippetCategory;

/**
 * The base class for snippets
 *
 * @param <Service> the service which descendants will use to make their calls
 * @param <Result>  the expected object to be returned by the service
 */
public abstract class AbstractSnippet<Service, Result> {

    public static final Services sServices = new Services();

    private String mName, mDesc, mSection, mUrl, mO365Version, mMSAVersion;
    public final Service mService;
    public final Input mInputArgs;

    private static final int sNameIndex = 0;
    private static final int sDescIndex = 1;
    private static final int sUrlIndex = 2;
    private static final int sO365VersionIndex = 3;
    private static final int sMSAVersionIndex = 4;


    /**
     * Snippet constructor
     *
     * @param category         Snippet category (Notebook, sectionGroup, section, page)
     * @param descriptionArray The String array for the specified snippet
     */
    public AbstractSnippet(
            SnippetCategory<Service> category,
            Integer descriptionArray) {

        //Get snippet configuration information from the
        //XML configuration for the snippet
        getSnippetArrayContent(category, descriptionArray);

        mService = category.mService;
        mInputArgs = Input.None;
    }

    /**
     * Snippet constructor
     *
     * @param category         Snippet category (Notebook, sectionGroup, section, page)
     * @param descriptionArray The String array for the specified snippet
     * @param inputArgs        any input arguments
     */
    public AbstractSnippet(
            SnippetCategory<Service> category,
            Integer descriptionArray,
            Input inputArgs) {

        //Get snippet configuration information from the
        //XML configuration for the snippet
        getSnippetArrayContent(category, descriptionArray);

        mSection = category.mSection;
        mService = category.mService;
        mInputArgs = inputArgs;
    }

    /**
     * Gets the items from the specified snippet XML string array and stores the values
     * in private class fields
     *
     * @param category
     * @param descriptionArray
     */
    private void getSnippetArrayContent(SnippetCategory<Service> category, Integer descriptionArray) {
        if (null != descriptionArray) {
            String[] params = SnippetApp.getApp().getResources().getStringArray(descriptionArray);

            try {
                mName = params[sNameIndex];
                mDesc = params[sDescIndex];
                mUrl = params[sUrlIndex];
                mO365Version = params[sO365VersionIndex];
                mMSAVersion = params[sMSAVersionIndex];
            } catch (IndexOutOfBoundsException ex) {
                throw new RuntimeException(
                        "Invalid array in "
                                + category.mSection
                                + " snippet XML file"
                        , ex);
            }
        } else {
            mName = category.mSection;
            mDesc = mUrl = null;
            mO365Version = null;
            mMSAVersion = null;

        }
        mSection = category.mSection;
    }

    protected static class Services {

        public final NotebooksService mNotebooksService;
        public final PagesService mPagesService;
        public final SectionGroupsService mSectionGroupsService;
        public final SectionsService mSectionsService;

        Services() {
            mNotebooksService = notebookSnippetCategory.mService;
            mPagesService = pagesSnippetCategory.mService;
            mSectionGroupsService = sectionGroupsSnippetCategory.mService;
            mSectionsService = sectionsSnippetCategory.mService;
        }
    }

    @SuppressWarnings("unused")
    public void setUp(Services services, retrofit.Callback<String[]> callback) {
        // Optional method....
        callback.success(new String[]{}, null);
    }

    /**
     * Returns the version segment of the endpoint url with input from
     * XML snippet description and authentication method (Office 365, MSA)
     *
     * @return the version of the endpoint to use
     */
    public String getVersion() {
        return User.isMsa() ? mMSAVersion : mO365Version;
    }


    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDesc;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getSection() {
        return mSection;
    }

    /**
     * Abstract declaration of method which subclasses must define to actually run the snippet
     *
     * @param service  the service instance to use
     * @param callback the recipient of the result
     */
    public abstract void request(Service service, Callback<Result> callback);

}
