package com.example.matthieugedeon.android_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.classes.AsyncWalletFetcher;
import com.example.matthieugedeon.android_project.classes.ListAdapter;
import com.example.matthieugedeon.android_project.classes.SessionData;
import com.example.matthieugedeon.android_project.fragments.AddAddressFragment;
import com.example.matthieugedeon.android_project.fragments.MainBaseFragment;

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
        String context = intent.getStringExtra("context");
        ImageView icon = (ImageView)findViewById(R.id.address_icon);
        TextView addressValue = (TextView)findViewById(R.id.address_val);
        addressValue.setText(coin.concat(address));

        switch(coin){
            case "BTC":
                icon.setImageDrawable(getDrawable(R.drawable.btc));

                break;
            case "ETH": icon.setImageDrawable(getDrawable(R.drawable.eth)); break;
            default: break;
        }

        //Getting the list by its id and linking our tailored adapter to it
        ListView list = (ListView)findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter(this,new Vector<JSONObject>(),coin);
        list.setAdapter(adapter);


        AsyncWalletFetcher fetcher = new AsyncWalletFetcher(R.id.ad_balance,this,adapter,coin);
        fetcher.execute(address);

        if(SessionData.isConnected() && !context.equals("Look")){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.sub_container, AddAddressFragment.class,null)
                    .commit();


        }
    }
}