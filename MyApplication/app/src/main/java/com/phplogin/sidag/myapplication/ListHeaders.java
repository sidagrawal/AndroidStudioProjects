package com.phplogin.sidag.myapplication;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sidag_000 on 9/18/2015.
 */
public class ListHeaders {

    private String name;
    private String id;
    private String uid;
    private int timestamp;
    private int status;
    private ArrayList<ListItems> items;

    public ListHeaders(String name, String id, ArrayList<ListItems> items, int timestamp, int status) {
        this.uid = makeUID();
        this.name = name;
        this.id = id;
        this.items = items;
        this.timestamp = timestamp;
        this.status = status;
    }

    public ListHeaders(String name, String id, ArrayList<ListItems> items, String uid, int timestamp, int status) {
        this.name = name;
        this.id = id;
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

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setItems(ArrayList<ListItems> items) {
        this.items = items;
    }

    public void addItem(ListItems listItem){
        items.add(listItem);
    }

}
