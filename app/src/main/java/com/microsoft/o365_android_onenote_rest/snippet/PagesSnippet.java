/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/

package com.microsoft.o365_android_onenote_rest.snippet;

import com.google.gson.JsonArray;
import com.microsoft.o365_android_onenote_rest.R;
import com.microsoft.o365_android_onenote_rest.SnippetDetailFragment;
import com.microsoft.o365_android_onenote_rest.application.SnippetApp;
import com.microsoft.onenoteapi.service.OneNotePartsMap;
import com.microsoft.onenoteapi.service.PagesService;
import com.microsoft.onenoteapi.service.PatchCommand;
import com.microsoft.onenotevos.Envelope;
import com.microsoft.onenotevos.Page;
import com.microsoft.onenotevos.Section;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import timber.log.Timber;

import static com.microsoft.o365_android_onenote_rest.R.array.create_page_under_named_section;
import static com.microsoft.o365_android_onenote_rest.R.array.create_page_with_business_cards;
import static com.microsoft.o365_android_onenote_rest.R.array.create_page_with_image;
import static com.microsoft.o365_android_onenote_rest.R.array.create_page_with_note_tags;
import static com.microsoft.o365_android_onenote_rest.R.array.create_page_with_pdf;
import static com.microsoft.o365_android_onenote_rest.R.array.create_page_with_product_info;
import static com.microsoft.o365_android_onenote_rest.R.array.create_page_with_recipe;
import static com.microsoft.o365_android_onenote_rest.R.array.create_page_with_url_snapshot;
import static com.microsoft.o365_android_onenote_rest.R.array.create_page_with_web_page_snapshot;
import static com.microsoft.o365_android_onenote_rest.R.array.create_simple_page;
import static com.microsoft.o365_android_onenote_rest.R.array.delete_page;
import static com.microsoft.o365_android_onenote_rest.R.array.get_all_pages;
import static com.microsoft.o365_android_onenote_rest.R.array.get_page_as_html;
import static com.microsoft.o365_android_onenote_rest.R.array.get_pages_skip_and_top;
import static com.microsoft.o365_android_onenote_rest.R.array.meta_specific_page;
import static com.microsoft.o365_android_onenote_rest.R.array.page_append;
import static com.microsoft.o365_android_onenote_rest.R.array.pages_selected_meta;
import static com.microsoft.o365_android_onenote_rest.R.array.pages_specific_section;
import static com.microsoft.o365_android_onenote_rest.R.array.search_all_pages;
import static com.microsoft.o365_android_onenote_rest.R.array.specific_title;

public abstract class PagesSnippet<Result> extends AbstractSnippet<PagesService, Result> {

    public PagesSnippet(Integer descriptionArray) {
        super(SnippetCategory.pagesSnippetCategory, descriptionArray);
    }

    public PagesSnippet(Integer descriptionArray, Input input) {
        super(SnippetCategory.pagesSnippetCategory, descriptionArray, input);
    }

    static PagesSnippet[] getPagesSnippets() {
        return new PagesSnippet[]{
                // Marker element
                new PagesSnippet(null) {
                    @Override
                    public void request(
                            PagesService service
                            , Callback callback) {
                        // Not implemented
                    }
                },

                /*
                 * POST a new OneNote page in the section picked by the user
                 * HTTP POST https://www.onenote.com/api/beta/me/notes/sections/{id}/pages
                 * @see http://dev.onenote.com/docs#/reference/post-pages
                 */
                new PagesSnippet<Page>(create_simple_page, Input.Spinner) {

                    Map<String, Section> sectionMap = new HashMap<>();
                    String endpointVersion;

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(
                            PagesService service, Callback<Page> callback) {
                        DateTime date = DateTime.now();

                        String simpleHtml = getSimplePageContentBody(SnippetApp
                                .getApp()
                                .getResources()
                                .openRawResource(R.raw.simple_page), date.toString(), null);
                        String contentType = "text/html; encoding=utf8";

                        TypedString typedString = new TypedString(simpleHtml) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };

                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.postPages(
                                contentType, //Content-Type value
                                getVersion(),  //Version
                                section.id,   //Section Id,
                                typedString,       //HTML note body,
                                callback);
                    }
                },

                /*
                 * Creates a new page in a section referenced by title instead of Id
                 * HTTP POST https://www.onenote.com/api/beta/me/notes/pages{?sectionName}
                 * @see http://dev.onenote.com/docs#/reference/post-pages/v10menotespagessectionname
                 * This method is not yet supported for enterprise notebooks
                 */
                new PagesSnippet<Envelope<Page>>(create_page_under_named_section, Input.Spinner) {

                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(PagesService service, Callback callback) {
                        DateTime date = DateTime.now();
                        String simpleHtml = getSimplePageContentBody(SnippetApp
                                .getApp()
                                .getResources()
                                .openRawResource(R.raw.simple_page)
                                , date.toString()
                                , null);

                        TypedString typedString = new TypedString(simpleHtml) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };

                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.postPagesInSection(
                                "text/html; encoding=utf8",
                                getVersion(),
                                section.name,
                                typedString,
                                callback
                        );
                    }
                },

