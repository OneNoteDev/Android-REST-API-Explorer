/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.onenotevos;

public class Links {

    // todo Is this a bug??
    public static class Url {
        public String href;
    }

    public static class OneNoteClientUrl extends Url {}

    public static class OneNoteWebUrl extends Url {}

    public OneNoteClientUrl oneNoteClientUrl;
    public OneNoteWebUrl oneNoteWebUrl;
}
