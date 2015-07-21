/*
*  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. See full license at the bottom of this file.
*/
package com.microsoft.onenoteapi.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PatchCommandSerializer implements JsonSerializer<PatchCommand> {

    @Override
    public JsonElement serialize(PatchCommand patchCommand,
                                 Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("Target", new JsonPrimitive(patchCommand.getTarget()));
        result.add("Action", new JsonPrimitive(patchCommand.getAction()));
        result.add("Content", new JsonPrimitive(patchCommand.getContent()));
        result.add("Position", new JsonPrimitive(patchCommand.getPosition()));

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(result);

        return jsonArray;
    }
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