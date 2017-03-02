package com.shendrikov.alex.mynotes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shendrikov.alex.mynotes.fragment.NoteFragment;
import com.shendrikov.alex.mynotes.model.Person;

import java.util.List;

/**
 * Created by Alex on 26.02.2017.
 */

public class NotesFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Person> mDataSource = null;

    public NotesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        long id = mDataSource.get(position).getId();
        return NoteFragment.newInstance(id);
    }

    @Override
    public int getCount() {
        return mDataSource == null ? 0 : mDataSource.size();
    }

    public void setDataSource(List<Person> dataSource) {
        mDataSource = dataSource;
        notifyDataSetChanged();
    }
}
