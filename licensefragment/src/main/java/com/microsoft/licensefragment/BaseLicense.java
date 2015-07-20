package com.microsoft.licensefragment;

import android.content.Context;
import android.content.res.Resources;

public class BaseLicense {

    public final String mName, mDescription, mUrl, mNotes;

    BaseLicense(String name,
                String description,
                String url,
                String notes) {
        this.mName = name;
        this.mDescription = description;
        this.mUrl = url;
        this.mNotes = notes;
    }

    private static final int INDEX_NAME = 0;
    private static final int INDEX_DESC = 1;
    private static final int INDEX_URL = 2;
    private static final int INDEX_NOTES = 3;

    BaseLicense(Context context, int arrayId) {
        Resources resources = context.getResources();
        String[] params = resources.getStringArray(arrayId);
        this.mName = params[INDEX_NAME];
        this.mDescription = params[INDEX_DESC];
        this.mUrl = params[INDEX_URL];
        this.mNotes = params[INDEX_NOTES];
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BaseLicense{");
        sb.append("mName='").append(mName).append('\'');
        sb.append(", mDescription='").append(mDescription).append('\'');
        sb.append(", mUrl='").append(mUrl).append('\'');
        sb.append(", mNotes='").append(mNotes).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseLicense that = (BaseLicense) o;

        if (mName != null ? !mName.equals(that.mName) : that.mName != null) return false;
        if (mDescription != null ? !mDescription.equals(that.mDescription) : that.mDescription != null)
            return false;
        if (mUrl != null ? !mUrl.equals(that.mUrl) : that.mUrl != null) return false;
        return !(mNotes != null ? !mNotes.equals(that.mNotes) : that.mNotes != null);

    }

    @Override
    public int hashCode() {
        int result = mName != null ? mName.hashCode() : 0;
        result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
        result = 31 * result + (mUrl != null ? mUrl.hashCode() : 0);
        result = 31 * result + (mNotes != null ? mNotes.hashCode() : 0);
        return result;
    }
}
