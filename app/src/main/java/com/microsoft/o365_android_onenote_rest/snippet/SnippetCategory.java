/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/

package com.microsoft.o365_android_onenote_rest.snippet;

import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.onenoteapi.service.NotebooksService;
import com.microsoft.onenoteapi.service.PagesService;
import com.microsoft.onenoteapi.service.SectionGroupsService;
import com.microsoft.onenoteapi.service.SectionsService;

import static com.microsoft.o365_android_onenote_rest.R.string.section_notebooks;
import static com.microsoft.o365_android_onenote_rest.R.string.section_pages;
import static com.microsoft.o365_android_onenote_rest.R.string.section_sectiongroups;
import static com.microsoft.o365_android_onenote_rest.R.string.section_sections;

public class SnippetCategory<T> {
    static final SnippetCategory<NotebooksService> notebookSnippetCategory
            = new SnippetCategory<>(section_notebooks, create(NotebooksService.class));

    static final SnippetCategory<PagesService> pagesSnippetCategory
            = new SnippetCategory<>(section_pages, create(PagesService.class));

    static final SnippetCategory<SectionGroupsService> sectionGroupsSnippetCategory
            = new SnippetCategory<>(section_sectiongroups, create(SectionGroupsService.class));

    static final SnippetCategory<SectionsService> sectionsSnippetCategory
            = new SnippetCategory<>(section_sections, create(SectionsService.class));

    final String mSection;
    final T mService;

    SnippetCategory(int sectionId, T service) {
        mSection = SnippetApp.getApp().getString(sectionId);
        mService = service;
    }

    private static <T> T create(Class<T> clazz) {
        return SnippetApp.getApp().getRestAdapter().create(clazz);
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