package com.shendrikov.alex.mynotes.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.shendrikov.alex.mynotes.R;
import com.shendrikov.alex.mynotes.db.MyNotesContract;
import com.shendrikov.alex.mynotes.util.DateUtil;
import com.tjeannin.provigen.ProviGenBaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 430 on 27.02.2017.
 */

public class CreateNotesActivity extends AppCompatActivity {

    public static final String LOG_TAG = CreateNotesActivity.class.getSimpleName();

    private static final String SHARE_TYPE = "text/plain";

    @BindView(R.id.id_name_edit_text)
    protected EditText mNameEditText;
    @BindView(R.id.id_surname_edit_text)
    protected EditText mSurNameEditText;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    private long mId = -1;

    @NonNull
    public static Intent newInstance(@NonNull Context context) {
        return new Intent(context, CreateNotesActivity.class);
    }

    @NonNull
    public static Intent newInstance(@NonNull Context context, long id) {
        Intent intent = newInstance(context);
        intent.putExtra(ProviGenBaseContract._ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_my_notes);
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
                safetyFinish(this::finish);
                break;
            case R.id.menu_item_action_share:
                shareAction();
                break;
            case R.id.menu_item_action_delete:
                deleteNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        if (isNoteUpdatable()) {
            getContentResolver().delete(
                    Uri.withAppendedPath(MyNotesContract.CONTENT_URI, String.valueOf(mId)),
                    null,
                    null);
        }
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ProviGenBaseContract._ID, mId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mId = savedInstanceState.getLong(ProviGenBaseContract._ID);
    }

    private boolean isNoteUpdatable() {
        return mId != -1;
    }

    private void safetyFinish(Runnable runnable) {
        String originalName = "";
        String originalSurName = "";

        if (originalName.equals(mNameEditText.getText().toString())
                && originalSurName.equals(mSurNameEditText.getText().toString())) {
            runnable.run();
            return;
        }
        showSaveAlert(runnable);
    }

    private void showSaveAlert(final Runnable runnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.save_changes_alert_dialog_title);
        builder.setMessage(R.string.all_changes_will_be_saved);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.save, (dialogInterface, id) -> {
            save();
            runnable.run();
        });
        builder.setNegativeButton(R.string.cancel, ((dialogInterface, id) -> runnable.run()));
        builder.show();
    }

    private void shareAction() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, prepareNotForSharing());
        shareIntent.setType(SHARE_TYPE);
        startActivity(shareIntent);
    }

    @OnClick(R.id.id_button_save)
    public void onSaveButtonClick() {
        save();
        finish();
    }

    private void save() {
        insertPerson();
    }

    private void insertPerson() {
        ContentValues contentValues = new ContentValues();

        if (TextUtils.isEmpty(mNameEditText.getText().toString()) ||
                TextUtils.isEmpty(mSurNameEditText.getText().toString())) {
            Log.d(LOG_TAG, "insertPerson(): Field is empty. Fill all fields!");
            return;
        } else {
            contentValues.put(MyNotesContract.NAME_COLUMN, mNameEditText.getText().toString());
            contentValues.put(MyNotesContract.SURNAME_COLUMN, mSurNameEditText.getText().toString());
            contentValues.put(MyNotesContract.TIME_COLUMN, DateUtil.getDate());
            getContentResolver().insert(MyNotesContract.CONTENT_URI, contentValues);
        }
    }

    private String prepareNotForSharing() {
        String name = mNameEditText.getText().toString();
        String surName = mSurNameEditText.getText().toString();
        return getString(R.string.sharing_template, name, surName);
    }

    @Override
    public void onBackPressed() {
        safetyFinish(CreateNotesActivity.super::onBackPressed);
    }
}
