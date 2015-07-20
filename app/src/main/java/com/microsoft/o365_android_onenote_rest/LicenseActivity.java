package com.microsoft.o365_android_onenote_rest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.microsoft.licensefragment.BaseLicense;

import java.util.List;

public class LicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
    }

    public static class LicenseFragment extends com.microsoft.licensefragment.LicenseFragment {

        @Override
        protected List<BaseLicense> getLicenses() {
            return getLicenses(getActivity(), R.array.appcompat, R.array.live_auth);
        }
    }
}
