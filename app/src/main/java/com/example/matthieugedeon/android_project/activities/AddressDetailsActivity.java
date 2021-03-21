package com.example.matthieugedeon.android_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.classes.AsyncWalletFetcher;
import com.example.matthieugedeon.android_project.classes.ListAdapter;

import org.json.JSONObject;

import java.util.Vector;

//TODO: The transactions are well fetched but need to be processed and styled in a better way

public class AddressDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String coin = intent.getStringExtra("coin");
        ImageView icon = (ImageView)findViewById(R.id.address_icon);

        switch(coin){
            case "BTC": icon.setImageDrawable(getDrawable(R.drawable.btc)); break;
            case "ETH": icon.setImageDrawable(getDrawable(R.drawable.eth)); break;
            default: break;
        }

        //Getting the list by its id and linking our tailored adapter to it
        ListView list = (ListView)findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter(this,new Vector<JSONObject>());
        list.setAdapter(adapter);


        AsyncWalletFetcher fetcher = new AsyncWalletFetcher(R.id.ad_balance,this,adapter);
        fetcher.execute(address);
    }
}