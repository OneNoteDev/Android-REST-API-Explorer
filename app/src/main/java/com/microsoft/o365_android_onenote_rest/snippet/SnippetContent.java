package com.microsoft.o365_android_onenote_rest.snippet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.microsoft.o365_android_onenote_rest.snippet.NotebookSnippet.getNotebookSnippets;
import static com.microsoft.o365_android_onenote_rest.snippet.PagesSnippet.getPagesSnippets;
import static com.microsoft.o365_android_onenote_rest.snippet.SectionGroupSnippet.getSectionGroupsSnippets;
import static com.microsoft.o365_android_onenote_rest.snippet.SectionSnippet.getSectionsServiceSnippets;

public class SnippetContent {

    public static List<AbstractSnippet<?, ?>> ITEMS = new ArrayList<>();

    static {
        AbstractSnippet<?, ?>[][] baseSnippets = new AbstractSnippet<?, ?>[][]{
                getNotebookSnippets(),
                getPagesSnippets(),
                getSectionGroupsSnippets(),
                getSectionsServiceSnippets()
        };

        for (AbstractSnippet<?, ?>[] snippetArray : baseSnippets) {
            Collections.addAll(ITEMS, snippetArray);
        }
    }

}
