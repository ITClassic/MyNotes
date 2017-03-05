package com.shendrikov.alex.mynotes.activity;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.shendrikov.alex.mynotes.R;
import com.shendrikov.alex.mynotes.adapters.NotesFragmentPagerAdapter;
import com.shendrikov.alex.mynotes.db.MyNotesContract;
import com.shendrikov.alex.mynotes.model.Person;
import com.shendrikov.alex.mynotes.util.DateUtil;
import com.tjeannin.provigen.ProviGenBaseContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 02.01.2017.
 */

public class EditNotesActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String SHARE_TYPE = "text/plain";

//    @BindView(R.id.name_edit_text)
//    protected EditText mNameEditText;
//    @BindView(R.id.surname_edit_text)
//    protected EditText mSurNameEditText;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.view_pager)
    protected ViewPager mViewPager;

    private long mId = -1;
//    private String mOriginalName = "";
//    private String mOriginalSurName = "";
    private NotesFragmentPagerAdapter mViewPagerAdapter = null;

    @NonNull
    public static Intent newInstance(@NonNull Context context) {
        return new Intent(context, EditNotesActivity.class);
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
        setContentView(R.layout.activity_edit_my_notes);
        ButterKnife.bind(this);
        checkIntentByExtraId();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPagerAdapter = new NotesFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    private void checkIntentByExtraId() {
        Intent intent = getIntent();
        if (!intent.hasExtra(ProviGenBaseContract._ID)) return;
        mId = intent.getLongExtra(ProviGenBaseContract._ID, mId);
        if (mId == -1) return;
        getLoaderManager().initLoader(R.id.edit_note_loader, null, this);
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
//        if (mOriginalName.equals(mNameEditText.getText())
//                && mOriginalSurName.equals(mSurNameEditText.getText())) {
//            runnable.run();
//            return;
//        }
        showAreYouSureAlert(runnable);
    }

    private void showAreYouSureAlert(final Runnable runnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.are_you_sure_alert_dialog_title);
        builder.setMessage(R.string.are_you_sure_alert_do_you_want_to_save_changes);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes, (dialogInterface, id) -> {
//            save();
            runnable.run();
        });
        builder.setNegativeButton(R.string.no, ((dialogInterface, id) -> runnable.run()));
        builder.show();
    }


    private void shareAction() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.putExtra(Intent.EXTRA_TEXT, prepareNotForSharing());
        shareIntent.setType(SHARE_TYPE);
        startActivity(shareIntent);
   }


    /*
    @OnClick(R.id.button_save)
    public void onSaveButtonClick() {
        save();
        finish();
    }*/

    private void save() {
        if (isNoteUpdatable()) {
            updatePerson();
        } else insertPerson();
    }

    private void updatePerson() {
        final ContentValues values = new ContentValues();
//        values.put(MyNotesContract.NAME_COLUMN, mNameEditText.getText().toString());
//        values.put(MyNotesContract.SURNAME_COLUMN, mSurNameEditText.getText().toString());
        getContentResolver().update(
                Uri.withAppendedPath(MyNotesContract.CONTENT_URI, String.valueOf(mId)),
                values,
                null,
                null);
    }


    private void insertPerson() {
        ContentValues contentValues = new ContentValues();
    //    contentValues.put(MyNotesContract.NAME_COLUMN, mNameEditText.getText().toString());
    //    contentValues.put(MyNotesContract.SURNAME_COLUMN, mSurNameEditText.getText().toString());
        contentValues.put(MyNotesContract.TIME_COLUMN, DateUtil.getDate());
        getContentResolver().insert(MyNotesContract.CONTENT_URI, contentValues);
    }

    /*
    private String prepareNotForSharing() {
        String name = mNameEditText.getText().toString();
        String surName = mSurNameEditText.getText().toString();
        return getString(R.string.sharing_template, "","");
    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

        return new CursorLoader(this,
                MyNotesContract.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<Person> dataSource = new ArrayList<>();
        while(cursor.moveToNext()) {
            Person person = new Person(cursor);
            dataSource.add(person);
           //mNameEditText.setText(person.getName());
          //  mSurNameEditText.setText(person.getSurName());
       //     mOriginalName = person.getName();
        //    mOriginalSurName = person.getSurName();
        }

        mViewPagerAdapter.setDataSource(dataSource);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}

    @Override
    public void onBackPressed() {
        safetyFinish(EditNotesActivity.super::onBackPressed);
    }
}
