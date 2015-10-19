package com.phplogin.sidag.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Siddhant on 10/6/2015.
 */
public class UserProvider extends ContentProvider {
    private ListDatabaseHelper mDB;

    private static final String AUTHORITY = "com.phplogin.sidag.data.ListProvider";
    public static final int LISTUSER = 100;
    public static final int LISTUSER_ID = 110;

    public static final String LISTITEMS_BASE_PATH = ListDatabaseHelper.TABLE_USER;

    public static final Uri CONTENT_URI_USERS = Uri.parse("content://" + AUTHORITY
            + "/" + LISTITEMS_BASE_PATH);

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/mt-listitem";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/mt-listitem";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, LISTITEMS_BASE_PATH, LISTUSER);
        sURIMatcher.addURI(AUTHORITY, LISTITEMS_BASE_PATH + "/#", LISTUSER_ID);
    }
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        mDB = new ListDatabaseHelper(getContext());
        db = mDB.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(ListDatabaseHelper.TABLE_USER);

            int uriType = sURIMatcher.match(uri);
            switch (uriType) {
                case LISTUSER_ID:
                    queryBuilder.appendWhere(ListDatabaseHelper.LIST_ITEM_ID + "="
                            + uri.getLastPathSegment());
                    break;
                case LISTUSER:
                    // no filter
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI");
            }

            Cursor cursor = queryBuilder.query(mDB.getReadableDatabase(),
                    projection, selection, selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = mDB.getWritableDatabase();
        long rowID = db.insert(ListDatabaseHelper.TABLE_ITEMS, "", values);
        if(rowID > 0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI_USERS, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to insert record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = mDB.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case LISTUSER:
                rowsDeleted = db.delete(ListDatabaseHelper.TABLE_ITEMS, selection,
                        selectionArgs);
                break;
            case LISTUSER_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(ListDatabaseHelper.TABLE_ITEMS,
                            ListDatabaseHelper.LIST_ITEM_UID + "=" + id,
                            null);
                } else {
                    rowsDeleted = db.delete(ListDatabaseHelper.TABLE_ITEMS,
                            ListDatabaseHelper.LIST_ITEM_UID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = mDB.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case LISTUSER:
                rowsUpdated = db.update(ListDatabaseHelper.TABLE_ITEMS,
                        values,
                        selection,
                        selectionArgs);
                break;
            case LISTUSER_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(ListDatabaseHelper.TABLE_ITEMS,
                            values,
                            ListDatabaseHelper.LIST_ITEM_UID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(ListDatabaseHelper.TABLE_ITEMS,
                            values,
                            ListDatabaseHelper.LIST_ITEM_UID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
