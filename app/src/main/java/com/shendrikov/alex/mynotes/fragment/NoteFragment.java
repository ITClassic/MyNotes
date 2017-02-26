package com.shendrikov.alex.mynotes.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shendrikov.alex.mynotes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 26.02.2017.
 */

public class NoteFragment extends Fragment {

    @BindView(R.id.name_edit_text)
    protected EditText mNameEditText;
    @BindView(R.id.surname_edit_text)
    protected EditText mSurNameEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_note, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
