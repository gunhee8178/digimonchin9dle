package com.example.ryan.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class localPlayerOne extends AppCompatActivity{

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_player_one);

        Intent last_intent = getIntent();

        intent = new Intent(getApplicationContext(), localPass.class);

        intent.putExtra("p1_wins",last_intent.getStringExtra("p1_wins"));
        intent.putExtra("p2_wins",last_intent.getStringExtra("p2_wins"));
        intent.putExtra("draws",last_intent.getStringExtra("draws"));

        final Button rock = (Button) findViewById(R.id.rock);
        final Button paper = (Button) findViewById(R.id.paper);
        final Button scissors = (Button) findViewById(R.id.scissors);

        rock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.putExtra("p1Move", "rock");
                startActivity(intent);
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.putExtra("p1Move", "paper");
                startActivity(intent);
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.putExtra("p1Move", "scissors");
                startActivity(intent);
            }
        });

    }
}
