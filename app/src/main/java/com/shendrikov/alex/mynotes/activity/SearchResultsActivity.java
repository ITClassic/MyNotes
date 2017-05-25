package com.shendrikov.alex.mynotes.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.shendrikov.alex.mynotes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11.01.2017.
 */

public class SearchResultsActivity extends AppCompatActivity {

    @BindView(R.id.search_results_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setTitle(R.string.app_name);

        handleIntent(getIntent());

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, mRecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

        }
    }
}
