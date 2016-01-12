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