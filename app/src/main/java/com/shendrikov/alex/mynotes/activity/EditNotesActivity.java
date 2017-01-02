package com.shendrikov.alex.mynotes.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.shendrikov.alex.mynotes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 02.01.2017.
 */

public class EditNotesActivity extends AppCompatActivity {

    @BindView(R.id.name_edit_text)
    protected EditText mNameEditText;
    @BindView(R.id.surname_edit_text)
    protected EditText mSurNameEditText;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_notes);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
