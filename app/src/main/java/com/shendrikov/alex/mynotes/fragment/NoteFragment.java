package com.shendrikov.alex.mynotes.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shendrikov.alex.mynotes.R;
import com.shendrikov.alex.mynotes.db.MyNotesContract;
import com.shendrikov.alex.mynotes.model.Person;
import com.tjeannin.provigen.ProviGenBaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 26.02.2017.
 */

public class NoteFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        View.OnClickListener {

    public static final String LOG_TAG = NoteFragment.class.getSimpleName();

    @BindView(R.id.id_name_edit_text)
    protected EditText mNameEditText;
    @BindView(R.id.id_surname_edit_text)
    protected EditText mSurNameEditText;
    @BindView(R.id.id_button_save)
    Button mSaveButton;

    public OnSomeEventListener mOnSomeEventListener;

    private long mId;

    public static NoteFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(ProviGenBaseContract._ID, id);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnSomeEventListener = (OnSomeEventListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_note, container, false);
        Log.d(LOG_TAG, "onCreateView(): ");

        ButterKnife.bind(this, view);
        mSaveButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(LOG_TAG, "onViewCreated():");
        getActivity().
                getSupportLoaderManager().
                initLoader((int)getArguments().getLong(ProviGenBaseContract._ID), null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        long noteId = getArguments().getLong(ProviGenBaseContract._ID);

        Log.d(LOG_TAG, "onCreateLoader():");
        return new CursorLoader(
                getActivity(),
                Uri.withAppendedPath(MyNotesContract.CONTENT_URI, String.valueOf(noteId)),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor == null || !cursor.moveToFirst()) return;
        Person person = new Person(cursor);
        mId = person.getId();
        mNameEditText.setText(person.getName());
        mSurNameEditText.setText(person.getSurName());

        Log.d(LOG_TAG, "onLoadFinished(): mNameEditText = " + mNameEditText.getText() +
                ", mSurNameEditText = " + mSurNameEditText.getText() +
                ", mId = " + mId);
    }

    @Override
    public void onLoaderReset(Loader loader) {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_button_save:
                Log.d(LOG_TAG, "save():");
                mOnSomeEventListener.saveChanges();
                break;
                default:
                    break;
        }
    }
}
