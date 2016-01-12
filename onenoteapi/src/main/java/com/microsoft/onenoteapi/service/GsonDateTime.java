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