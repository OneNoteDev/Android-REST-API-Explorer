package com.microsoft.o365_android_onenote_rest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.microsoft.o365_android_onenote_rest.inject.AppModule;


public class SnippetListActivity extends BaseActivity
        implements SnippetListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet_list);

        if (findViewById(R.id.snippet_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((SnippetListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.snippet_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(int position) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(SnippetDetailFragment.ARG_ITEM_ID, position);
            SnippetDetailFragment fragment = new SnippetDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.snippet_detail_container, fragment)
                    .commit();
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, SnippetDetailActivity.class);
            detailIntent.putExtra(SnippetDetailFragment.ARG_ITEM_ID, position);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onDisconnectClicked() {
        finish();
        // drop the application shared preferences to clear any old auth tokens
        getSharedPreferences(AppModule.PREFS, Context.MODE_PRIVATE)
                .edit() // get the editor
                .clear() // clear it
                .apply(); // asynchronously apply
        mAuthenticationManager.disconnect();
        Intent login = new Intent(this, SignInActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
    }
}
