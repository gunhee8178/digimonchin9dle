package com.example.ryan.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class localResult extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_result);

        final String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String username = settings.getString("username","");

        final Intent intent = getIntent();
        final Intent playAgainIntent = new Intent(getApplicationContext(),localPlayerOne.class);
        final Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);

        Button winner = (Button) findViewById(R.id.winner);

        TextView p1_ratio = (TextView) findViewById(R.id.p1);
        TextView p2_ratio = (TextView) findViewById(R.id.p2);

        String p1_move = intent.getStringExtra("p1Move");
        String p2_move = intent.getStringExtra("p2Move");
        Boolean p1_winner = false;
        Boolean draw = false;

        Button playAgain = (Button) findViewById(R.id.playAgain);
        Button home = (Button) findViewById(R.id.home);

        int p1_wins = Integer.parseInt(intent.getStringExtra("p1_wins"));
        int p2_wins = Integer.parseInt(intent.getStringExtra("p2_wins"));
        int draws = Integer.parseInt(intent.getStringExtra("draws"));


        if(p1_move.equals(p2_move)) {
            winner.setText("DRAW");
            draws = draws + 1;
            draw = true;
            new updateRecord().execute(android_id,"d");
        }
        if(p1_move.equals("rock") && p2_move.equals("scissors")) {
            p1_winner = true;
        }
        if(p1_move.equals("scissors") && p2_move.equals("paper")) {
            p1_winner = true;
        }
        if(p1_move.equals("paper") && p2_move.equals("rock")) {
            p1_winner = true;
        }

        if(p1_winner) {
            winner.setText(username+" wins!");
            p1_wins = p1_wins + 1;
            new updateRecord().execute(android_id,"w");
        }
        if(!p1_winner && !draw) {
            winner.setText("Player 2 wins!");
            p2_wins = p2_wins + 1;
            new updateRecord().execute(android_id,"l");
        }

        p1_ratio.append(p1_wins+"-"+p2_wins+"-"+draws);
        p2_ratio.append(p2_wins+"-"+p1_wins+"-"+draws);

        playAgainIntent.putExtra("p1_wins",String.valueOf(p1_wins));
        playAgainIntent.putExtra("p2_wins",String.valueOf(p2_wins));
        playAgainIntent.putExtra("draws",String.valueOf(draws));

        playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(playAgainIntent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(homeIntent);
            }
        });


    }
}
