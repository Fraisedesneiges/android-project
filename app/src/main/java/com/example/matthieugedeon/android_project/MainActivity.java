package com.example.matthieugedeon.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finding the fetch button by it's id and linking a tailored onClickListener
        Button b1=(Button)findViewById(R.id.fetch_button);
        b1.setOnClickListener(new GetImageOnClickListener());

        AsyncAddressFetcher fetcher = new AsyncAddressFetcher(R.id.course,"course");
        fetcher.execute("https://blockchain.info/ticker");

        populateView();

    }

    //Function found on StackOverflow
    //Construct a string from data extracted of a Stream
    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
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
            AsyncAddressFetcher fetcher = new AsyncAddressFetcher(R.id.display,"wallet");
            fetcher.execute("https://blockchain.info/rawaddr/1F1tAaz5x1HUXrCNLbtMDqcw6o5GNn4xqX");
        }
    }

    //AsyncTask to get the JSON object containing wallet data from URL
    class AsyncAddressFetcher extends AsyncTask<String, Void, JSONObject> {

        JSONObject data;
        int viewItemID;
        String getExpected;

        public AsyncAddressFetcher(int viewItemID, String getExpected){
            super();
            this.viewItemID = viewItemID;
            this.getExpected = getExpected;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Log.i("CP", "In OnPostExecute");

            TextView tv =(TextView) findViewById(viewItemID);
            //tv.setText(jsonObject.toString());


            int value = 0;
            try {
                if(getExpected.equals("wallet")){
                    value = data.getInt("final_balance");
                }
                else if(getExpected.equals("course")){
                    value = data.getJSONObject("USD").getInt("last");
                }
                Log.i("Size",Integer.toString(value));
            }
            catch (Exception e){
                Log.i("ERR", "Value not assigned");
            }

            tv.setText(Integer.toString(value));




            /*
            String s;
            try {
                s = first.getJSONObject("media").getString("m");
            }
            catch (Exception e){
                s = "";
            }

            tv.setText(s);

             */
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            Log.i("Thread","In Thread");
            URL url;
            for(int i = 0; i<strings.length; i++){
                try {
                    url = new URL(strings[i]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        String s = readStream(in);
                        Log.i("JFL", s);
                        Log.i("Size", String.valueOf(s.length()));

                        try{
                            data = new JSONObject(s);
                        }
                        catch(Exception e){
                            Log.i("ERR", "JSON not mounted");
                            data = new JSONObject();
                        }
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return data;
        }
    }

}