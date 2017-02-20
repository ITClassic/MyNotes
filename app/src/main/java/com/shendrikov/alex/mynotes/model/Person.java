package com.shendrikov.alex.mynotes.model;

import android.database.Cursor;

import com.shendrikov.alex.mynotes.db.MyNotesContract;
import com.tjeannin.provigen.ProviGenBaseContract;

import java.io.Serializable;

/**
 * Created by Alex on 17.12.2016.
 */

public class Person implements Serializable{

    private String mName;
    private String mSurName;
    private long mId;

    public Person(){}

    public Person(Cursor cursor) {
        mId = cursor.getLong(cursor.getColumnIndex(ProviGenBaseContract._ID));
        mName = cursor.getString(cursor.getColumnIndex(MyNotesContract.NAME_COLUMN));
        mSurName = cursor.getString(cursor.getColumnIndex(MyNotesContract.SURNAME_COLUMN));
    }

    public long getId() {
        return mId;
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
