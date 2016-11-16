package com.example.ryan.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class localPlayerTwo extends AppCompatActivity {

    Intent intent;
    Intent oldIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_player_two);

        oldIntent = getIntent();
        intent = new Intent(getApplicationContext(),localResult.class);
        intent.putExtra("p1Move",oldIntent.getStringExtra("p1Move"));


        intent.putExtra("p1_wins",oldIntent.getStringExtra("p1_wins"));
        intent.putExtra("p2_wins",oldIntent.getStringExtra("p2_wins"));
        intent.putExtra("draws",oldIntent.getStringExtra("draws"));

        final Button rock = (Button) findViewById(R.id.rock);
        final Button paper = (Button) findViewById(R.id.paper);
        final Button scissors = (Button) findViewById(R.id.scissors);

        rock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.putExtra("p2Move", "rock");
                startActivity(intent);
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.putExtra("p2Move", "paper");
                startActivity(intent);
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent.putExtra("p2Move", "scissors");
                startActivity(intent);
            }
        });
    }

}
