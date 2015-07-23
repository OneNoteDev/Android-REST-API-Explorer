/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/

package com.microsoft.o365_android_onenote_rest.snippet;

import com.google.gson.JsonObject;
import com.microsoft.o365_android_onenote_rest.SnippetDetailFragment;
import com.microsoft.onenoteapi.service.NotebooksService;
import com.microsoft.onenotevos.Envelope;
import com.microsoft.onenotevos.Notebook;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

import static com.microsoft.o365_android_onenote_rest.R.array.create_new_notebook;
import static com.microsoft.o365_android_onenote_rest.R.array.get_all_notebooks;
import static com.microsoft.o365_android_onenote_rest.R.array.get_notebook_by_id;
import static com.microsoft.o365_android_onenote_rest.R.array.get_notebooks_expand;
import static com.microsoft.o365_android_onenote_rest.R.array.get_notebooks_shared_by_others;
import static com.microsoft.o365_android_onenote_rest.R.array.notebook_selected_meta;
import static com.microsoft.o365_android_onenote_rest.R.array.notebook_specific_meta;
import static com.microsoft.o365_android_onenote_rest.R.array.notebook_specific_name;

public abstract class NotebookSnippet<Result> extends AbstractSnippet<NotebooksService, Result> {

    public NotebookSnippet(Integer descriptionArray) {
        super(SnippetCategory.notebookSnippetCategory, descriptionArray);
    }

    public NotebookSnippet(Integer descriptionArray, Input input) {
        super(SnippetCategory.notebookSnippetCategory, descriptionArray, input);
    }

    static NotebookSnippet[] getNotebookSnippets() {
        return new NotebookSnippet[]{
                // Marker element
                new NotebookSnippet(null) {
                    @Override
                    public void request(NotebooksService service, Callback callback) {
                        // Not implemented
                    }
                },
                // Snippets

                /*
                 * Gets all of the user's notebooks
                 */
                new NotebookSnippet<Envelope<Notebook>>(get_all_notebooks) {
                    @Override
                    public void request(NotebooksService service, Callback<Envelope<Notebook>> callback) {
                        service.getNotebooks(
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
                 * Gets all of the user's notebooks and expands notebook sections
                 */
                new NotebookSnippet(get_notebooks_expand) {
                    /*
                    https://www.onenote.com/api/beta/me/notes/notebooks?$expand=sections,sectionGroups($expand=sections)
                     */
                    @Override
                    public void request(NotebooksService service, Callback callback) {
                        service.getNotebooks(getVersion(),
                                null, // filter
                                null, // orderby
                                null, // select
                                "sections,sectionGroups($expand=sections)", //expand
                                null, // top
                                null, // skip
                                callback);
                    }
                },

                /*
                 * Gets a notebook by specified notebook id
                 */
                new NotebookSnippet<Envelope<Notebook>>(get_notebook_by_id, Input.Spinner) {

                    Map<String, Notebook> notebookMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillNotebookSpinner(services, callback, notebookMap);
                    }

                    @Override
                    public void request(NotebooksService service, Callback callback) {
                        Notebook notebook = notebookMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION).toString());

                        service.getNotebookById(
                                getVersion(),
                                notebook.id,
                                null,
                                callback
                        );
                    }
                },

                /*
                 * Gets specified metadata from a specified notebook
                 */
                new NotebookSnippet<Envelope<Notebook>>(notebook_specific_meta, Input.Spinner) {

                    Map<String, Notebook> notebookMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillNotebookSpinner(services, callback, notebookMap);
                    }

                    @Override
                    public void request(NotebooksService service, Callback callback) {

                        Notebook notebook = notebookMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION).toString());

                        service.getNotebookById(
                                getVersion(),
                                notebook.id,
                                null,
                                callback

                        );

                    }
                },

                /*
                 * Gets a notebook in your personal OneDrive notebook folder by name
                 * For example: the Microsoft-my.sharepoint.com/personal/denisd_microsoft_com/documents/Test_Notebook
                 * location for Denis D. holds a notebook called "Test_Notebook"
                 */
                new NotebookSnippet(notebook_specific_name, Input.Text) {
                    @Override
                    public void request(NotebooksService service, Callback callback) {
                        service.getNotebooks(getVersion(),
                                "name eq '" + callback
                                        .getParams()
                                        .get(SnippetDetailFragment.ARG_TEXT_INPUT)
                                        .toString() + "'",
                                null,
                                null,
                                null,
                                null,
                                null,
                                callback);

                    }
                },
                /*
                 * Returns id and name metadata fields in sorted ascending order
                 * from all notebooks.
                 */
                new NotebookSnippet(notebook_selected_meta) {
                    @Override
                    public void request(NotebooksService service, Callback callback) {
                        service.getNotebooks(
                                getVersion(),
                                null, // filter
                                "name asc", // orderby
                                "id,name", // select
                                null, //expand
                                null, // top
                                null, // skip
                                callback);
                    }
                },

                /*
                 * Gets notebooks whose owner is not the current user
                 */
                new NotebookSnippet(get_notebooks_shared_by_others) {
                    @Override
                    public void request(NotebooksService service, Callback callback) {
                        service.getSharedNotebooks(
                                getVersion(),
                                "userRole ne Microsoft.OneNote.Api.UserRole'Owner'", // filter
                                null, // orderby
                                null, // select
                                null, //expand
                                null, // top
                                null, // skip
                                callback);
                    }
                },

                /*
                 * Create a new notebook under Microsoft-my.sharepoint.com/personal/{username}_microsoft_com/documents/notebooks
                 */
                new NotebookSnippet<Envelope<Notebook>>(create_new_notebook, Input.Text) {
                    //Create the JSON body of a new section request.
                    //The body sets the section name
                    TypedString createNewSection(String sectionName) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("name", sectionName);
                        return new TypedString(jsonObject.toString()) {
                            @Override
                            public String mimeType() {
                                return "application/json";
                            }
                        };
                    }

                    /*
                     * Call the POST request on the Microsoft OneNote endpoint
                     */
                    @Override
                    public void request(
                            NotebooksService service,
                            Callback<Envelope<Notebook>> callback) {
                        service.postNotebook(
                                getVersion(),
                                "application/json",
                                createNewSection(
                                        callback
                                                .getParams()
                                                .get(SnippetDetailFragment.ARG_TEXT_INPUT)
                                                .toString()),
                                callback
                        );
                    }
                }
        };
    }

    @Override
    public abstract void request(NotebooksService service, Callback<Result> callback);

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
