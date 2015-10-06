package com.phplogin.sidag.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Siddhant on 10/5/2015.
 */
public class ListDatabaseHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "ListDatabase";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "list_data";

    public ListDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
