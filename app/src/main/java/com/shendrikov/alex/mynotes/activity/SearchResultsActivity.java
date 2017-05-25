package com.shendrikov.alex.mynotes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.shendrikov.alex.mynotes.R;
import com.shendrikov.alex.mynotes.adapters.SearchResultsAdapter;
import com.shendrikov.alex.mynotes.model.Person;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11.01.2017.
 */

public class SearchResultsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOG_TAG = SearchResultsActivity.class.getSimpleName();

    @BindView(R.id.search_results_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;

    private List<Person> mSearchedList;

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
        SearchResultsAdapter searchResultsAdapter = new SearchResultsAdapter();
        mRecyclerView.setAdapter(searchResultsAdapter);
        searchResultsAdapter.setDataSource(this, mSearchedList);
        searchResultsAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mSearchedList = intent.getParcelableArrayListExtra("filteredList");
        }
    }

    @Override
    public void onClick(View view) {
        SearchResultsAdapter.PersonViewHolder holder =
                (SearchResultsAdapter.PersonViewHolder) mRecyclerView.findContainingViewHolder(view);
        if (holder == null) return;
        startActivity(EditNotesActivity.newInstance(this, holder.getPerson().getId()));
        Log.d(LOG_TAG, "Clicked on item with id = " + holder.getPerson().getId());
    }
}
