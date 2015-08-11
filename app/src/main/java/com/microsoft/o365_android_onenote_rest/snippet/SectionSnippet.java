/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/

package com.microsoft.o365_android_onenote_rest.snippet;

import com.google.gson.JsonObject;
import com.microsoft.o365_android_onenote_rest.SnippetDetailFragment;
import com.microsoft.onenoteapi.service.NotebooksService;
import com.microsoft.onenoteapi.service.SectionGroupsService;
import com.microsoft.onenoteapi.service.SectionsService;
import com.microsoft.onenotevos.Envelope;
import com.microsoft.onenotevos.Notebook;
import com.microsoft.onenotevos.SectionGroup;
import com.microsoft.onenotevos.Section;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

import static com.microsoft.o365_android_onenote_rest.R.array.create_section;
import static com.microsoft.o365_android_onenote_rest.R.array.create_section_in_sectiongroup;
import static com.microsoft.o365_android_onenote_rest.R.array.get_all_sections;
import static com.microsoft.o365_android_onenote_rest.R.array.get_metadata_of_section;
import static com.microsoft.o365_android_onenote_rest.R.array.sections_specific_name;
import static com.microsoft.o365_android_onenote_rest.R.array.sections_specific_notebook;

public abstract class SectionSnippet<Result>
        extends AbstractSnippet<SectionsService, Result> {

    public SectionSnippet(Integer descriptionArray) {
        super(SnippetCategory.sectionsSnippetCategory, descriptionArray);
    }

    public SectionSnippet(Integer descriptionArray, Input input) {
        super(SnippetCategory.sectionsSnippetCategory, descriptionArray, input);
    }

    static SectionSnippet[] getSectionsServiceSnippets() {
        return new SectionSnippet[]{
                // Marker element
                new SectionSnippet(null) {
                    @Override
                    public void request(SectionsService service, Callback callback) {
                        // not implemented
                    }
                },
                /*
                 * Gets all sections in the selected notebook specified by notebook id
                 */
                new SectionSnippet<String[]>(sections_specific_notebook, Input.Spinner) {

                    Map<String, Notebook> notebookMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillNotebookSpinner(services.mNotebooksService, callback, notebookMap);
                    }

                    @Override
                    public void request(SectionsService service, Callback callback) {

                        Notebook notebook = notebookMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION).toString());

                        service.getNotebookSections(
                                getVersion(),
                                notebook.id,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                callback
                        );
                    }
                },

                /*
                 * Gets all of the sections in the user's default notebook
                 * HTTP GET https://www.onenote.com/api/beta/me/notes/sections
                 */
                new SectionSnippet<Envelope<Section>>(get_all_sections) {
                    @Override
                    public void request(SectionsService service, Callback<Envelope<Section>> callback) {
                        service.getSections(
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
                 * Gets any section whose title matches the given title
                 * HTTP GET https://www.onenote.com/api/beta/me/notes/sections?filter=name+eq+%27{1}%27
                 */
                new SectionSnippet<Envelope<Section>>(sections_specific_name, Input.Text) {

                    @Override
                    public void request(SectionsService service, Callback<Envelope<Section>> callback) {
                        service.getSections(
                                getVersion(),
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
                 * Gets the metadata for a section by section id
                 */
                new SectionSnippet<Envelope<Section>>(get_metadata_of_section, Input.Spinner) {
                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services.mSectionsService, callback, sectionMap);
                    }

                    @Override
                    public void request(SectionsService service, Callback callback) {

                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.getSectionById(
                                getVersion(),
                                section.id,
                                callback);
                    }
                },

                /*
                 * Creates a new section with a title in a notebook specified by id
                 */
                new SectionSnippet<Envelope<Section>>(create_section, Input.Both) {

                    Map<String, Notebook> notebookMap = new HashMap<>();

                    @Override
                    public void setUp(
                            Services services,
                            final retrofit.Callback<String[]> callback) {
                        fillNotebookSpinner(services.mNotebooksService, callback, notebookMap);
                    }

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

                    @Override
                    public void request(SectionsService service, Callback callback) {

                        Notebook notebook = notebookMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION).toString());

                        service.postSection(
                                getVersion(),
                                "application/json",
                                notebook.id,
                                createNewSection(callback
                                        .getParams()
                                        .get(SnippetDetailFragment.ARG_TEXT_INPUT)
                                        .toString()),
                                callback
                        );
                    }
                },

                /*
                 * Creates a new section with a title in a section group specified by id
                 */
                new SectionSnippet<Envelope<Section>>(create_section_in_sectiongroup, Input.Both) {

                    Map<String, SectionGroup> sectionGroupMap = new HashMap<>();

                    @Override
                    public void setUp(
                            Services services,
                            final retrofit.Callback<String[]> callback) {
                        fillSectionGroupSpinner(services.mSectionGroupsService, callback, sectionGroupMap);
                    }

                    //Create the JSON body of a new section request.
                    //The body sets the section name
                    TypedString createSectionInSectionGroup(String sectionName) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("name", sectionName);
                        return new TypedString(jsonObject.toString()) {
                            @Override
                            public String mimeType() {
                                return "application/json";
                            }
                        };
                    }

                    @Override
                    public void request(SectionsService service, Callback callback) {

                        SectionGroup sectionGroup = sectionGroupMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION).toString());

                        service.postSectionInSectionGroup(
                                getVersion(),
                                "application/json",
                                sectionGroup.id,
                                createSectionInSectionGroup(callback
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
    public abstract void request(SectionsService service, Callback<Result> callback);


    protected void fillNotebookSpinner(
            NotebooksService notebooksService,
            final retrofit.Callback<String[]> callback,
            final Map<String, Notebook> notebookMap) {
        notebooksService.getNotebooks(getVersion(),
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

    protected void fillSectionGroupSpinner(
            SectionGroupsService sectionGroupsService,
            final retrofit.Callback<String[]> callback,
            final Map<String, SectionGroup> sectionGroupMap) {
        sectionGroupsService.getSectionGroups(getVersion(),
                null,
                null,
                null,
                null,
                null,
                null,
                new Callback<Envelope<SectionGroup>>() {

                    @Override
                    public void success(Envelope<SectionGroup> sectionGroupEnvelope, Response response) {
                        SectionGroup[] sectionGroups = sectionGroupEnvelope.value;
                        String[] sectionGroupNames = new String[sectionGroups.length];
                        for (int i = 0; i < sectionGroups.length; i++) {
                            sectionGroupNames[i] = sectionGroups[i].name;
                            sectionGroupMap.put(sectionGroups[i].name, sectionGroups[i]);
                        }
                        callback.success(sectionGroupNames, response);
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

    protected void fillSectionSpinner(
            SectionsService sectionsService,
            final retrofit.Callback<String[]> callback,
            final Map<String, Section> sectionMap) {
        sectionsService.getSections(
                getVersion(),
                null,
                null,
                null,
                null,
                null,
                null,
                new Callback<Envelope<Section>>() {

                    @Override
                    public void success(Envelope<Section> envelope, Response response) {
                        Section[] sections = envelope.value;
                        String[] sectionNames = new String[sections.length];
                        for (int i = 0; i < sections.length; i++) {
                            sectionNames[i] = sections[i].name;
                            sectionMap.put(sections[i].name, sections[i]);
                        }
                        callback.success(sectionNames, response);

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