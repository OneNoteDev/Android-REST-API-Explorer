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