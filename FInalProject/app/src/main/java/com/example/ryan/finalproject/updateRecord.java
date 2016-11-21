package com.example.ryan.finalproject;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ryan Bern on 5/2/16.
 */
public class updateRecord extends AsyncTask<String,Void,String> {
    @Override
    // value[0] is equal to android unique id
    // this is retrieved by final String android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
    // value[1] is either w,l,d (win, loss, or draw)
    // example calling: updateRecord(unique_id5,"d")
    protected String doInBackground(String... values) {
        try {
            URL url = new URL("http://umdandroid.x10host.com/updateLeaderboard.php?id="+values[0]+"&status="+values[1]);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                Log.i("STR",sb.toString());
                br.close();
            } catch(IOException e) {

            }
        }
        catch(MalformedURLException e) {

        }
        return "";
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
    }
}
