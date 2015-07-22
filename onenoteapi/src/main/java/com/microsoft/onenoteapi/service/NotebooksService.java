/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/

package com.microsoft.onenoteapi.service;

import com.microsoft.onenotevos.Envelope;
import com.microsoft.onenotevos.Notebook;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedString;

public interface NotebooksService {

    /**
     * Gets all of the user's notebooks
     *
     * @param filter
     * @param order
     * @param select
     * @param expand
     * @param top
     * @param skip
     * @param callback
     */
    @GET("/{version}/me/notes/notebooks")
    void getNotebooks(
            @Path("version") String version,
            @Query("$filter") String filter,
            @Query("$orderby") String order,
            @Query("$select") String select,
            @Query("$expand") String expand,
            @Query("$top") Integer top,
            @Query("$skip") Integer skip,
            Callback<Envelope<Notebook>> callback
    );

    /**
     * Gets all of the notebooks owned by other users and shared
     * with the signed in user
     *
     * @param version
     * @param filter
     * @param order
     * @param select
     * @param expand
     * @param top
     * @param skip
     * @param callback
     */
    @GET("/{version}/me/notes/notebooks")
    void getSharedNotebooks(
            @Path("version") String version,
            @Query("$filter") String filter,
            @Query("$orderby") String order,
            @Query("$select") String select,
            @Query("$expand") String expand,
            @Query("$top") Integer top,
            @Query("$skip") Integer skip,
            Callback<Envelope<Notebook>> callback
    );

    /**
     * Gets a notebook by notebook id
     *
     * @param version
     * @param id
     * @param callback
     */
    @GET("/{version}/me/notes/notebooks/{id}")
    void getNotebookById(
            @Path("version") String version,
            @Path("id") String id,
            @Query("$select") String select,
            Callback<Envelope<Notebook>> callback
    );

    /**
     * Creates a new notebook under the user's OneDrive for Business notebooks folder
     *
     * @param version
     * @param contentTypeHeader
     * @param body
     * @param callback
     */
    @POST("/{version}/me/notes/notebooks")
    void postNotebook(
            @Path("version") String version,
            @Header("Content-type") String contentTypeHeader,
            @Body TypedString body,
            Callback<Envelope<Notebook>> callback
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