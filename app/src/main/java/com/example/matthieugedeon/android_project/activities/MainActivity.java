package com.example.matthieugedeon.android_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.classes.AsyncCourseFetcher;
import com.example.matthieugedeon.android_project.classes.AsyncWalletFetcher;
import com.example.matthieugedeon.android_project.fragments.ButtonBaseFragment;
import com.example.matthieugedeon.android_project.fragments.ButtonConnectedFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding the fetch button by it's id and linking a tailored onClickListener
        Button b1=(Button)findViewById(R.id.fetch_button);
        b1.setOnClickListener(new AddressDetailsOnClickListener());

        b1=(Button)findViewById(R.id.open_login);
        b1.setOnClickListener(new LogInOnClickListener());

        b1=(Button)findViewById(R.id.open_signup);
        b1.setOnClickListener(new SignUpOnClickListener());

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment, ButtonBaseFragment.class, null)
                .commit();

        populateView();

        Spinner spinner = (Spinner)findViewById(R.id.currency_course);
        String text = spinner.getSelectedItem().toString();
        AsyncCourseFetcher fetcher = new AsyncCourseFetcher(R.id.course,text,this);
        spinner = (Spinner)findViewById(R.id.coin_course);
        text = spinner.getSelectedItem().toString();
        fetcher.execute(text);

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

        SpinnerListener sl = new SpinnerListener(this);
        spinner.setOnItemSelectedListener(sl);

        //Populate Spinner (Android Developers)
        spinner = (Spinner) findViewById(R.id.coin_course);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.coin_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(sl);

        //Populate Spinner (Android Developers)
        spinner = (Spinner) findViewById(R.id.coin_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.coin_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(sl);
    }

    //Tailored onClickListener that launch our parameterized AsyncTask
    class AddressDetailsOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.i("Button","Clicked");
            Intent intent = new Intent(MainActivity.this, AddressDetailsActivity.class);
            intent.putExtra("address", "https://blockchain.info/rawaddr/1F1tAaz5x1HUXrCNLbtMDqcw6o5GNn4xqX");

            Spinner spinner = (Spinner)findViewById(R.id.coin_type);
            String text = spinner.getSelectedItem().toString();
            intent.putExtra("coin", text);
            startActivity(intent);

        }
    }

    public class SpinnerListener implements AdapterView.OnItemSelectedListener {

        Activity activity;

        public SpinnerListener(Activity activity){
            this.activity = activity;
        }

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

            if (parent.getId() == R.id.coin_course) {
                ImageView iv = (ImageView) findViewById(R.id.course_icon);
                String s = (String)parent.getItemAtPosition(pos);

                Spinner spinner = (Spinner)findViewById(R.id.currency_course) ;
                String text = (String)spinner.getSelectedItem().toString();

                AsyncCourseFetcher fetcher;
                switch (s) {
                    case "1 BTC":
                        iv.setImageDrawable(getDrawable(R.drawable.btc));
                        fetcher = new AsyncCourseFetcher(R.id.course,text,activity);
                        fetcher.execute(s);
                        break;
                    case "1 ETH":
                        iv.setImageDrawable(getDrawable(R.drawable.eth));
                        fetcher = new AsyncCourseFetcher(R.id.course,text,activity);
                        fetcher.execute(s);
                        break;
                    default: break;
                }
            }
            else if(parent.getId() == R.id.currency_course)
            {
                String text = (String)parent.getSelectedItem().toString();
                AsyncCourseFetcher fetcher = new AsyncCourseFetcher(R.id.course,text,activity);

                Spinner spinner = (Spinner)findViewById(R.id.coin_course) ;
                text = (String)spinner.getSelectedItem().toString();
                fetcher.execute(text);
            }
            else if(parent.getId() == R.id.coin_type){
                ImageView iv = (ImageView) findViewById(R.id.search_icon);
                String s = (String)parent.getItemAtPosition(pos);

                switch (s) {
                    case "BTC":
                        iv.setImageDrawable(getDrawable(R.drawable.btc));
                        break;
                    case "ETH":
                        iv.setImageDrawable(getDrawable(R.drawable.eth));
                        break;
                    default: break;
                }
            }
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    class SignUpOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.i("Signup","Clicked");
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);

        }
    }

    class LogInOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.i("Login","Clicked");
            //Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            //startActivity(intent);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, ButtonConnectedFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name") // name can be null
                    .commit();

        }
    }


}