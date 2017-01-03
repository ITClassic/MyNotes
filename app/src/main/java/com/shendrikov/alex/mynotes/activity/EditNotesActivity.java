package com.shendrikov.alex.mynotes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.shendrikov.alex.mynotes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 02.01.2017.
 */

public class EditNotesActivity extends AppCompatActivity {

    private static final String SHARE_TYPE = "text/plain";

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_note_item_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_share:
                shareAction();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

     //Call to update the share intent

    private void shareAction() {

        Intent shareIntent = new Intent();

        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, prepareNotForSharing());
        shareIntent.setType(SHARE_TYPE);

        startActivity(shareIntent);
   }

    private String prepareNotForSharing() {
        String name = mNameEditText.getText().toString();
        String surName = mSurNameEditText.getText().toString();

        return getString(R.string.sharing_template, name, surName);
    }
}
