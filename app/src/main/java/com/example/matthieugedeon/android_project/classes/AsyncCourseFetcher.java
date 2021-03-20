package com.example.matthieugedeon.android_project.classes;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.matthieugedeon.android_project.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncCourseFetcher  extends AsyncTask<String, Void, JSONObject> {

    final static String COURSE_URL = "https://api.coinbase.com/v2/exchange-rates?currency=";

    JSONObject data;
    int viewItemID;
    String currency;
    Activity activity;

    public AsyncCourseFetcher(int viewItemID, String currency, Activity activity){
        super();
        this.viewItemID = viewItemID;
        this.activity = activity;
        this.currency = currency;
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

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        Log.i("CP", "In OnPostExecute");

        TextView tv =(TextView) this.activity.findViewById(viewItemID);

        String value = "";
        try {
            switch(currency){
                case"$ USD": value = data.getJSONObject("data").getJSONObject("rates").getString("USD"); break;
                case"€ EUR": value = data.getJSONObject("data").getJSONObject("rates").getString("EUR"); break;
                case"£ GBP": value = data.getJSONObject("data").getJSONObject("rates").getString("GBP"); break;
                default: break;
            }
            Log.i("Size",value);
        }
        catch (Exception e){
            Log.i("ERR", "Value not assigned");
        }

        tv.setText(value);

    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        Log.i("Thread","In Thread");
        URL url;
        for(int i = 0; i<strings.length; i++){
            try {
                url = new URL(COURSE_URL+strings[i]);
                Log.i("ADDR",strings[i]);
                switch (strings[i]){
                    case "1 BTC": url = new URL(COURSE_URL.concat("BTC")); break;
                    case "1 ETH": url = new URL(COURSE_URL+"ETH"); break;
                    case "1 DOGE": url = new URL(COURSE_URL+"DOGE"); break;
                    default: break;
                }
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