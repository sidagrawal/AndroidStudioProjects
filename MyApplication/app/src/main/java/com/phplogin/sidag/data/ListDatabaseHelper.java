package com.phplogin.sidag.data;

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

    //Add or Delete
    public static final int ADD = 1;
    public static final int DELETE = 2;

    //Columns for items table
    public static final String TABLE_ITEMS = "listitems";
    public static final String LIST_ITEM_ID = "list_item_id";
    public static final String LIST_ITEM = "list_item";
    public static final String LIST_ITEM_UID = "list_item_uid";
    public static final String LIST_ITEM_STATUS = "list_item_status";
    public static final String LIST_ITEM_TIMESTAMP = "list_item_timestamp";

    //Columns shared between the List and Item tables
    public static final String LIST_UID = "list_uid";

    //Columns for lists table
    public static final String TABLE_LIST = "lists";
    public static final String LIST_ID = "list_id";
    public static final String LIST_NAME = "list_name";
    public static final String LIST_STATUS = "list_status";
    public static final String LIST_TIMESTAMP = "list_timestamp";

    //Columns shared by List and User tables
    public static final String EMAIL = "email";

    //Columns for the Users Table
    public static final String USER_ID = "users_id";
    public static final String TABLE_USER = "users";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    //Create Statement for lists table
    private static final String CREATE_TABLE_LISTS = "create table " + TABLE_LIST
            + " ( " + LIST_ID + " integer primary key autoincrement, " +LIST_NAME + " text not null, " + LIST_UID
            + " text not null, " + LIST_TIMESTAMP + " text not null, " + LIST_STATUS + " integer, "+ EMAIL + " text not null );";

    //Create statement for items table
    private static final String CREATE_TABLE_ITEMS = "create table " + TABLE_ITEMS
            + " ( " + LIST_ITEM_ID + " integer primary key autoincrement, " + LIST_UID + " text not null, "
            + LIST_ITEM_STATUS + " integer, " + LIST_ITEM_TIMESTAMP + " text not null, " + LIST_ITEM
            + " text not null, " + LIST_ITEM_UID + " text not null );";

    //Create statement for the users table
    private static final String CREATE_TABLE_USERS = "create table " + TABLE_USER
            + " ( " + USER_ID + " integer primary key autoincrement, " + USERNAME
            + " text not null, " + EMAIL + " text not null, "+ PASSWORD + " text not null );";



    public ListDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ITEMS);
        db.execSQL(CREATE_TABLE_LISTS);
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DEBUG_TAG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

}
