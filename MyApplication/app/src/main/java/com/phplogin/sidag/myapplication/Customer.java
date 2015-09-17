package com.phplogin.sidag.myapplication;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sidag_000 on 9/4/2015.
 */
public class Customer {

    private String username;
    private String password;
    private String email;
    private ArrayList<String> list_headers;
    private HashMap<String, ArrayList<String>> list_children;

    public Customer(String username, String password, String email, ArrayList<String> list_headers, HashMap<String, ArrayList<String>> list_children){
        this.username = username;
        this.password = password;
        this.email = email;
        this.list_headers = list_headers;
        this.list_children = list_children;
    }

    public int getHeaderCount(){
        return list_headers.size();
    }

    public int getChildrenCount(int pos){
        return list_children.get(list_headers.get(pos)).size();
    }

    public String getChild(int parent_pos, int child_pos){
        return list_children.get(list_headers.get(parent_pos)).get(child_pos);
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

    public ArrayList<String> getList_headers() {
        return list_headers;
    }

    public void setList_headers(ArrayList<String> list_headers) {
        this.list_headers = list_headers;
    }

    public HashMap<String, ArrayList<String>> getList_children() {
        return list_children;
    }

    public void setList_children(HashMap<String, ArrayList<String>> list_children) {
        this.list_children = list_children;
    }
}
