/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/
package com.microsoft.o365_android_onenote_rest.model;

import com.microsoft.o365_android_onenote_rest.application.SnippetApp;

import static com.microsoft.o365_android_onenote_rest.R.string.on_base;
import static com.microsoft.o365_android_onenote_rest.R.string.on_create;
import static com.microsoft.o365_android_onenote_rest.R.string.on_update;
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
        onenote_update(on_update),
        onenote(on_base);

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
