package com.example.matthieugedeon.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        dbHelp = new DataBaseHelper(this);

        //Finding the fetch button by it's id and linking a tailored onClickListener
        Button b1 = (Button) findViewById(R.id.fetch_button);
        b1.setOnClickListener(new GetImageOnClickListener());

        AsyncAddressFetcher fetcher = new AsyncAddressFetcher(R.id.course, "course");
        fetcher.execute("https://blockchain.info/ticker");
    }

    //Function found on StackOverflow
    //Construct a string from data extracted of a Stream
    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }


    //Tailored onClickListener that launch our parameterized AsyncTask
    class GetImageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.i("Button", "Clicked");
            AsyncAddressFetcher fetcher = new AsyncAddressFetcher(R.id.display, "wallet");
            fetcher.execute("https://blockchain.info/rawaddr/1F1tAaz5x1HUXrCNLbtMDqcw6o5GNn4xqX");
        }
    }

    //AsyncTask to get the JSON object containing wallet data from URL
    class AsyncAddressFetcher extends AsyncTask<String, Void, JSONObject> {

        JSONObject data;
        int viewItemID;
        String getExpected;

        public AsyncAddressFetcher(int viewItemID, String getExpected) {
            super();
            this.viewItemID = viewItemID;
            this.getExpected = getExpected;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Log.i("CP", "In OnPostExecute");

            TextView tv = (TextView) findViewById(viewItemID);
            //tv.setText(jsonObject.toString());


            int value = 0;
            try {
                if (getExpected.equals("wallet")) {
                    value = data.getInt("final_balance");
                } else if (getExpected.equals("course")) {
                    value = data.getJSONObject("USD").getInt("last");
                }
                Log.i("Size", Integer.toString(value));
            } catch (Exception e) {
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
            Log.i("Thread", "In Thread");
            URL url;
            for (int i = 0; i < strings.length; i++) {
                try {
                    url = new URL(strings[i]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        String s = readStream(in);
                        Log.i("JFL", s);
                        Log.i("Size", String.valueOf(s.length()));

                        try {
                            data = new JSONObject(s);
                        } catch (Exception e) {
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

    //
    // -----------Database stuff------------
    //

    DataBaseHelper dbHelp;

    //small function to easily show toast messages
    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //possibility to concat and then split the credentials but heh
    //Adds a user with his password (needs some additional verification if it exists etc)
    public void addData(String newUser, String newPassword) {
        boolean add = dbHelp.addUser(newUser, newPassword);

        if (add) {
            toast("Added successfully");
        } else {
            toast("Failed to add the user");
        }
    }

    //Getting the cursor containing the data of our user
    public void getData(String un) {
        Cursor data = dbHelp.getUserData(un);
        int i = 0;
        if (data.getCount() == 0) {
            toast("No data to show");
        } else {
            do {
                toast(data.getString(i)); //toasting the values of our user while there are collumns to do so
            } while (data.moveToNext());
        }
    }

}