package com.microsoft.onenoteapi.service;

import java.util.HashMap;

import retrofit.mime.TypedInput;


public class OneNotePartsMap extends HashMap<String, TypedInput> {

    private static final String MANDATORY_PART_NAME = "Presentation";

    public OneNotePartsMap(TypedInput initValue) {
        put(MANDATORY_PART_NAME, initValue);
    }

}