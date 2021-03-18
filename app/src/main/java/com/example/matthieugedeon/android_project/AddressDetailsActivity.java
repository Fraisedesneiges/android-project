package com.example.matthieugedeon.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AddressDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");

        AsyncAddressFetcher fetcher = new AsyncAddressFetcher(R.id.ad_balance,"wallet",this);
        fetcher.execute(address);
    }
}