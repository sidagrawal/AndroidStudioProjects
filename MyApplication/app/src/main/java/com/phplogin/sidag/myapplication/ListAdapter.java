package com.phplogin.sidag.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Created by sidag_000 on 9/4/2015.
 */
public class ListAdapter extends BaseExpandableListAdapter {
    private Customer customer;
    private Context ctx;

    public ListAdapter(Context ctx, Customer customer){
        this.ctx = ctx;
        this.customer = customer;
    }

    @Override
    public int getGroupCount() {
        return customer.getHeaderCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return customer.getChildrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return customer.getList_headers().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return customer.getChild(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String)this.getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_layout, null);
        }

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);


        TextView textView = (TextView)convertView.findViewById(R.id.heading);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title = (String)this.getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_layout, null);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.child_heading);
        textView.setText(title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
