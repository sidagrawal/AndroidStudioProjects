package com.phplogin.sidag.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Siddhant on 10/6/2015.
 */
public class ListProvider extends ContentProvider {
    private ListDatabaseHelper mDB;

    private static final String AUTHORITY = "com.phplogin.sidag.data.ListProvider";
    public static final int LISTITEMS = 100;
    public static final int LISTITEM_ID = 110;

    private static final String LISTITEMS_BASE_PATH = "listitems";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + LISTITEMS_BASE_PATH);

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/mt-listitem";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/mt-listitem";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, LISTITEMS_BASE_PATH, LISTITEMS);
        sURIMatcher.addURI(AUTHORITY, LISTITEMS_BASE_PATH + "/#", LISTITEM_ID);
    }

    @Override
    public boolean onCreate() {
        mDB = new ListDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(ListDatabaseHelper.TABLE_LIST);

            int uriType = sURIMatcher.match(uri);
            switch (uriType) {
                case LISTITEM_ID:
                    queryBuilder.appendWhere(ListDatabaseHelper.LIST_ITEM_ID + "="
                            + uri.getLastPathSegment());
                    break;
                case LISTITEMS:
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
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
