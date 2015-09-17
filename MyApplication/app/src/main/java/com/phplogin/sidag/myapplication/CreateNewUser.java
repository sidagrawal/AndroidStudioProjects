package com.phplogin.sidag.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class CreateNewUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_new_user, menu);
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

    public void CreateNewUser(View view) {
        EditText result = (EditText)findViewById(R.id.result);
        String username = ((EditText)findViewById(R.id.NewUsername)).getText().toString();
        String password = ((EditText)findViewById(R.id.NewPassword)).getText().toString();
        String email = ((EditText)findViewById(R.id.NewEmail)).getText().toString();
        String creationResult;
        String toastText = "Error";
        try {
            creationResult = new phpCreateNewUser(this, result).execute(username, password, email).get();
            Log.d("Result", creationResult);
            if(creationResult.contains("Username") && creationResult.contains("Email")){
                toastText = "Username and Email already in use";
            }
            else if(creationResult.contains("Username")){
                toastText = "Username already in use";
            }
            else if(creationResult.contains("Email")){
                toastText = "Email already in use";
            }
            else if(creationResult.contains("Successfully")){
                toastText = "You have been successfully added";
            }

            Toast toast = Toast.makeText(this, (CharSequence)toastText, Toast.LENGTH_SHORT );
            toast.show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
