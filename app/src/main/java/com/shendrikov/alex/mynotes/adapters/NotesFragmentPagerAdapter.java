package com.shendrikov.alex.mynotes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.shendrikov.alex.mynotes.R;
import com.shendrikov.alex.mynotes.fragment.NoteFragment;
import com.shendrikov.alex.mynotes.model.Person;
import com.tjeannin.provigen.ProviGenBaseContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 26.02.2017.
 */

public class NotesFragmentPagerAdapter extends FragmentStatePagerAdapter {

//    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private List<NoteFragment> fragmentsList = new ArrayList<>();
    private NoteFragment currentFragment;
    private long currentId;

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

        NoteFragment noteFragment = NoteFragment.newInstance(id);
        fragmentsList.add(position, noteFragment);
        return noteFragment;
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "getCount(): mCount = " + mCount);
        return mDataSource == null ? 0 : mCount;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentFragment = fragmentsList.get(position);
        currentId = currentFragment.getArguments().getLong(ProviGenBaseContract._ID);

        Log.d(LOG_TAG, "setPrimaryItem(): currentFragment id  = " + currentFragment.getArguments().get(ProviGenBaseContract._ID));
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public long getCurrentId() {
        return currentId;
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
