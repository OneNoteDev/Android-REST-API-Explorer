/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.onenoteapi.service;

import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

public class GsonDateTime {

    public static GsonBuilder getOneNoteBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeDeserializer());
        return gsonBuilder;
    }

}