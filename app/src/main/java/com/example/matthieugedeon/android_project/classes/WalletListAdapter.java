package com.example.matthieugedeon.android_project.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.activities.AddressDetailsActivity;
import com.example.matthieugedeon.android_project.activities.SignUpActivity;
import com.example.matthieugedeon.android_project.models.Wallet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class WalletListAdapter extends BaseAdapter {
    private Context context; //context
    private Vector<Wallet> vector;
    private Activity activity;

    public WalletListAdapter(Context context, Vector<Wallet> vector, Activity activity){
        this.context = context;
        this.vector = vector;
        this.activity = activity;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.address_item, parent, false); //inflate layout with image item
        }

        // get current item to be displayed
        Wallet currentItem = (Wallet) getItem(position);

        // get the TextView for item name and item description
        TextView address = (TextView) convertView.findViewById(R.id.i_address);

        ImageView icon = (ImageView) convertView.findViewById(R.id.i_icon);

        switch (currentItem.getCoin()){
            case "BTC": icon.setImageDrawable(activity.getDrawable(R.drawable.btc)); break;
            case "ETH": icon.setImageDrawable(activity.getDrawable(R.drawable.eth)); break;
            default: break;
        }

        Button look = (Button)convertView.findViewById(R.id.i_button);
        look.setOnClickListener(new lookOnClickListener(currentItem.getAddress(),currentItem.getCoin()));

        address.setText(currentItem.getAddress());

        // returns the view for the current row
        return convertView;
    }
    public void add(Wallet o){
        vector.add(o);
    }

    class lookOnClickListener implements View.OnClickListener{
        private String address;
        private String coin;
        public lookOnClickListener(String address, String coin){
            this.address = address;
            this.coin = coin;
        }
        @Override
        public void onClick(View v) {
            Log.i("Signup","Clicked");
            Intent intent = new Intent(activity, AddressDetailsActivity.class);
            intent.putExtra("address", address);
            intent.putExtra("coin", coin);
            intent.putExtra("context", "Look");
            activity.startActivity(intent);

        }
    }


}