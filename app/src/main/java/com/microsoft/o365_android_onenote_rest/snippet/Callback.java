package com.microsoft.o365_android_onenote_rest.snippet;

import java.util.Map;

public interface Callback<T> extends retrofit.Callback<T> {

    Map<String, String> getParams();
}