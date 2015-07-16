package com.microsoft.onenotevos;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public abstract class BaseVO {

    public Boolean isDefault;

    public Boolean isShared;

    public DateTime createdTime;

    public DateTime lastModifiedTime;

    public Links links;

    public String createdBy;

    public String createdByAppId;

    public String id;

    public String lastModifiedBy;

    public String name;

    @SerializedName("@odata.context")
    public String odataContext;

    public String self;

    public String userRole;
}
