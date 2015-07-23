/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/

package com.microsoft.o365_android_onenote_rest.snippet;

import com.microsoft.o365_android_onenote_rest.SnippetDetailFragment;
import com.microsoft.onenoteapi.service.SectionGroupsService;
import com.microsoft.onenotevos.Envelope;
import com.microsoft.onenotevos.Notebook;
import com.microsoft.onenotevos.SectionGroup;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.microsoft.o365_android_onenote_rest.R.array.list_sectiongroups;
import static com.microsoft.o365_android_onenote_rest.R.array.list_sectiongroups_in_a_notebook;

public abstract class SectionGroupSnippet<Result>
        extends AbstractSnippet<SectionGroupsService, Result> {

    public SectionGroupSnippet(Integer descriptionArray) {
        super(SnippetCategory.sectionGroupsSnippetCategory, descriptionArray);
    }

    public SectionGroupSnippet(Integer descriptionArray, Input input) {
        super(SnippetCategory.sectionGroupsSnippetCategory, descriptionArray, input);
    }

    static SectionGroupSnippet[] getSectionGroupsSnippets() {
        return new SectionGroupSnippet[]{
                // Marker element
                new SectionGroupSnippet(null) {
                    @Override
                    public void request(SectionGroupsService service, Callback callback) {
                        // Not implemented
                    }
                },

                /*
                 * Gets all of the Section groups for all of a user's notebooks
                 * @see http://dev.onenote.com/docs#/reference/get-sectiongroups/v10menotessectiongroupsfilterorderbyselectexpandtopskipcount
                 */
                new SectionGroupSnippet<SectionGroup>(list_sectiongroups) {


                    @Override
                    public void request(SectionGroupsService service, Callback callback) {
                        service.getSectionGroups(
                                getVersion(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                callback);

                    }
                },

                /*
                 * Gets all section groups for a given notebook
                 * @see http://dev.onenote.com/docs#/reference/get-sectiongroups/v10menotesnotebooksidsectiongroupsfilterorderbyselectexpandtopskipcount
                 */
                new SectionGroupSnippet<SectionGroup>(
                        list_sectiongroups_in_a_notebook, Input.Spinner) {

                    Map<String, Notebook> notebookMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillNotebookSpinner(services, callback, notebookMap);
                    }

                    @Override
                    public void request(SectionGroupsService service, Callback callback) {
                        Notebook notebook = notebookMap.get(
                                callback.getParams().get(
                                        SnippetDetailFragment.ARG_SPINNER_SELECTION));
                        service.getSectionGroupsForNotebook(getVersion(),
                                notebook.id,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                callback);
                    }
                }
        };
    }

    @Override
    public abstract void request(SectionGroupsService service, Callback<Result> callback);

    protected void fillNotebookSpinner(
            Services services,
            final retrofit.Callback<String[]> callback,
            final Map<String, Notebook> notebookMap) {
        services.mNotebooksService.getNotebooks(getVersion(),
                null,
                null,
                null,
                null,
                null,
                null,
                new Callback<Envelope<Notebook>>() {

                    @Override
                    public void success(Envelope<Notebook> notebookEnvelope, Response response) {
                        Notebook[] notebooks = notebookEnvelope.value;
                        String[] bookNames = new String[notebooks.length];
                        for (int i = 0; i < notebooks.length; i++) {
                            bookNames[i] = notebooks[i].name;
                            notebookMap.put(notebooks[i].name, notebooks[i]);
                        }
                        callback.success(bookNames, response);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }

                    @Override
                    public Map<String, String> getParams() {
                        return null;
                    }
                });
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