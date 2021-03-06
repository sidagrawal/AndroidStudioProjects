package com.phplogin.sidag.myapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.phplogin.sidag.data.ItemProvider;
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

    public Customer(Customer cust){
        this.username = cust.getUsername();
        this.password = cust.getPassword();
        this.email = cust.getEmail();
        this.list_headers = cust.getList_headers();
    }

    public Customer(){

    };

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

    public void addToDatabase(ContentResolver mContentResolver){
        ContentValues list_values = new ContentValues();
        for(ListHeaders listHeaders : list_headers){
            if(listHeaders.getStatus() == 0){
                list_values = listHeaders.getAll();
                list_values.put(ListDatabaseHelper.EMAIL, this.email);
                mContentResolver.insert(ListProvider.CONTENT_URI_LISTS, list_values);
            }
            ContentValues listitem_values = new ContentValues();
            for(ListItems listItems : listHeaders.getItems()){
                if(listItems.getStatus() == 0){
                    listitem_values = listItems.getAll();
                    listitem_values.put(ListDatabaseHelper.LIST_UID, listHeaders.getUid());
                    mContentResolver.insert(ItemProvider.CONTENT_URI_ITEMS, listitem_values);
                }
            }
        }
    }

    public void removeListHeader(String list_UID, ContentResolver contentResolver){
        for(int i = 0; i < list_headers.size(); i++){
            ListHeaders listHeaders = list_headers.get(i);
            if(list_UID.equals(listHeaders.getUid())){
                listHeaders.setStatus(1);
            }
            String[] projection = new String[]{ListDatabaseHelper.LIST_ITEM_UID};
            Cursor list_item = contentResolver.query(ItemProvider.CONTENT_URI_ITEMS, projection, ListDatabaseHelper.LIST_UID + " LIKE '" + list_UID + "'", null, null);
            if (list_item != null) {
                list_item.moveToFirst();
                while(!list_item.isAfterLast()){
                    String item_uid = list_item.getString(0);
                    for(int j = 0; j < listHeaders.getItems().size(); j++){
                        if(item_uid.equals(listHeaders.getItems().get(j).getUid())){
                            listHeaders.getItems().get(j).setStatus(1);
                        }
                    }
                    list_item.moveToNext();
                }
            }
        }
    }


}
