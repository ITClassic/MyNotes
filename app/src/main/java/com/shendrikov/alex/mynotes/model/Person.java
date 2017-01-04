package com.shendrikov.alex.mynotes.model;

import android.database.Cursor;

import com.shendrikov.alex.mynotes.db.MyNotesContract;

import java.util.ArrayList;

/**
 * Created by Alex on 17.12.2016.
 */

public class Person {

    private String mName;
    private String mSurName;

    public Person(){}

    public Person(Cursor cursor) {
        mName = cursor.getString(cursor.getColumnIndex(MyNotesContract.NAME_COLUMN));
        mSurName = cursor.getString(cursor.getColumnIndex(MyNotesContract.SURNAME_COLUMN));
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getSurName() {
        return mSurName;
    }

    public void setSurName(String mSurName) {
        this.mSurName = mSurName;
    }
}
