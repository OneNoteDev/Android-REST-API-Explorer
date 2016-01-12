/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
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