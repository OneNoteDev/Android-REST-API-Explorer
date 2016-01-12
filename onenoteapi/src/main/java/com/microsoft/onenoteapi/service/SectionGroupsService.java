/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.onenoteapi.service;

import com.microsoft.onenotevos.Envelope;
import com.microsoft.onenotevos.SectionGroup;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedString;

public interface SectionGroupsService {

    /**
     * @param version
     * @param filter
     * @param order
     * @param select
     * @param top
     * @param skip
     * @param search
     * @param callback
     */
    @GET("/{version}/me/notes/sectionGroups")
    void getSectionGroups(
            @Path("version") String version,
            @Query("filter") String filter,
            @Query("orderby") String order,
            @Query("select") String select,
            @Query("top") Integer top,
            @Query("skip") Integer skip,
            @Query("search") String search,
            Callback<Envelope<SectionGroup>> callback
    );

    @GET("/{version}/me/notes/notebooks/{id}/sectionGroups")
    void getSectionGroupsForNotebook(
            @Path("version") String version,
            @Path("id") String id,
            @Query("filter") String filter,
            @Query("orderby") String order,
            @Query("select") String select,
            @Query("top") Integer top,
            @Query("skip") Integer skip,
            @Query("search") String search,
            Callback<Envelope<SectionGroup>> callback

    );

    /**
     * POST to the sectiongroups resource of a notebook
     *
     * @param contentTypeHeader
     * @param version
     * @param id
     * @param content
     * @param callback
     */
    @POST("/{version}/me/notes/notebooks/{id}/sectiongroups")
    void postSectionGroupInNotebook(
            @Path("version") String version,
            @Header("Content-type") String contentTypeHeader,
            @Path("id") String id,
            @Body TypedString content,
            Callback<Envelope<SectionGroup>> callback
    );


    /**
     * POST to the sectiongroups resource of a section group
     *
     * @param contentTypeHeader
     * @param version
     * @param id
     * @param content
     * @param callback
     */
    @POST("/{version}/me/notes/sectiongroups/{id}/sectiongroups")
    void postSectionGroupInSectionGroup(
            @Path("version") String version,
            @Header("Content-type") String contentTypeHeader,
            @Path("id") String id,
            @Body TypedString content,
            Callback<Envelope<SectionGroup>> callback
    );
}