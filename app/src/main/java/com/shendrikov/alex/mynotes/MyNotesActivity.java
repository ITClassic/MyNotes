package com.shendrikov.alex.mynotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shendrikov.alex.mynotes.adapters.MyAdapter;
import com.shendrikov.alex.mynotes.model.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNotesActivity extends AppCompatActivity {

    @BindView(R.id.my_recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.my_toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.fab)
    protected FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // use a linear layout manager (vertical)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, mRecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Create and initialize personList
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Person person = new Person();
            person.setName("Name " + (i + 1));
            person.setSurName("SurName " + (i + 1));
            personList.add(person);
        }

        // specify an adapter (see also next example)
        MyAdapter myAdapter = new MyAdapter(personList);
        mRecyclerView.setAdapter(myAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "FAB is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_note_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

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
}
