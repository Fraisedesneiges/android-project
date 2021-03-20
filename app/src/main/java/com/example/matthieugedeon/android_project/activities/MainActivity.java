package com.example.matthieugedeon.android_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.classes.AsyncCourseFetcher;
import com.example.matthieugedeon.android_project.classes.AsyncWalletFetcher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding the fetch button by it's id and linking a tailored onClickListener
        Button b1=(Button)findViewById(R.id.fetch_button);
        //b1.setOnClickListener(new GetImageOnClickListener());
        b1.setOnClickListener(new SignupOnClickListener());
        //AsyncCourseFetcher fetcher = new AsyncCourseFetcher(R.id.course,this);
        //fetcher.execute("https://blockchain.info/ticker");

       // populateView();

    }

    private void populateView(){

        //Populate Spinner (Android Developers)
        Spinner spinner = (Spinner) findViewById(R.id.currency_course);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Populate Spinner (Android Developers)
        spinner = (Spinner) findViewById(R.id.coin_course);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.coin_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    //Tailored onClickListener that launch our parameterized AsyncTask
    class GetImageOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.i("Button","Clicked");
            Intent intent = new Intent(MainActivity.this, AddressDetailsActivity.class);
            intent.putExtra("address", "https://blockchain.info/rawaddr/1F1tAaz5x1HUXrCNLbtMDqcw6o5GNn4xqX");
            startActivity(intent);

        }
    }
    //
    //------DB Stuff--------
    //
    class SignupOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.i("Button","Clicked");
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            //intent.putExtra("address", "https://blockchain.info/rawaddr/1F1tAaz5x1HUXrCNLbtMDqcw6o5GNn4xqX");
            startActivity(intent);

        }
    }

}