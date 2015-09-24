package com.phplogin.sidag.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainListActivity extends AppCompatActivity implements FragmentList.OnFragmentInteractionListener {

    ExpandableListView expandableListView;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        String username = "";
        String password = "";
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
            password = extras.getString("password");
        }
        String all_lists = "";
        JSONObject jsonObj = null;
        try {
            all_lists = new phpGetAllLists(this).execute(username, password).get();
            Log.d("json", all_lists);
            jsonObj = new JSONObject(all_lists);
            customer = JsonParser.decodeCustomer(jsonObj, username, password);
            Log.d("Customer", customer.getList_headers().get(0).getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentList listFrag = new FragmentList();
        fragmentTransaction.add(R.id.fragment_container, listFrag, "List Fragment");
        fragmentTransaction.commit();




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
}
