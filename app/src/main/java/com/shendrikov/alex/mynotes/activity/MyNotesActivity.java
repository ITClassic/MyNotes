package com.shendrikov.alex.mynotes.activity;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shendrikov.alex.mynotes.R;
import com.shendrikov.alex.mynotes.adapters.MyAdapter;
import com.shendrikov.alex.mynotes.db.MyNotesContract;
import com.shendrikov.alex.mynotes.model.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shendrikov.alex.mynotes.R.id.fab;

public class MyNotesActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener,
                    SearchView.OnQueryTextListener{

    public static final String LOG_TAG = MyNotesActivity.class.getSimpleName();

    @BindView(R.id.id_my_recycler_view)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.my_toolbar)
    protected Toolbar mToolbar;
    @BindView(fab)
    protected FloatingActionButton mFab;

    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setTitle(R.string.app_name);

        // use a linear layout manager (vertical)
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, mRecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        getSupportLoaderManager().initLoader(R.id.notes_loader, null, this);
    }

    @Override
    public void startActivity(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            intent.
                    putParcelableArrayListExtra("filteredList",
                            (ArrayList<? extends Parcelable>) mMyAdapter.getFilteredList());
        }
        super.startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void onFABClicked() {
        startActivity(CreateNotesActivity.newInstance(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_note_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings option clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_help:
                Toast.makeText(this, "Help option clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader:");
        return new CursorLoader(
                this,
                MyNotesContract.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<Person> dataSource = new ArrayList<>();
        while (cursor.moveToNext()) {
            dataSource.add(new Person(cursor));
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        mMyAdapter.setDataSource(this, this, dataSource);
        mMyAdapter.setOnItemClickListener(this);

        Log.d(LOG_TAG, "onLoadFinished(): " + loader.hashCode());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, "onLoaderReset(): " + loader.hashCode());
    }

    @Override
    public void onClick(View view) {
        MyAdapter.PersonViewHolder holder =
                (MyAdapter.PersonViewHolder) mRecyclerView.findContainingViewHolder(view);
        if (holder == null) return;
        startActivity(EditNotesActivity.newInstance(this, holder.getPerson().getId()));
        Log.d(LOG_TAG, "Clicked on item with id = " + holder.getPerson().getId());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(LOG_TAG, "onQueryTextSubmit(): ");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(LOG_TAG, "onQueryTextChange(): " + newText);
        mMyAdapter.getFilter().filter(newText);
        return false;
    }
}
