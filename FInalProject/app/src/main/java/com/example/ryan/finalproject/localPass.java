package com.example.ryan.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class localPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_pass);

        final Intent intent = getIntent();

        final Button passButton = (Button) findViewById(R.id.pass);
        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent localPlayerTwo = new Intent(getApplicationContext(),localPlayerTwo.class);
                localPlayerTwo.putExtra("p1Move",intent.getStringExtra("p1Move"));


                localPlayerTwo.putExtra("p1_wins",intent.getStringExtra("p1_wins"));
                localPlayerTwo.putExtra("p2_wins",intent.getStringExtra("p2_wins"));
                localPlayerTwo.putExtra("draws",intent.getStringExtra("draws"));

                startActivity(localPlayerTwo);
            }
        });
    }
}
