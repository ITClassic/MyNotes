package com.shendrikov.alex.mynotes.db;

import android.net.Uri;

import com.shendrikov.alex.mynotes.BuildConfig;
import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.ContentUri;

/**
 * Created by 430 on 04.01.2017.
 */

public interface MyNotesContract extends ProviGenBaseContract {

    @Column(Column.Type.TEXT)
    public static final String NAME_COLUMN = "NAME";

    @Column(Column.Type.TEXT)
    public static final String SURNAME_COLUMN = "SURNAME";

    public static final String TABLE_NAME = "my_notes_table";

    public static final String TABLE_URI_TEMPLATE = "content://%s/%s";

    public static final String URI = String.format(
            TABLE_URI_TEMPLATE, BuildConfig.APPLICATION_ID, TABLE_NAME);

    @ContentUri
    public static final Uri CONTENT_URI = Uri.parse(URI);
}

