package com.phplogin.sidag.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;

import com.phplogin.sidag.data.ListDatabaseHelper;
import com.phplogin.sidag.data.ListProvider;
import com.phplogin.sidag.data.UserProvider;
import com.phplogin.sidag.myapplication.Customer;
import com.phplogin.sidag.myapplication.phpGetAllLists;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Siddhant on 10/17/2015.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {


    // Global variables
    ContentResolver mContentResolver;
    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        //Get email of user from local database
        String[] projection = {ListDatabaseHelper.EMAIL};
        Cursor email_cursor = mContentResolver.query(UserProvider.CONTENT_URI_USERS, projection, null, null, null);
        String email = new String();
        if(email_cursor.moveToFirst()){
            email = email_cursor.getString(email_cursor.getColumnIndex(ListDatabaseHelper.EMAIL));
        }

        //Get the list of lists from the remote database using email
        //TODO: Get the customer object in the background???
        Customer remote_customer = null;
        try {
            remote_customer = new phpGetAllLists(getContext()).execute(email).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //Get the list of lists from the local database
        projection = new String[]{ListDatabaseHelper.LIST_UID};
        Cursor lists_in_local = mContentResolver.query(ListProvider.CONTENT_URI_LISTS, projection, null, null, null);

        //Find what is in Remote but not in Local and make a list of what to add to Local and then add to local
        Customer add_to_local_cust = new Customer(remote_customer);
        if (lists_in_local != null) {
            lists_in_local.moveToFirst();
            while(!lists_in_local.isAfterLast()){
                add_to_local_cust.removeListHeader(lists_in_local.getString(0));
            }
        }
        add_to_local_cust.addToDatabase(mContentResolver);

        //Find all the Lists newly added to Local and add them to Remote
        String query = "SELECT " + ListDatabaseHelper.LIST_UID + ", " + ListDatabaseHelper.LIST_NAME +
                        ", " + ListDatabaseHelper.EMAIL + ", " + ListDatabaseHelper.LIST_STATUS + ", " +
                        ListDatabaseHelper.LIST_TIMESTAMP + ", " + ListDatabaseHelper.LIST_ITEM_UID + ", "
                        + ListDatabaseHelper.LIST_ITEM + ", " + ListDatabaseHelper.LIST_ITEM_STATUS + ", " +
                        ListDatabaseHelper.LIST_ITEM_TIMESTAMP + " FROM " + ListDatabaseHelper.TABLE_LIST
                        + ", " + ListDatabaseHelper.TABLE_ITEMS + " WHERE " + ListDatabaseHelper.LIST_STATUS +
                        " AND " + ListDatabaseHelper.LIST_UID + " = " + ListDatabaseHelper.LIST_ITEM_UID;
        Cursor add_to_remote = mContentResolver.query(ListProvider.CONTENT_URI_LISTS_RAW_QUERY, null, query, null, null);
        if(add_to_remote.getCount() > 0)
            Log.d("add to remote", DatabaseUtils.dumpCurrentRowToString(add_to_remote));

        //Find the lists that need to be deleted in local and delete that person-list pairing from remote

        //Check timestamps of each list and listitem, if timestamp is less then update that list/listitem
        //in the place with the lessor timestamp




    }
}
