package com.microsoft.o365_android_onenote_rest.model;

import com.microsoft.o365_android_onenote_rest.application.SnippetApp;

import static com.microsoft.o365_android_onenote_rest.R.string.on_base;
import static com.microsoft.o365_android_onenote_rest.R.string.on_create;
import static com.microsoft.o365_android_onenote_rest.R.string.on_update;
import static com.microsoft.o365_android_onenote_rest.R.string.on_update_by_app;
import static com.microsoft.o365_android_onenote_rest.R.string.wl_offline_access;
import static com.microsoft.o365_android_onenote_rest.R.string.wl_signin;

public class Scope {

    public static final String DELIM = "\n---\n";

    public enum wl {
        signin(wl_signin),
        offline_access(wl_offline_access);

        public final String mDescription;

        wl(int desc) {
            mDescription = getString(desc);
        }

        public String getScope() {
            return "wl." + name();
        }

        @Override
        public String toString() {
            return getScope() + DELIM + mDescription;
        }
    }

    public enum office {
        onenote_create(on_create),
        onenote_update(on_update), onenote(on_base);

        public final String mDescription;

        office(int desc) {
            mDescription = getString(desc);
        }

        public String getScope() {
            return "office." + name();
        }

        @Override
        public String toString() {
            return getScope() + DELIM + mDescription;
        }
    }

    private static String getString(int res) {
        return SnippetApp.getApp().getString(res);
    }
}
