package com.phplogin.sidag.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.phplogin.sidag.data.ListDatabaseHelper;
import com.phplogin.sidag.data.ListProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainListActivity extends AppCompatActivity implements FragmentList.OnFragmentInteractionListener, LoaderManager.LoaderCallbacks<Cursor> {

    ExpandableListView expandableListView;
    Customer customer;
    Cursor list_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        //TODO : This can be deleted after the syncadapter is done
        String username = "";
        String password = "";
        String email = "";
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
            password = extras.getString("password");
            email = extras.getString("email");
        }
        try {
            customer = new phpGetAllLists(this).execute(email).get();
            if(customer != null)
                Log.d("Customer", customer.getList_headers().get(0).getItemNames().get(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        getLoaderManager().initLoader(0, null, this);

//        //Create a fragment for every list the user ha
//        // TODO : after syncadapter is done the new instance of each fragment needs list data
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        list_ids.moveToFirst();
//        while (!list_ids.isAfterLast()) {
//            FragmentList listFrag = FragmentList.newInstance();
//            fragmentTransaction.add(R.id.fragment_container, listFrag, "List Fragment");
//        }
//
//        fragmentTransaction.commit();




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

    //Get all the lists of the user
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {ListDatabaseHelper.LIST_ID};
        CursorLoader cursor = new CursorLoader(this, ListProvider.CONTENT_URI_LISTS, projection , null, null, null);
        return cursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        list_ids = data;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
