package com.phplogin.sidag.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sidag_000 on 9/18/2015.
 */
public class phpGetAllLists extends AsyncTask<String, Void, Customer> {


    private Context context;
    public phpGetAllLists(Context context){
        this.context = context;
    }

    @Override
    protected Customer doInBackground(String... params) {
        try{
            String email = params[0];
            String link = "http://159.203.66.71/get_all_lists.php?email="+email;
            Log.d("link", link);

            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream in = connection.getInputStream();
            JsonParser parser = new JsonParser();
            parser.decodeCustomer(in);
            in.close();
            return parser.getCustomer();
        }
        catch(Exception e){
            return new Customer(null, null, e.getMessage(), null);
        }
    }
    protected void onPostExecute(Customer customer){

    }
}
