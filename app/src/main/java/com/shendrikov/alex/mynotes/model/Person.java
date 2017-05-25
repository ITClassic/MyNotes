package com.shendrikov.alex.mynotes.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.shendrikov.alex.mynotes.db.MyNotesContract;
import com.tjeannin.provigen.ProviGenBaseContract;

import java.io.Serializable;

/**
 * Created by Alex on 17.12.2016.
 */

public class Person implements Parcelable{

    private String mName;
    private String mSurName;
    private String mTime;
    private long mId;

    public Person(){}

    public Person(Cursor cursor) {
        mId = cursor.getLong(cursor.getColumnIndex(ProviGenBaseContract._ID));
        mName = cursor.getString(cursor.getColumnIndex(MyNotesContract.NAME_COLUMN));
        mSurName = cursor.getString(cursor.getColumnIndex(MyNotesContract.SURNAME_COLUMN));
        mTime = cursor.getString(cursor.getColumnIndex(MyNotesContract.TIME_COLUMN));
    }

    protected Person(Parcel in) {
        mName = in.readString();
        mSurName = in.readString();
        mTime = in.readString();
        mId = in.readLong();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

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

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mSurName);
        dest.writeString(mTime);
        dest.writeLong(mId);
    }
}
