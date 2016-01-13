/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.onenoteapi.service;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public class PatchCommand implements JsonSerializer<PatchCommand> {

    @SerializedName("Target")
    public String mTarget;
    @SerializedName("Action")
    public String mAction;
    @SerializedName("Content")
    public String mContent;
    @SerializedName("Position")
    public String mPosition;

    @Override
    public JsonElement serialize(PatchCommand patchCommand,
                                 Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonElement result = new GsonBuilder().create().toJsonTree(patchCommand);
        Log.i(getClass().getSimpleName(), result.toString());
        return result;
    }
}
