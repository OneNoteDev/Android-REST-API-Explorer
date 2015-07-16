package com.microsoft.o365_android_onenote_rest.snippet;

import android.content.res.Resources;

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

    public final String mName, mDesc, mSection, mUrl, mO365Version, mMSAVersion;
    public final Service mService;
    public final Input mInputArgs;

    public AbstractSnippet(
            SnippetCategory<Service> category,
            Integer descriptionArray) {
        if (null != descriptionArray) {
            Resources res = SnippetApp.getApp().getResources();
            String[] params = res.getStringArray(descriptionArray);

            //TODO make this less fragile... assume one or more
            //of these string array element can be null
            mName = params[0];
            mDesc = params[1];
            mUrl = params[2];
            mO365Version = params[3];
            mMSAVersion = params[4];

        }
        else {
            mName = category.mSection;
            mDesc = mUrl = null;
            mO365Version = null;
            mMSAVersion = null;

        }
        mSection = category.mSection;
        mService = category.mService;
        mInputArgs = Input.None;
    }

    public AbstractSnippet(
            SnippetCategory<Service> category,
            Integer descriptionArray,
            Input inputArgs) {
        if (null != descriptionArray) {
            Resources res = SnippetApp.getApp().getResources();
            String[] params = res.getStringArray(descriptionArray);

            //TODO make this less fragile... assume one or more
            //of these string array element can be null
            mName = params[0];
            mDesc = params[1];
            mUrl = params[2];
            mO365Version = params[3];
            mMSAVersion = params[4];

        }
        else {
            mName = category.mSection;
            mDesc = mUrl = null;
            mO365Version = null;
            mMSAVersion = null;

        }
        mSection = category.mSection;
        mService = category.mService;
        mInputArgs = inputArgs;
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

    public abstract void request(Service service, Callback<Result> callback);

}
