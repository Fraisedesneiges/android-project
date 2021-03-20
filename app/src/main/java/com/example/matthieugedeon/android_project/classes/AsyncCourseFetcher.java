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

        int value = 0;
        try {
            switch(currency){
                case"$ USD": value = data.getJSONObject("USD").getInt("last"); break;
                case"€ EUR": value = data.getJSONObject("EUR").getInt("last"); break;
                case"£ GBP": value = data.getJSONObject("GBP").getInt("last"); break;
                default: break;
            }
            Log.i("Size",Integer.toString(value));
        }
        catch (Exception e){
            Log.i("ERR", "Value not assigned");
        }

        tv.setText(Integer.toString(value));

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