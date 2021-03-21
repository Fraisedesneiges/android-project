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

//AsyncTask to get the JSON object containing wallet data from URL
public class AsyncWalletFetcher extends AsyncTask<String, Void, JSONObject> {

    final static String ETH_API = "https://api.blockcypher.com/v1/eth/main/addrs/";
    final static String BTC_API = "https://blockchain.info/rawaddr/";

    JSONObject data;
    int viewItemID = 0;
    Activity activity;
    String coin;
    ListAdapter adapter;

    public AsyncWalletFetcher(int viewItemID, Activity activity, ListAdapter adapter, String coin){
        super();
        this.viewItemID = viewItemID;
        this.activity = activity;
        this.adapter = adapter;
        this.coin = coin;
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

        switch (coin){
            case "BTC": handleBitcoin(data); break;
            case "ETH": handleEthereum(data); break;
            default: break;
        }

    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        Log.i("Thread","In Thread");
        URL url;
        for(int i = 0; i<strings.length; i++){
            String endpoint = "";
            try {
                switch(coin){
                    case "BTC": endpoint = BTC_API.concat(strings[i]); break;
                    case "ETH": endpoint = ETH_API.concat(strings[i]); break;
                    default: break;
                }
                url = new URL(endpoint);
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

    private void handleEthereum(JSONObject data){

        int value = 0;

        try {
            value = data.getInt("balance");
            JSONArray array = data.getJSONArray("txrefs");
            Log.i("INFO",Integer.toString(array.length()));
            for(int i = 0;i<array.length();i++){
                adapter.add(array.getJSONObject(i));

                //Notify our adapter that its data set has been updated
                adapter.notifyDataSetChanged();
            }

            Log.i("Size",Integer.toString(value));
        }
        catch (Exception e){
            Log.i("ERR", "Value not assigned");
        }

        TextView tv =(TextView) this.activity.findViewById(viewItemID);
        tv.setText(Integer.toString(value));
    }

    private void handleBitcoin(JSONObject data){
        int value = 0;

        try {
            value = data.getInt("final_balance");
            JSONArray array = data.getJSONArray("txs");
            Log.i("INFO",Integer.toString(array.length()));
            for(int i = 0;i<array.length();i++){
                adapter.add(array.getJSONObject(i));

                //Notify our adapter that its data set has been updated
                adapter.notifyDataSetChanged();
            }

            Log.i("Size",Integer.toString(value));
        }
        catch (Exception e){
            Log.i("ERR", "Value not assigned");
        }

        TextView tv =(TextView) this.activity.findViewById(viewItemID);
        tv.setText(Integer.toString(value));
    }
}