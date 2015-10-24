package com.phplogin.sidag.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;

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
        Customer customer = null;
        try {
            customer = new phpGetAllLists(getContext()).execute(email).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //Get the list of lists from the local database

        //Find what is in Remote but not in Local and make a list of what to add to Remote

        //Find all the Lists newly added to Local and add them to Remote

        //Find the lists that need to be deleted in local and delete that person-list pairing from remote

        //Check timestamps of each list and listitem, if timestamp is less then update that list/listitem
        //in the place with the lessor timestamp




    }
}
