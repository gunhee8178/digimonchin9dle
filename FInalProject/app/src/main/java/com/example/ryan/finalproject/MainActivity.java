package com.example.ryan.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // final GifView gifView = (GifView) findViewById(R.id.imageView);
        final Button localPlay = (Button) findViewById(R.id.localPlay);
        final Button networkPlay = (Button) findViewById(R.id.networkPlay);
        final Button leaderboard = (Button) findViewById(R.id.leaderboard);

        localPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent localIntent = new Intent(getApplicationContext(),localPlayerOne.class);
                localIntent.putExtra("p1_wins","0");
                localIntent.putExtra("p2_wins","0");
                localIntent.putExtra("draws","0");
                startActivity(localIntent);
            }
        });
        
        networkPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent networkIntent = new Intent(getApplicationContext(),WiFiDirectActivity.class);
                networkIntent.putExtra("p1_wins","0");
                networkIntent.putExtra("p2_wins","0");
                networkIntent.putExtra("draws","0");
                startActivity(networkIntent);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent leaderIntent = new Intent(getApplicationContext(),leaderboard.class);
                startActivity(leaderIntent);
            }
        });

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String username = settings.getString("username","");

        if(username.isEmpty()) {
            final String android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            final EditText user = new EditText(this);
            builder.setView(user);
            builder.setMessage("Enter new username")
                    .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String user_value = user.getText().toString();
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("username",user_value);
                            editor.commit();
                            new addUserToDatabase().execute(android_id,user_value);
                            Toast toast = Toast.makeText(getApplicationContext(),"Added username to database",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
            builder.show();
        }
    }

    class addUserToDatabase extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... values) {
            try {
                URL url = new URL("http://umdandroid.x10host.com/createAccount.php?id="+values[0]+"&user_name="+values[1]);
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
}
