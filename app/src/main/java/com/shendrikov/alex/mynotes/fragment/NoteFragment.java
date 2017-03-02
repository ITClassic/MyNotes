package com.shendrikov.alex.mynotes.fragment;



import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shendrikov.alex.mynotes.R;
import com.shendrikov.alex.mynotes.adapters.NotesFragmentPagerAdapter;
import com.shendrikov.alex.mynotes.db.MyNotesContract;
import com.shendrikov.alex.mynotes.model.Person;
import com.tjeannin.provigen.ProviGenBaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 26.02.2017.
 */

public class NoteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    @BindView(R.id.name_edit_text)
    protected EditText mNameEditText;
    @BindView(R.id.surname_edit_text)
    protected EditText mSurNameEditText;

    public static NoteFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(ProviGenBaseContract._ID, id);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_note, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().
                getSupportLoaderManager().
                initLoader((int)getArguments().getLong(ProviGenBaseContract._ID), null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        long noteId = getArguments().getLong(ProviGenBaseContract._ID);
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
        mNameEditText.setText(person.getName());
        mSurNameEditText.setText(person.getSurName());
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
