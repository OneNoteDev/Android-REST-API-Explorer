/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/

package com.microsoft.o365_android_onenote_rest.snippet;

import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.onenoteapi.service.NotebooksService;
import com.microsoft.onenoteapi.service.PagesService;
import com.microsoft.onenoteapi.service.SectionGroupsService;
import com.microsoft.onenoteapi.service.SectionsService;

import static com.microsoft.o365_android_onenote_rest.snippet.SnippetCategory.notebookSnippetCategory;
import static com.microsoft.o365_android_onenote_rest.snippet.SnippetCategory.pagesSnippetCategory;
import static com.microsoft.o365_android_onenote_rest.snippet.SnippetCategory.sectionGroupsSnippetCategory;
import static com.microsoft.o365_android_onenote_rest.snippet.SnippetCategory.sectionsSnippetCategory;

public abstract class AbstractSnippet<Service, Result> {

    public static final Services sServices = new Services();

    private String mName, mDesc, mSection, mUrl, mO365Version, mMSAVersion;
    public final Service mService;
    public final Input mInputArgs;

    private final int mNameIndex = 0;
    private final int mDescIndex = 1;
    private final int mUrlIndex = 2;
    private final int mO365VersionIndex = 3;
    private final int mMSAVersionIndex = 4;


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
     * @param category         Snippet category (Notbook, sectionGroup, section, page)
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
                mName = params[mNameIndex];
                mDesc = params[mDescIndex];
                mUrl = params[mUrlIndex];
                mO365Version = params[mO365VersionIndex];
                mMSAVersion = params[mMSAVersionIndex];
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
     * XML snippet description and authentication method (O365, MSA)
     *
     * @return
     */
    protected String getVersion() {
        //TODO get authentication location logic
        //and condition returned value on result
        //At the moment, return the O365 version
        return mO365Version;
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

    public abstract void request(Service service, Callback<Result> callback);

}
// *********************************************************
//
// Android-REST-API-Explorer, https://github.com/OneNoteDev/Android-REST-API-Explorer
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
// *********************************************************