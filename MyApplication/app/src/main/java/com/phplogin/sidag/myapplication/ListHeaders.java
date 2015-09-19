package com.phplogin.sidag.myapplication;

import java.util.ArrayList;

/**
 * Created by sidag_000 on 9/18/2015.
 */
public class ListHeaders {

    private String name;
    private String id;
    private ArrayList<ListItems> items;

    public ListHeaders(String name, String id, ArrayList<ListItems> items) {

        this.name = name;
        this.id = id;
        this.items = items;
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

    public void setItems(ArrayList<ListItems> items) {
        this.items = items;
    }

    public void addItem(ListItems listItem){
        items.add(listItem);
    }

}
