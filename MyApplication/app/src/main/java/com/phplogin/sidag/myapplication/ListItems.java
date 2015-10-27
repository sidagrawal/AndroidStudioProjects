package com.phplogin.sidag.myapplication;

import java.sql.Time;
import java.util.Random;

/**
 * Created by sidag_000 on 9/18/2015.
 */
public class ListItems {

    private String name;
    private String uid;
    private TimeStamp timestamp;
    private int status;

    public ListItems(String name, TimeStamp timestamp, int status) {
        this.name = name;
        this.uid = makeUID();
        this.timestamp = timestamp;
        this.status = status;
    }

    public ListItems(String name, String uid, TimeStamp timestamp, int status) {
        this.name = name;
        this.uid = uid;
        this.timestamp = timestamp;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
