package com.example.matthieugedeon.android_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.classes.AsyncAddressFetcher;
import com.example.matthieugedeon.android_project.classes.ListAdapter;

import org.json.JSONObject;

import java.util.Vector;

public class AddressDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");

        //Getting the list by its id and linking our tailored adapter to it
        ListView list = (ListView)findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter(this,new Vector<JSONObject>());
        list.setAdapter(adapter);


        AsyncAddressFetcher fetcher = new AsyncAddressFetcher("wallet",this,adapter);
        fetcher.execute(address);
    }
}