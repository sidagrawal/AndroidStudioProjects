package com.phplogin.sidag.myapplication;
import android.app.LauncherActivity;
import android.util.Log;

import org.json.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by sidag_000 on 9/19/2015.
 */
public class JsonParser {

    private JsonParser() {}

    public static Customer decodeCustomer(JSONObject jsonObj, String username, String password) throws JSONException {
        ArrayList<ListHeaders> list_headers = new ArrayList<ListHeaders>();
        JSONObject lists = jsonObj.getJSONObject("lists");
        Iterator<?> keys =  lists.keys();
        while(keys.hasNext()){
            String key = (String)keys.next();
            if(lists.get(key) instanceof JSONObject){
                ArrayList<ListItems> list_items = new ArrayList<ListItems>();
                JSONObject list = lists.getJSONObject(key);
                JSONObject items = list.getJSONObject("items");
                Iterator<?> itemKeys = items.keys();
                while(itemKeys.hasNext()){
                    String itemKey = (String)itemKeys.next();
                    ListItems temp_list_item = new ListItems(itemKey, items.getString(itemKey));
                    list_items.add(temp_list_item);
                }
                ListHeaders temp_list_header = new ListHeaders(list.getString("name"), key, list_items);
                list_headers.add(temp_list_header);
            }
        }
        Customer cust = new Customer(username, password,jsonObj.getString("email"), list_headers);
        return cust;
    }


}
