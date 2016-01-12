package com.microsoft.onenoteapi.service;

import com.microsoft.onenotevos.Envelope;
import com.microsoft.onenotevos.Page;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedString;

public interface PagesService {

    /**
     * Gets the collection of a users OneNote pages
     * Allows for query parameters to filter, order, sort... etc
     *
     * @param filter
     * @param order
     * @param select
     * @param top
     * @param skip
     * @param search
     * @param callback
     */
    @GET("/{version}/me/notes/pages")
    void getPages(
            @Path("version") String version,
            @Query("filter") String filter,
            @Query("orderby") String order,
            @Query("select") String select,
            @Query("top") Integer top,
            @Query("skip") Integer skip,
            @Query("search") String search,
            Callback<Envelope<Page>> callback
    );


    /**
     * Gets a page specified by page id
     *
     * @param version
     * @param id
     * @param callback
     */
    @GET("/{version}/me/notes/pages/{id}")
    void getPageById(
            @Path("version") String version,
            @Path("id") String id,
            Callback<Envelope<Page>> callback
    );

    /**
     * @param version
     * @param id
     * @param callback
     */
    @GET("/{version}/me/notes/pages/{id}/content")
    void getPageContentById(
            @Path("version") String version,
            @Path("id") String id,
            @Header("Accept") String acceptType,
            Callback<Response> callback
    );

    /**
     * Gets the pages in a give OneNote section and
     * provides query parameters for sorting, ordering...
     * on the page collection
     *
     * @param version
     * @param sectionId
     * @param order
     * @param select
     * @param top
     * @param skip
     * @param search
     * @param callback
     */
    @GET("/{version}/me/notes/sections/{sectionId}/pages")
    void getSectionPages(
            @Path("version") String version,
            @Path("sectionId") String sectionId,
            @Query("orderby") String order,
            @Query("select") String select,
            @Query("top") Integer top,
            @Query("skip") Integer skip,
            @Query("search") String search,
            Callback<Envelope<Page>> callback
    );

    /**
     * Creates a new page in a specified OneNote section
     *
     * @param contentTypeHeader
     * @param version
     * @param id
     * @param content
     * @param callback
     */
    @POST("/{version}/me/notes/sections/{id}/pages")
    void postPages(
            @Header("Content-type") String contentTypeHeader,
            @Path("version") String version,
            @Path("id") String id,
            @Body TypedString content,
            Callback<Page> callback
    );

    /**
     * Creates a new page in a section specified by section title
     *
     * @param version
     * @param name
     * @param content
     * @param callback
     */
    @POST("/{version}/me/notes/pages")
    void postPagesInSection(
            @Header("Content-type") String contentTypeHeader,
            @Path("version") String version,
            @Query("sectionName") String name,
            @Body TypedString content,
            Callback<Envelope<Page>> callback
    );

    /**
     * Creates a new page with a multi-part content body
     * in a section specified by section id
     *
     * @param version
     * @param sectionId
     * @param partMap
     * @param callback
     */
    @Multipart
    @POST("/{version}/me/notes/sections/{sectionId}/pages")
    void postMultiPartPages(
            @Path("version") String version,
            @Path("sectionId") String sectionId,
            @PartMap OneNotePartsMap partMap,
            Callback<Envelope<Page>> callback
    );

    /**
     * Deletes the specified page
     *
     * @param version
     * @param pageId
     * @param callback
     */
    @DELETE("/{version}/me/notes/pages/{pageId}")
    void deletePage(
            @Path("version") String version,
            @Path("pageId") String pageId,
            Callback<Envelope<Page>> callback
    );

    /**
     * Appends new content to an existing page
     * specified by page id
     *
     * Note: This passes a blank Accept-Encoding header to
     * work around a known issue with the PATCH on this OneNote API
     *
     * @param encoding
     * @param version
     * @param pageId
     * @param body
     * @param callback
     */
    @PATCH("/{version}/me/notes/pages/{pageId}/content")
    void patchPage(
            @Header("Accept-Encoding") String encoding,
            @Path("version") String version,
            @Path("pageId") String pageId,
            @Body TypedString body,
            Callback<Envelope<Page>> callback
    );
}