package com.shendrikov.alex.mynotes.providers;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.shendrikov.alex.mynotes.db.MyNotesContract;
import com.tjeannin.provigen.ProviGenOpenHelper;
import com.tjeannin.provigen.ProviGenProvider;

/**
 * Created by 430 on 04.01.2017.
 */

public class MyNotesContentProvider extends ProviGenProvider {

    private static final Class[] CONTRACTS = new Class[]{ MyNotesContract.class };

    public static final String DB_NAME = "my_notes_database";

    public static final int DB_VERSION = 1;

    @Override
    public SQLiteOpenHelper openHelper(Context context) {
        return new ProviGenOpenHelper(context, DB_NAME, null, DB_VERSION, CONTRACTS);
    }

    @Override
    public Class[] contractClasses() {
        return CONTRACTS;
    }
}
