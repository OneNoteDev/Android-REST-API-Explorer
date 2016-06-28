/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package com.microsoft.o365_android_onenote_rest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microsoft.o365_android_onenote_rest.snippet.AbstractSnippet;
import com.microsoft.o365_android_onenote_rest.snippet.SnippetContent;

public class SnippetListAdapter extends BaseAdapter {

    public static final String UNAVAILABLE = "unavailable";
    public static final String BETA = "beta";
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    @Override
    public int getCount() {
        return SnippetContent.ITEMS.size();
    }

    @Override
    public AbstractSnippet<?, ?> getItem(int position) {
        return (AbstractSnippet) SnippetContent.ITEMS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean isEnabled(int position) {
        return null != getItem(position).getDescription()
                && !hasStatus(getItem(position), UNAVAILABLE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == mContext) {
            mContext = parent.getContext();
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        AbstractSnippet<?, ?> clickedSnippet = getItem(position);
        boolean isSegment = (null == clickedSnippet.getDescription());

        final int id = isSegment ? R.layout.list_segment : R.layout.list_element;
        if (null == convertView || isWrongViewType(isSegment, convertView)) {
            convertView = mLayoutInflater.inflate(id, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.txt_snippet_name);
        name.setText(clickedSnippet.getName());

        if (!isSegment) {
            TextView betaIndicator = (TextView) convertView.findViewById(R.id.beta_indicator);
            if (hasStatus(clickedSnippet, UNAVAILABLE)) {
                betaIndicator.setText(mContext.getString(R.string.unavailable).toUpperCase());
            }
            if (hasStatus(clickedSnippet, BETA)) {
                betaIndicator.setText(R.string.beta);
                betaIndicator.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

    private boolean hasStatus(AbstractSnippet<?, ?> clickedSnippet, String unavailable) {
        return clickedSnippet.getVersion().equalsIgnoreCase(unavailable);
    }

    private boolean isWrongViewType(boolean isSegment, View convertView) {
        View v = convertView.findViewById(R.id.beta_indicator);
        return !isSegment && null == v || (isSegment && null != v);
    }

}
