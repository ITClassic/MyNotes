package com.shendrikov.alex.mynotes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.shendrikov.alex.mynotes.fragment.NoteFragment;
import com.shendrikov.alex.mynotes.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 26.02.2017.
 */

public class NotesFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static final String LOG_TAG = NotesFragmentPagerAdapter.class.getSimpleName();

    private List<Person> mDataSource = null;
    private int mIndex;
    private int mCount;

    public NotesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        long id = mDataSource.get(mIndex + position).getId();

        Log.d(LOG_TAG, "getItem(): id = " + id);
        return NoteFragment.newInstance(id);
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "getCount(): mCount = " + mCount);
        return mDataSource == null ? 0 : mCount;
    }

    public void setDataSource(List<Person> dataSource, long id) {
        List<Long> idList = new ArrayList<>(); // initialize the array for storing id
        mDataSource = dataSource;

        for (int i = 0; i < dataSource.size(); i++) {
            idList.add(dataSource.get(i).getId()); // store id to array
        }

        mIndex = idList.indexOf(id); // define the index of selected id
        mCount = idList.size() - mIndex; // define the number of fragments to show by adapter
        notifyDataSetChanged();
        Log.d(LOG_TAG, "index = " + mIndex + ", count = " + mCount);
    }
}
