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
        return null != getItem(position).mDesc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == mContext) {
            mContext = parent.getContext();
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        AbstractSnippet<?, ?> clickedSnippet = getItem(position);
        boolean isSegment = (null == clickedSnippet.mDesc);

        final int id = isSegment ? R.layout.list_segment : R.layout.list_element;
        if (null == convertView || isWrongViewType(isSegment, convertView)) {
            convertView = mLayoutInflater.inflate(id, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.txt_snippet_name);
        name.setText(clickedSnippet.mName);

        if (!isSegment) {
            // TODO determine beta status??
            View betaIndicator = convertView.findViewById(R.id.beta_indicator);
            betaIndicator.setVisibility(View.GONE);
        }

        return convertView;
    }

    private boolean isWrongViewType(boolean isSegment, View convertView) {
        View v = convertView.findViewById(R.id.beta_indicator);
        return !isSegment && null == v || (isSegment && null != v);
    }

}
