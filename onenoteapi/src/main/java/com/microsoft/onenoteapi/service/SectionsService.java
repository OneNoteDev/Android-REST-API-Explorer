/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.onenoteapi.service;

import com.microsoft.onenotevos.Envelope;
import com.microsoft.onenotevos.Section;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedString;

public interface SectionsService {

    /**
     * Performs GET requests against the sections resource.
     *
     * @param filter
     * @param order
     * @param select
     * @param top
     * @param skip
     * @param search
     * @param callback
     */
    @GET("/{version}/me/notes/sections")
    void getSections(
            @Path("version") String version,
            @Query("filter") String filter,
            @Query("orderby") String order,
            @Query("select") String select,
            @Query("top") Integer top,
            @Query("skip") Integer skip,
            @Query("search") String search,
            Callback<Envelope<Section>> callback
    );

    /**
     * Get a section specified by id
     *
     * @param version
     * @param id
     * @param callback
     */
    @GET("/{version}/me/notes/sections/{id}")
    void getSectionById(
            @Path("version") String version,
            @Path("id") String id,
            Callback<Envelope<Section>> callback
    );

    @GET("/{version}/me/notes/notebooks/{notebookId}/sections")
    void getNotebookSections(
            @Path("version") String version,
            @Path("notebookId") String Id,
            @Query("filter") String filter,
            @Query("orderby") String order,
            @Query("select") String select,
            @Query("top") Integer top,
            @Query("skip") Integer skip,
            @Query("search") String search,
            Callback<Envelope<Section>> callback
    );

    /**
     * POST to the sections resource of a notebook
     *
     * @param contentTypeHeader
     * @param version
     * @param id
     * @param content
     * @param callback
     */
    @POST("/{version}/me/notes/notebooks/{id}/sections")
    void postSection(
            @Path("version") String version,
            @Header("Content-type") String contentTypeHeader,
            @Path("id") String id,
            @Body TypedString content,
            Callback<Envelope<Section>> callback
    );

    /**
     * POST to the sections resource of a section group
     *
     * @param contentTypeHeader
     * @param version
     * @param id
     * @param content
     * @param callback
     */
    @POST("/{version}/me/notes/sectiongroups/{id}/sections")
    void postSectionInSectionGroup(
            @Path("version") String version,
            @Header("Content-type") String contentTypeHeader,
            @Path("id") String id,
            @Body TypedString content,
            Callback<Envelope<Section>> callback
    );

    /**
     * DELETE a section
     *
     * @param version
     * @param sectionId
     * @param callback
     */
    @DELETE("/{version}/me/notes/sections/{sectionId}")
    void deleteSection(
            @Path("version") String version,
            @Path("sectionId") String sectionId,
            Callback<Envelope> callback
    );

}
