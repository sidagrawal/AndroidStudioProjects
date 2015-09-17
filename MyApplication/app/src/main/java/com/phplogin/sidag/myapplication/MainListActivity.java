package com.phplogin.sidag.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainListActivity extends AppCompatActivity {

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
        expandableListView = (ExpandableListView)findViewById(R.id.exp_list_view);
        ArrayList<String> headings = new ArrayList<String>();
        headings.add("Back");
        headings.add("Chest");
        headings.add("Leg");
        ArrayList<String> child1 = new ArrayList<String>();
        child1.add("Pull ups");
        child1.add("Dead Lifts");
        child1.add("Rows");
        ArrayList<String> child2 = new ArrayList<String>();
        child2.add("Chest Press");
        child2.add("Push ups");
        child2.add("Flies");
        ArrayList<String> child3 = new ArrayList<String>();
        child3.add("Squats");
        child3.add("Lunges");
        child3.add("Jumping Jacks");
        HashMap<String, ArrayList<String>> children = new HashMap<String, ArrayList<String>>();
        children.put(headings.get(0), child1);
        children.put(headings.get(1), child2);
        children.put(headings.get(2), child3);
        customer = new Customer(username, password, "Sid", headings, children);
        ListAdapter myAdapter = new ListAdapter(this, customer);
        expandableListView.setAdapter(myAdapter);
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
}
