package com.microsoft.licensefragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class LicenseFragment extends ListFragment {

    protected abstract List<BaseLicense> getLicenses();

    protected static List<BaseLicense> getLicenses(final Context context, final int... ids) {
        return new ArrayList<BaseLicense>() {{
            for (int id : ids) {
                add(new BaseLicense(context, id));
            }
        }};
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new LicenseAdapter());
    }

    private class LicenseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return getLicenses().size();
        }

        @Override
        public BaseLicense getItem(int position) {
            return getLicenses().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.layout_license, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.license_name);
            name.setText(getItem(position).mName);
            TextView desc = (TextView) convertView.findViewById(R.id.license_desc);
            desc.setText(getItem(position).mDescription);

            return convertView;
        }
    }
}
