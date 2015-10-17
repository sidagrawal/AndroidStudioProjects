package com.phplogin.sidag.myapplication;

import java.util.Random;

/**
 * Created by sidag_000 on 9/18/2015.
 */
public class ListItems {

    private String name;
    private String id;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ListItems(String id, String name) {
        this.id = id;
        this.name = name;
        this.uid = getUID();
    }

    private String getUID(){
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
}
