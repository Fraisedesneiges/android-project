package com.example.matthieugedeon.android_project.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matthieugedeon.android_project.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

//Adapter that update our ListView through the fetched data
public class ListAdapter extends BaseAdapter {
    private Context context; //context
    private Vector<JSONObject> vector;

    public ListAdapter(Context context, Vector<JSONObject> vector){
        this.context = context;
        this.vector = vector;
    }

    @Override
    public int getCount() {
        return vector.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return vector.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            //convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false); //inflate layout with text item
            convertView = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false); //inflate layout with image item
        }

        // get current item to be displayed
        JSONObject currentItem = (JSONObject) getItem(position);

        // get the TextView for item name and item description
        TextView TVDate = (TextView) convertView.findViewById(R.id.date);

        TextView TVAmount = (TextView) convertView.findViewById(R.id.amount);

        try{
            TVDate.setText(currentItem.getString("hash"));
        }
        catch (JSONException e){
            TVDate.setText("err");
        }

        // returns the view for the current row
        return convertView;
    }
    public void add(JSONObject o){
        vector.add(o);
    }
}