                /*
                 * Creates a page with an embedded image
                 * @see http://dev.onenote.com/docs#/reference/post-pages/v10menotespages
                 */
                new PagesSnippet<Envelope<Page>>(create_page_with_image, Input.Spinner) {

                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {
                        DateTime date = DateTime.now();

                        String imagePartName = "image1";
                        String simpleHtml = getSimplePageContentBody(SnippetApp
                                .getApp()
                                .getResources()
                                .openRawResource(R.raw.create_page_with_image)
                                , date.toString()
                                , imagePartName);

                        TypedString presentationString = new TypedString(simpleHtml) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };
                        OneNotePartsMap oneNotePartsMap = new OneNotePartsMap(presentationString);

                        File imageFile = getImageFile("/res/drawable/logo.jpg");
                        TypedFile typedFile = new TypedFile("image/jpg", imageFile);
                        oneNotePartsMap.put(imagePartName, typedFile);
                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.postMultiPartPages(
                                getVersion(),
                                section.id,
                                oneNotePartsMap,
                                callback);
                    }
                },

                /*
                 * Gets a page by using a filter query that asks for
                 * pages whose title contains text input by user
                 */
                new PagesSnippet(specific_title, Input.Text) {
                    @Override
                    public void request(PagesService service, Callback callback) {

                        service.getPages(
                                getVersion(),
                                "contains(title,'" + callback
                                        .getParams()
                                        .get(SnippetDetailFragment.ARG_TEXT_INPUT).toString() + "')",
                                null,
                                null,
                                null,
                                null,
                                null,
                                callback);
                    }
                },

                /*
                 * Creates a new page with an embedded web page
                 * @see http://dev.onenote.com/docs#/reference/post-pages/v10menotespages
                 */
                new PagesSnippet<Envelope<Page>>(create_page_with_web_page_snapshot, Input.Spinner) {

                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {
                        DateTime date = DateTime.now();
                        String embeddedPartName = "part1";
                        String simpleHtml = getSimplePageContentBody(SnippetApp
                                        .getApp()
                                        .getResources()
                                        .openRawResource(R.raw.create_page_with_web_snap),
                                date.toString(),
                                embeddedPartName);

                        TypedString presentationString = new TypedString(simpleHtml) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };

                        String embeddedWebPage = "";
                        try {
                            embeddedWebPage = IOUtils.toString(
                                    SnippetApp
                                            .getApp()
                                            .getResources()
                                            .openRawResource(R.raw.create_page_with_web_snap_part1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        TypedString typedStringPart2 = new TypedString(embeddedWebPage) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };

                        OneNotePartsMap oneNotePartsMap = new OneNotePartsMap(presentationString);
                        oneNotePartsMap.put(embeddedPartName, typedStringPart2);

                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));


                        service.postMultiPartPages(
                                getVersion(),
                                section.id,
                                oneNotePartsMap,
                                callback);
                    }
                },

                /*
                 * Deletes a page specified by page Id
                 * @see http://dev.onenote.com/docs#/reference/delete-pages
                 */
                new PagesSnippet<Envelope<Page>>(delete_page, Input.Spinner) {

                    Map<String, Page> pageMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillPageSpinner(services, callback, pageMap);
                    }

                    @Override
                    public void request(PagesService service, Callback callback) {

                        Page page = pageMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));


                        service.deletePage(
                                getVersion(),
                                page.id,
                                callback);
                    }
                },


                /*
                 * Appends an HTML para to the end of the page selected by the user
                 * @see http://dev.onenote.com/docs#/reference/patch-pages
                 */
                new PagesSnippet<Envelope<Page>>(page_append, Input.Spinner) {

                    Map<String, Page> pageMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillPageSpinner(services, callback, pageMap);
                    }

                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {

                        //Create PatchCommand list and convert to json to build patch request body
                        PatchCommand command = new PatchCommand();
                        command.mAction = "append";
                        command.mTarget = "body";
                        command.mPosition = "after";
                        command.mContent = "<p>New trailing content</p>";
                        JsonArray jsonArray = new JsonArray();
                        jsonArray.add(command.serialize(command, null, null));
                        Timber.d(jsonArray.toString());
                        TypedString typedString = new TypedString(jsonArray.toString()) {
                            @Override
                            public String mimeType() {
                                return "application/json";
                            }
                        };

                        Page page = pageMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.patchPage(
                                "",
                                getVersion(),
                                page.id,
                                typedString,
                                callback
                        );
                    }
                },

                /*
                 * Creates a page that embeds a webpage and renders the embedded web page
                 * @see http://dev.onenote.com/docs#/reference/post-pages/v10menotespages
                 */
                new PagesSnippet<Envelope<Page>>(create_page_with_url_snapshot, Input.Spinner) {

                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {
                        DateTime date = DateTime.now();
                        String simpleHtml = getSimplePageContentBody(SnippetApp
                                        .getApp()
                                        .getResources()
                                        .openRawResource(R.raw.create_page_with_url_snapshot),
                                date.toString(),
                                null);

                        TypedString typedString = new TypedString(simpleHtml) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };

                        OneNotePartsMap oneNotePartsMap = new OneNotePartsMap(typedString);

                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.postMultiPartPages(
                                getVersion(),
                                section.id,
                                oneNotePartsMap,
                                callback);
                    }
                },

                /*
                 * Gets a collection of pages in a specific section
                 * @see http://dev.onenote.com/docs#/reference/post-pages/v10menotespages
                 */
                new PagesSnippet<Envelope<Page>>(pages_specific_section, Input.Spinner) {

                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(PagesService service, Callback callback) {

                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.getSectionPages(
                                getVersion(),
                                section.id,
                                null,
                                null,
                                null,
                                null,
                                null,
                                callback);
                    }
                },

                /*
                 * Creates a page that embeds an image
                 * @see http://dev.onenote.com/docs#/reference/post-pages/v10menotespages
                 */
                new PagesSnippet<Envelope<Page>>(create_page_with_pdf, Input.Spinner) {

                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {
                        DateTime date = DateTime.now();
                        String pdfPartName = "pdfattachment1";
                        String simpleHtml = getSimplePageContentBody(SnippetApp
                                        .getApp()
                                        .getResources()
                                        .openRawResource(R.raw.create_page_with_pdf),
                                date.toString(),
                                pdfPartName);

                        TypedString presentationString = new TypedString(simpleHtml) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };
                        OneNotePartsMap oneNotePartsMap = new OneNotePartsMap(presentationString);

                        File imageFile = getImageFile("/res/drawable/attachment.pdf");

                        TypedFile typedFile = new TypedFile("application/pdf", imageFile);
                        oneNotePartsMap.put(pdfPartName, typedFile);

                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.postMultiPartPages(
                                getVersion(),
                                section.id,
                                oneNotePartsMap,
                                callback);
                    }
                },

                /*
                 * Creates a new page with HTML content that includes para tags with data-tag attributes
                 * @see http://dev.onenote.com/docs#/reference/post-pages/v10menotespages
                 */
                new PagesSnippet<Envelope<Page>>(create_page_with_note_tags, Input.Spinner) {
                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {

                        DateTime date = DateTime.now();
                        String simpleHtml = getSimplePageContentBody(SnippetApp
                                        .getApp()
                                        .getResources()
                                        .openRawResource(R.raw.create_page_with_note_tags),
                                date.toString(),
                                null);

                        TypedString presentationString = new TypedString(simpleHtml) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };
                        OneNotePartsMap oneNotePartsMap = new OneNotePartsMap(presentationString);

                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.postMultiPartPages(
                                getVersion(),
                                section.id,
                                oneNotePartsMap,
                                callback);
                    }
                },


                /*
                 * Creates a new page with content extracted from an image
                 * @see http://dev.onenote.com/docs#/reference/post-pages/v10menotespages
                 */
                new PagesSnippet<Envelope<Page>>(create_page_with_business_cards, Input.Spinner) {
                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {
                        DateTime date = DateTime.now();
                        String imagePartName = "image1";
                        String simpleHtml = getSimplePageContentBody(SnippetApp
                                        .getApp()
                                        .getResources()
                                        .openRawResource(R.raw.create_page_with_business_cards),
                                date.toString(),
                                imagePartName);

                        TypedString presentationString = new TypedString(simpleHtml) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };
                        OneNotePartsMap oneNotePartsMap = new OneNotePartsMap(presentationString);
                        File imageFile = getImageFile("/res/drawable/bizcard.png");

                        TypedFile typedFile = new TypedFile("image/jpg", imageFile);
                        oneNotePartsMap.put(imagePartName, typedFile);
                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.postMultiPartPages(
                                getVersion(),
                                section.id,
                                oneNotePartsMap,
                                callback);
                    }
                },

                /*
                 * Create a new page with contents that are extracted from a web page
                 * specified by Url in the HTML from the raw resource
                 * @see http://dev.onenote.com/docs#/reference/post-pages/v10menotespages
                 */
                new PagesSnippet<Envelope<Page>>(create_page_with_recipe, Input.Spinner) {
                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {

                        DateTime date = DateTime.now();
                        String simpleHtml = getSimplePageContentBody(SnippetApp
                                        .getApp()
                                        .getResources()
                                        .openRawResource(R.raw.create_page_with_recipe),
                                date.toString(),
                                null);

                        TypedString presentationString = new TypedString(simpleHtml) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };
                        OneNotePartsMap oneNotePartsMap = new OneNotePartsMap(presentationString);
                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.postMultiPartPages(
                                getVersion(),
                                section.id,
                                oneNotePartsMap,
                                callback);
                    }
                },

                /*
                 * Creates a new page with the content of a web page referenced by Url in
                 * HTML from raw resource
                 * @see http://dev.onenote.com/docs#/reference/post-pages/v10menotespages
                 */
                new PagesSnippet<Envelope<Page>>(create_page_with_product_info, Input.Spinner) {
                    Map<String, Section> sectionMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillSectionSpinner(services, callback, sectionMap);
                    }

                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {

                        DateTime date = DateTime.now();
                        String simpleHtml = getSimplePageContentBody(SnippetApp
                                        .getApp()
                                        .getResources()
                                        .openRawResource(R.raw.create_page_with_product_info),
                                date.toString(),
                                null);

                        TypedString presentationString = new TypedString(simpleHtml) {
                            @Override
                            public String mimeType() {
                                return "text/html";
                            }
                        };
                        OneNotePartsMap oneNotePartsMap = new OneNotePartsMap(presentationString);

                        Section section = sectionMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.postMultiPartPages(getVersion(),
                                section.id,
                                oneNotePartsMap,
                                callback);
                    }
                },

                /*
                 * Get a collection of all the user's pages
                 * @see http://dev.onenote.com/docs#/reference/get-pages/v10menotespagesfilterorderbyselecttopskipsearchcount
                 */
                new PagesSnippet<Envelope<Page>>(get_all_pages) {
                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {
                        service.getPages(getVersion(),
                                null,
                                null,
                                null, //select
                                null,
                                null,
                                null,
                                callback);
                    }
                },

                /*
                 * Get a 20 page collection, starting with the 3rd page in the user's collection
                 * @see http://dev.onenote.com/docs#/reference/get-pages/v10menotessectionsidpagesfilterorderbyselecttopskipsearchcount
                 */
                new PagesSnippet<Envelope<Page>>(get_pages_skip_and_top) {
                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {
                        service.getPages(getVersion(),
                                null,
                                null,
                                null,
                                20,
                                3,
                                null,
                                callback);
                    }
                },

                /*
                 * GET a collection of pages whose subject or content contains the search text
                 * @see http://dev.onenote.com/docs#/reference/get-pages/v10menotespagesfilterorderbyselecttopskipsearchcount
                 */
                new PagesSnippet<Envelope<Page>>(search_all_pages, Input.Text) {
                    @Override
                    public void request(PagesService service, Callback<Envelope<Page>> callback) {
                        Map<String, String> params = callback.getParams();
                        service.getPages(
                                getVersion(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                params.get(SnippetDetailFragment.ARG_TEXT_INPUT),
                                callback);
                    }
                },

                /*
                 * GET a page from the user's page collection for specific id
                 */
                new PagesSnippet<Envelope<Page>>(meta_specific_page, Input.Spinner) {

                    Map<String, Page> pageMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillPageSpinner(services, callback, pageMap);
                    }

                    @Override
                    public void request(PagesService service, Callback callback) {

                        Page page = pageMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));

                        service.getPageById(
                                getVersion(),
                                page.id,
                                callback
                        );
                    }
                },

                /*
                 * GET a collection of pages ordered by title
                 */
                new PagesSnippet(pages_selected_meta) {
                    @Override
                    public void request(PagesService service, Callback callback) {
                        service.getPages(
                                getVersion(),
                                null,
                                "title",
                                "id,title",
                                null,
                                null,
                                null,
                                callback);

                    }
                },

                /*
                 * GET the content of a page specified by page id
                 */
                new PagesSnippet<Envelope<Page>>(get_page_as_html, Input.Spinner) {
                    Map<String, Page> pageMap = new HashMap<>();

                    @Override
                    public void setUp(Services services, final retrofit.Callback<String[]> callback) {
                        fillPageSpinner(services, callback, pageMap);
                    }

                    @Override
                    public void request(PagesService service, Callback callback) {
                        Page page = pageMap.get(callback
                                .getParams()
                                .get(SnippetDetailFragment.ARG_SPINNER_SELECTION));


                        service.getPageContentById(
                                getVersion(),
                                page.id,
                                "text/html",
                                callback);
                    }
                }
        };
    }

    static String getSimplePageContentBody(
            InputStream input, String replacement, String imagePartName) {
        String simpleHtml = "";
        try {
            simpleHtml = IOUtils.toString(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (replacement != null) {
            simpleHtml = simpleHtml.replace("{contentDate}", replacement);
        }
        if (imagePartName != null) {
            simpleHtml = simpleHtml.replace("{partName}", imagePartName);
        }

        return simpleHtml;
    }

    @Override
    public abstract void request(PagesService service, Callback<Result> callback);

    /*
     * @param imagePath The path to the image
     * @return File. the image to attach to a OneNote page
     */
    protected File getImageFile(String imagePath) {
        URL imageResource = getClass().getResource(imagePath);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(
                    FilenameUtils.getBaseName(imageResource.getFile()),
                    FilenameUtils.getExtension(imageResource.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            IOUtils.copy(imageResource.openStream(),
                    FileUtils.openOutputStream(imageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    protected void fillPageSpinner(
            Services services,
            final retrofit.Callback<String[]> callback,
            final Map<String, Page> pageMap) {
        services.mPagesService.getPages(
                getVersion(),
                null,
                null,
                null,
                null,
                null,
                null,
                new Callback<Envelope<Page>>() {

                    @Override
                    public void success(Envelope<Page> envelope, Response response) {
                        Page[] pages = envelope.value;
                        String[] pageNames = new String[pages.length];
                        for (int i = 0; i < pages.length; i++) {
                            pageNames[i] = pages[i].title;
                            pageMap.put(pages[i].title, pages[i]);
                        }
                        callback.success(pageNames, response);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }

                    @Override
                    public Map<String, String> getParams() {
                        return null;
                    }

                }
        );

    }

    protected void fillSectionSpinner(
            Services services,
            final retrofit.Callback<String[]> callback,
            final Map<String, Section> sectionMap) {
        services.mSectionsService.getSections(
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