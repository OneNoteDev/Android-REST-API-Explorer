/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.onenoteapi.service;

import java.util.HashMap;

import retrofit.mime.TypedInput;


public class OneNotePartsMap extends HashMap<String, TypedInput> {

    private static final String MANDATORY_PART_NAME = "Presentation";

    public OneNotePartsMap(TypedInput initValue) {
        put(MANDATORY_PART_NAME, initValue);
    }

}
