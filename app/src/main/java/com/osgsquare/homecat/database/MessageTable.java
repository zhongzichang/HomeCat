package com.osgsquare.homecat.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by zhongzichang on 9/16/14.
 */
public class MessageTable {

    // Database table
    public static final String TABLE_MESSAGE = "message";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GROUP_ID = "group_id";
    public static final String COLUMN_SOURCE_ID = "source_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TEXT = "text";



    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MESSAGE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_GROUP_ID + " integer not null, "
            + COLUMN_SOURCE_ID + " integer not null,"
            + COLUMN_TYPE + " integer not null,"
            + COLUMN_TEXT + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(MessageTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        onCreate(database);
    }
}
