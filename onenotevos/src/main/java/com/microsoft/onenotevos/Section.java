/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.onenotevos;

import com.google.gson.annotations.SerializedName;

public class Section extends BaseVO {

    public String pagesUrl;
    public Notebook parentNotebook;
    public SectionGroup parentSectionGroup;

    @SerializedName("parentSectionGroup@odata.context")
    public String parentSectionGroup_odata_context;

    @SerializedName("parentNotebook@odata.context")
    public String parentNotebook_odata_context;

}
