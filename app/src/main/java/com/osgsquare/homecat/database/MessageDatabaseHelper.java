package com.osgsquare.homecat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhongzichang on 9/16/14.
 */
public class MessageDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "messagetable.db";
    private static final int DATABASE_VERSION = 1;

    public MessageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        MessageTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        MessageTable.onUpgrade(database, oldVersion, newVersion);
    }
}
