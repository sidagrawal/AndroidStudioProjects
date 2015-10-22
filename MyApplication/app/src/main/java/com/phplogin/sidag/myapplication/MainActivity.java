package com.phplogin.sidag.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.phplogin.sidag.data.ListDatabaseHelper;
import com.phplogin.sidag.data.ListProvider;
import com.phplogin.sidag.data.UserProvider;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.deleteDatabase("list_data");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void login(View view) {
        EditText result = (EditText)findViewById(R.id.result);
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        String login_success = new String();
        try {
            login_success = new phpLogin(this, result).execute(username, password).get();
            Log.d("login", login_success);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /**/
        if(!login_success.contains("fail")) {
            ContentValues user = new ContentValues();
            user.put("username", username);
            user.put("password", password);
            user.put("email", login_success);
            String[] projection = {ListDatabaseHelper.USERNAME};
            String selection = ListDatabaseHelper.USERNAME + " = " + username;
            if(getContentResolver().query(UserProvider.CONTENT_URI_USERS, projection, selection, null, null).getCount() == 0){
                getContentResolver().insert(UserProvider.CONTENT_URI_USERS, user);
            }
            Intent intent = new Intent(this, MainListActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
        }
    }

    public void addUser(View view) {
        Intent intent = new Intent(this, CreateNewUser.class);
        startActivity(intent);
    }
}
