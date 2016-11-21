package com.example.ryan.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class networkResult extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_result);

        final Intent intent = getIntent();
        //final Intent playAgainIntent = new Intent(getApplicationContext(),networkPlayer.class);
        final Intent homeIntent = new Intent(getApplicationContext(),WiFiDirectActivity.class);

        Button winner = (Button) findViewById(R.id.winner);

        TextView p1_ratio = (TextView) findViewById(R.id.p1);
        TextView p2_ratio = (TextView) findViewById(R.id.p2);

        String p1_move = intent.getStringExtra("p1Move");
        String p2_move = intent.getStringExtra("p2Move");
        Boolean p1_winner = false;
        Boolean draw = false;

        //Button playAgain = (Button) findViewById(R.id.playAgain);
        Button home = (Button) findViewById(R.id.home);

        String p1_w_s = intent.getStringExtra("p1_wins");
        String p2_w_s = intent.getStringExtra("p2_wins");
        String draw_s = intent.getStringExtra("draws");
        int p1_wins = 0;
        if(p1_w_s != null)
            p1_wins = Integer.parseInt(p1_w_s);
        int p2_wins = 0;
        if(p2_w_s != null)
            p2_wins = Integer.parseInt(p2_w_s);
        int draws = 0;
        if(draw_s != null)
            draws = Integer.parseInt(draw_s);


            if (p1_move.equals(p2_move)) {
                winner.setText("DRAW!");
                draws = draws + 1;
                draw = true;
            }
            if (p1_move.equals("rock") && p2_move.equals("scissors")) {
                p1_winner = true;
            }
            if (p1_move.equals("scissors") && p2_move.equals("paper")) {
                p1_winner = true;
            }
            if (p1_move.equals("paper") && p2_move.equals("rock")) {
                p1_winner = true;
            }
            if (!p1_move.equals("nothing") && p2_move.equals("nothing")) {
                p1_winner = true;
            }

            if (p1_winner) {
                winner.setText("You win!");
                p1_wins = p1_wins + 1;
            }
            if (!p1_winner && !draw) {
                winner.setText("You Lose!");
                p2_wins = p2_wins + 1;
            }

        //p1_ratio.append(p1_wins+"-"+p2_wins+"-"+draws);
        //p2_ratio.append(p2_wins+"-"+p1_wins+"-"+draws);

        //playAgainIntent.putExtra("p1_wins",String.valueOf(p1_wins));
        //playAgainIntent.putExtra("p2_wins",String.valueOf(p2_wins));
        //playAgainIntent.putExtra("draws",String.valueOf(draws));

        /*playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(playAgainIntent);
            }
        });*/

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //startActivity(homeIntent);
                finish();
            }
        });


    }
}
