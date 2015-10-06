package com.phplogin.sidag.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Siddhant on 10/5/2015.
 */
public class ListDatabaseHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "ListDatabase";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "list_data";

    public static final String TABLE_LIST = "listitems";
    public static final String LIST_ITEM_ID = "list_item_id";
    public static final String LIST_ID = "list_id";
    public static final String LIST_ITEM = "list_item";
    public static final String COL_URL = "url";

    private static final String CREATE_TABLE_TUTORIALS = "create table " + TABLE_LIST
            + " (" + LIST_ITEM_ID + " integer primary key autoincrement, " +LIST_ID + " integer, " + LIST_ITEM
            + " text not null, " + COL_URL + " text not null);";

    private static final String DB_SCHEMA = CREATE_TABLE_TUTORIALS;

    public ListDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DEBUG_TAG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
        onCreate(db);

    }
}
