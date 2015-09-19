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
 * Created by sidag_000 on 9/4/2015.
 */
public class phpLogin extends AsyncTask<String, Void, String> {
    private Context context;
    private EditText result;
    public phpLogin(Context context, EditText result){
        this.context = context;
        this.result = result;
    }

    @Override
    protected String doInBackground(String... params) {
        try{
            String username = params[0];
            String password = params[1];
            String link = "http://192.168.1.146:8080/helloworld.php?username="+username+"&password="+password;
            Log.d("link", link);

            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            while((line = in.readLine()) != null){
                sb.append(line);
            }
            in.close();
            Log.d("result", sb.toString());
            return sb.toString();
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }
}
