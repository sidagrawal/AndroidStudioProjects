package com.phplogin.sidag.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by sidag_000 on 9/25/2015.
 */
public class MyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public ArrayList<ListItems> myItems;
    private Context context;

    public MyAdapter(Context context, ArrayList<ListItems> listItems) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        myItems = listItems;
        notifyDataSetChanged();
    }

    public int getCount() {
        return myItems.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item, null);
            holder.caption = (EditText) convertView
                    .findViewById(R.id.ItemCaption);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Fill EditText with the value you have in data source
        holder.caption.setText((CharSequence) myItems.get(position).getName());
        holder.caption.setId(position);

        //we need to update adapter once we finish with editing
        holder.caption.setFocusable(true);
        holder.caption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    myItems.get(position).setName(Caption.getText().toString());
                    Log.d("Focus", "Lost the focus");
                    myItems.get(position).updateDatabase(context.getContentResolver());
                }
                else if(hasFocus){
                    Log.d("Focus", "I got the focus");
                }
            }
        });

        return convertView;
    }
}

class ViewHolder {
    EditText caption;
}

class ListItem {
    public String caption;
}
