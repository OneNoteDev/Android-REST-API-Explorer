/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.o365_android_onenote_rest.snippet;

import java.util.Map;

public interface Callback<T> extends retrofit.Callback<T> {

    Map<String, String> getParams();
}
