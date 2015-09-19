package com.phplogin.sidag.myapplication;

/**
 * Created by sidag_000 on 9/18/2015.
 */
public class ListItems {

    private String name;
    private String id;

    public ListItems(String id, String name) {
        this.id = id;
        this.name = name;
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
}
