package com.phplogin.sidag.myapplication;

import android.content.ContentValues;
import android.content.Context;

import com.phplogin.sidag.data.ListDatabaseHelper;
import com.phplogin.sidag.data.ListProvider;

import java.util.ArrayList;

/**
 * Created by sidag_000 on 9/4/2015.
 */
public class Customer {

    private String username;
    private String password;
    private String email;
    private ArrayList<ListHeaders> list_headers;

    public Customer(String username, String password, String email, ArrayList<ListHeaders> list_headers) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.list_headers = list_headers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<ListHeaders> getList_headers() {
        return list_headers;
    }

    public void setList_headers(ArrayList<ListHeaders> list_headers) {
        this.list_headers = list_headers;
    }

    public void addToDatabase(Context context){
        ContentValues values = new ContentValues();
        for(ListHeaders listHeaders : list_headers){
            int listHeaderID = Integer.parseInt(listHeaders.getId());
            values.put(ListDatabaseHelper.LIST_ID, listHeaderID);
            for(ListItems listItems : listHeaders.getItems()){
                values.put(ListDatabaseHelper.LIST_ITEM, listItems.getName());
                values.put(ListDatabaseHelper.LIST_ITEM_UID, listItems.getUid());
                context.getContentResolver().insert(ListProvider.CONTENT_URI_LISTS, values);
            }
        }
    }


}
