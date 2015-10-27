package com.phplogin.sidag.myapplication;

import android.content.ContentValues;

import com.phplogin.sidag.data.ListDatabaseHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sidag_000 on 9/18/2015.
 */
public class ListHeaders {

    private String name;
    private String uid;
    private TimeStamp timestamp;
    private int status;
    private ArrayList<ListItems> items;

    public ListHeaders(String name, ArrayList<ListItems> items, TimeStamp timestamp, int status) {
        this.uid = makeUID();
        this.name = name;
        this.items = items;
        this.timestamp = timestamp;
        this.status = status;
    }

    public ListHeaders(String name, ArrayList<ListItems> items, String uid, TimeStamp timestamp, int status) {
        this.name = name;
        this.items = items;
        this.uid = uid;
        this.timestamp = timestamp;
        this.status = status;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public TimeStamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(TimeStamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String makeUID(){
        String result = "";
        String opt = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ0987654321";
        Random rand = new Random();
        for(int i = 0; i < opt.length(); i++){
            int index = rand.nextInt(opt.length());
            result += opt.charAt(index);
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ListItems> getItems() {
        return items;
    }

    public ArrayList<String> getItemNames(){
        ArrayList<String> result = new ArrayList<String>();
        for(ListItems item : items){
            result.add(item.getName());
        }
        return result;
    }

    public ContentValues getAll(){
        ContentValues values = new ContentValues();
        values.put(ListDatabaseHelper.LIST_UID, this.uid);
        values.put(ListDatabaseHelper.LIST_NAME, this.name);
        values.put(ListDatabaseHelper.LIST_STATUS, this.status);
        values.put(ListDatabaseHelper.LIST_TIMESTAMP, this.timestamp.toString());
        return values;
    }

    public void setItems(ArrayList<ListItems> items) {
        this.items = items;
    }

    public void addItem(ListItems listItem){
        items.add(listItem);
    }

}
