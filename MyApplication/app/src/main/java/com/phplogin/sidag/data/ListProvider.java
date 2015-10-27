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
public class ListProvider extends ContentProvider {
    private ListDatabaseHelper mDB;

    private static final String AUTHORITY = "com.phplogin.sidag.data.ListProvider";
    public static final int LIST = 100;
    public static final int LIST_ID = 110;
    public static final int LIST_RAW_QUERY = 120;

    public static final String LISTITEMS_BASE_PATH = ListDatabaseHelper.TABLE_LIST;
    public static final String RAW_QUERY = "rawquery";

    public static final Uri CONTENT_URI_LISTS = Uri.parse("content://" + AUTHORITY
            + "/" + LISTITEMS_BASE_PATH);
    public static final Uri CONTENT_URI_LISTS_RAW_QUERY = Uri.parse("content://" + AUTHORITY
            + "/" + LISTITEMS_BASE_PATH + RAW_QUERY);

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/mt-listitem";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/mt-listitem";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, LISTITEMS_BASE_PATH, LIST);
        sURIMatcher.addURI(AUTHORITY, LISTITEMS_BASE_PATH + "/#", LIST_ID);
        sURIMatcher.addURI(AUTHORITY, LISTITEMS_BASE_PATH + RAW_QUERY, LIST_RAW_QUERY);
    }
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        mDB = new ListDatabaseHelper(getContext());
        db = mDB.getWritableDatabase();
        return true;
    }

    public static String getAuthority(){
        return AUTHORITY;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(ListDatabaseHelper.TABLE_LIST);
            Cursor cursor;
            int uriType = sURIMatcher.match(uri);
            switch (uriType) {
                case LIST_ID:
                    queryBuilder.appendWhere(ListDatabaseHelper.LIST_ID + "="
                            + uri.getLastPathSegment());
                    break;
                case LIST:
                    // no filter
                    break;
                case LIST_RAW_QUERY:
                    cursor = db.rawQuery(selection, selectionArgs);
                    return cursor;
                default:
                    throw new IllegalArgumentException("Unknown URI");
            }

            cursor = queryBuilder.query(mDB.getReadableDatabase(),
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
        long rowID = db.insert(ListDatabaseHelper.TABLE_LIST, "", values);
        if(rowID > 0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI_LISTS, rowID);
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
            case LIST:
                rowsDeleted = db.delete(ListDatabaseHelper.TABLE_LIST, selection,
                        selectionArgs);
                break;
            case LIST_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(ListDatabaseHelper.TABLE_LIST,
                            ListDatabaseHelper.LIST_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = db.delete(ListDatabaseHelper.TABLE_LIST,
                            ListDatabaseHelper.LIST_ID + "=" + id
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
            case LIST:
                rowsUpdated = db.update(ListDatabaseHelper.TABLE_LIST,
                        values,
                        selection,
                        selectionArgs);
                break;
            case LIST_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(ListDatabaseHelper.TABLE_LIST,
                            values,
                            ListDatabaseHelper.LIST_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(ListDatabaseHelper.TABLE_LIST,
                            values,
                            ListDatabaseHelper.LIST_ID + "=" + id
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
