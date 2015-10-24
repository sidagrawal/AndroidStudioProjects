package com.phplogin.sidag.myapplication;
import android.app.LauncherActivity;
import android.util.JsonReader;
import android.util.Log;

import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sidag_000 on 9/19/2015.
 */
public class JsonParser {

    Customer customer;

    public JsonParser() {
        customer = new Customer();
    }

    public void decodeCustomer(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readJSONObj(reader);
        }
        finally{
            reader.close();
        }
    }

    private void readJSONObj(JsonReader reader) throws IOException{
        Log.d("Customer", "Getting user info");
        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("email")){
                customer.setEmail(reader.nextString());
            } else if(name.equals("username")){
                customer.setUsername(reader.nextString());
            } else if(name.equals("password")){
                customer.setPassword(reader.nextString());
            } else if(name.equals("lists")){
                readLists(reader);
            } else{
                reader.skipValue();
            }
        }
        Log.d("Customer", "Got user info");
        reader.endObject();
    }

    private void readLists(JsonReader reader) throws IOException{
        ArrayList<ListHeaders> list_headers = new ArrayList<ListHeaders>();
        Log.d("Customer", "Looping through lists");
        reader.beginArray();
        while(reader.hasNext()){
            list_headers.add(readList(reader));
        }
        reader.endArray();
        customer.setList_headers(list_headers);
    }

    private ListHeaders readList(JsonReader reader) throws IOException{
        Log.d("Customer", "Getting list info");
        String uid = "";
        String name = "";
        ArrayList<ListItems> list_items = new ArrayList<ListItems>();
        String timestamp = "";
        int status;
        reader.beginObject();
        while(reader.hasNext()){
            String token = reader.nextName();
            if(token.equals("List UID")){
                uid = reader.nextString();
            } else if(token.equals("Name")){
                name = reader.nextString();
            } else if(token.equals("Time Stamp")){
                timestamp = reader.nextString();
            } else if(token.equals("items")){
                list_items = readItems(reader);
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ListHeaders(name, list_items, uid, timestamp, 0);
    }

    private ArrayList<ListItems> readItems(JsonReader reader) throws IOException{
        Log.d("Customer", "Looping through items");
        ArrayList<ListItems> list_items = new ArrayList<ListItems>();
        reader.beginArray();
        while(reader.hasNext()){
            list_items.add(readItem(reader));
        }
        reader.endArray();
        return list_items;
    }

    private ListItems readItem(JsonReader reader) throws IOException{
        Log.d("Customer", "Getting item info");
        String uid = "";
        String name = "";
        String timestamp = "";
        int status;
        reader.beginObject();
        while(reader.hasNext()){
            String token = reader.nextName();
            if(token.equals("List Item UID")){
                uid = reader.nextString();
            } else if(token.equals("Item Name")){
                name = reader.nextString();
            } else if(token.equals("Time Stamp")){
                timestamp = reader.nextString();
            } else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ListItems(name, uid, timestamp, 0);
    }

    public Customer getCustomer() {
        return customer;
    }
}


