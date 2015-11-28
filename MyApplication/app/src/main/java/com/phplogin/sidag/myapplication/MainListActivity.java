package com.phplogin.sidag.myapplication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.phplogin.sidag.data.ListDatabaseHelper;
import com.phplogin.sidag.data.ListProvider;

public class MainListActivity extends AppCompatActivity implements FragmentList.OnFragmentInteractionListener, LoaderManager.LoaderCallbacks<Cursor> {

    ExpandableListView expandableListView;
    Customer customer;
    Cursor list_uids;
    Account mAccount;
    final int CREATE_FRAGMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        mAccount = CreateSyncAccount(this);


        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(mAccount, ListProvider.getAuthority(), bundle);
//        ContentResolver.addPeriodicSync(mAccount, ListProvider.getAuthority(), Bundle.EMPTY, 2L);

        //Sets the cursor with all List IDs
        getLoaderManager().initLoader(0, null, this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    private void createFragments(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        list_uids.moveToFirst();
        while (!list_uids.isAfterLast()) {
            FragmentList listFrag = FragmentList.newInstance(list_uids.getString(list_uids.getColumnIndex(ListDatabaseHelper.LIST_UID)),
                    list_uids.getString(list_uids.getColumnIndex(ListDatabaseHelper.LIST_NAME)));
            fragmentTransaction.add(R.id.fragment_container, listFrag, "List Fragment");
            list_uids.moveToNext();
        }
        fragmentTransaction.commit();
        //TODO:something needs to be done here with fragmentTransaction go google it
    }

    private void save(){

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == CREATE_FRAGMENT) {
                createFragments();
            }
        }
    };

    //Get all the lists of the user
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {ListDatabaseHelper.LIST_UID, ListDatabaseHelper.LIST_NAME};
        CursorLoader cursor = new CursorLoader(this, ListProvider.CONTENT_URI_LISTS, projection , null, null, null);
        return cursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        list_uids = data;
        handler.sendEmptyMessage(CREATE_FRAGMENT);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        String ACCOUNT = "dummyaccount";
        String ACCOUNT_TYPE = "example.com";
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) return newAccount;
        else {}
        return newAccount;
    }
}
