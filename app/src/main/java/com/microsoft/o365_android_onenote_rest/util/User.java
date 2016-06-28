/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.o365_android_onenote_rest.util;

public class User {

    private static final String PREF_MSA = "PREF_MSA";
    private static final String PREF_ORG = "PREF_ORG";

    static void isMsa(boolean msa) {
        set(PREF_MSA, msa);
    }

    static void isOrg(boolean org) {
        set(PREF_ORG, org);
    }

    public static boolean isMsa() {
        return is(PREF_MSA);
    }

    public static boolean isOrg() {
        return is(PREF_ORG);
    }

    private static boolean is(String which) {
        return SharedPrefsUtil.getSharedPreferences().getBoolean(which, false);
    }

    private static void set(String which, boolean state) {
        SharedPrefsUtil
                .getSharedPreferences()
                .edit()
                .putBoolean(which, state)
                .commit();
    }
}
