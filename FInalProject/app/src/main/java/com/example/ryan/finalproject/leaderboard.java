package com.example.ryan.finalproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class leaderboard extends ListActivity {
    LeaderboardAdapter adapter = null;
    ArrayList<LeaderBoxes> leadersArray = new ArrayList<>();
    private int index = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new LeaderboardAdapter(getApplicationContext());

        View headerView = (View) LayoutInflater.from(getApplicationContext()).inflate(R.layout.leaderboard, null);
        getListView().addHeaderView(headerView);

        getListView().setAdapter(adapter);

        final Button back = (Button) findViewById(R.id.LeaderBack);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        new getLeaders().execute("");

    }


    class getLeaders extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL("http://umdandroid.x10host.com/getLeaders.php");
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

                    return sb.toString();

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
            InputStream stream = new ByteArrayInputStream(result.getBytes(Charset.forName("UTF-8")));
            try {
                List list = readJsonStream(stream);
                for(int i = 0; i < list.size(); i++) {
                    User tmp = (User) list.get(i);
                    LeaderBoxes leaderItem = new LeaderBoxes(Integer.toString(i+1),tmp.user_name,tmp.wins,tmp.losses,tmp.draws);
                    adapter.add(leaderItem);
                }
            }
            catch(IOException e) {
            }
        }
    }

    class User {
        public String user_name;
        public String wins;
        public String losses;
        public String draws;

        public User(String user_name, String wins, String losses, String draws) {
            this.user_name = user_name;
            this.wins = wins;
            this.losses = losses;
            this.draws = draws;
        }
    }

    public List readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public List readMessagesArray(JsonReader reader) throws IOException {
        List messages = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    public User readMessage(JsonReader reader) throws IOException {
        String user_name = null;
        String wins = null;
        String losses = null;
        String draws = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("user_name")) {
                user_name = reader.nextString();
            } else if (name.equals("wins")) {
                wins = reader.nextString();
            } else if (name.equals("losses")) {
                losses = reader.nextString();
            } else if (name.equals("draws")) {
                draws = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new User(user_name, wins, losses, draws);
    }
}
