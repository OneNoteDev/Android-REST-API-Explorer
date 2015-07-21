/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
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
     * POST to the sections resource
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
            Callback<Envelope> callback
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