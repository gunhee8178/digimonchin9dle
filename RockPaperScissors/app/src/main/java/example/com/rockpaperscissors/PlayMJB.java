package example.com.rockpaperscissors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class is for playing Mukzzibba.
 * @author BaeSeongHun
 */
public class PlayMJB extends Activity{
    Button rock, paper, scissors;
    TextView human_hand, cpu_hand, winner, human_score, cpu_score;
    Integer your_turn=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_mjb);

        rock = (Button) findViewById(R.id.mjb_rock);
        paper = (Button) findViewById(R.id.mjb_paper);
        scissors = (Button) findViewById(R.id.mjb_scissors);
        human_hand = (TextView) findViewById(R.id.mjb_human_hand);
        cpu_hand = (TextView) findViewById(R.id.mjb_cpu_hand);
        winner = (TextView) findViewById(R.id.mjb_winner);
        human_score = (TextView) findViewById(R.id.mjb_human_score);
        cpu_score = (TextView) findViewById(R.id.mjb_cpu_score);

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                human_hand.setText("ROCK");
                cpu_hand.setText(GameAI.getDraw());
                checkDrawwinner("ROCK", cpu_hand.getText().toString());
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                human_hand.setText("PAPER");
                cpu_hand.setText(GameAI.getDraw());
                checkDrawwinner("PAPER", cpu_hand.getText().toString());
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                human_hand.setText("SCISSORS ");
                cpu_hand.setText(GameAI.getDraw());
                checkDrawwinner("SCISSORS", cpu_hand.getText().toString());
            }
        });
    }

    /**
     * This method shows
     * 1) who wins
     * 2) change of human and cpu score
     * 3) when finish the game, make a dialog, take user name, and save this data on class RankingM.
     * @param human_hand what we hand
     * @param cpu_hand what cpu hand
     */
    private void checkDrawwinner(String human_hand, String cpu_hand) {
        if (human_hand.equals(cpu_hand) && your_turn==0) {
            winner.setText("Again!");
        } else if (human_hand.equals(cpu_hand) && your_turn==1) {
            winner.setText("You Wins!");
            Integer int_human_score = Integer.parseInt(human_score.getText().toString());
            int_human_score++;
            human_score.setText(int_human_score.toString());
            your_turn = 0;
        } else if (human_hand.equals(cpu_hand) && your_turn==2) {
            winner.setText("CPU Wins!");
            Integer int_cpu_score = Integer.parseInt(cpu_score.getText().toString());
            int_cpu_score++;
            cpu_score.setText(int_cpu_score.toString());
            your_turn = 0;
            if(int_cpu_score==Life.life){
                AlertDialog.Builder alert = new AlertDialog.Builder(PlayMJB.this);
                alert.setTitle("Game Over!");
                alert.setMessage("Your score is "+Integer.parseInt(human_score.getText().toString())+" wins!");

                final EditText edit = new EditText(this);
                edit.setInputType(InputType.TYPE_CLASS_TEXT);
                edit.setHint("Enter your name");
                alert.setView(edit);

                edit.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if(keyCode == event.KEYCODE_ENTER) {
                            return true;
                        }
                        return false;
                    }
                });

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edit.getText().toString();
                        int score = Integer.parseInt(human_score.getText().toString());
                        RankingM rank = new RankingM(name, score);
                        MainActivity.ranklistM.add(rank);
                        int size = MainActivity.ranklistM.size();
                        for(int i=0; i<size-1; i++) {
                            for (int j = 0; j<size-1-i; j++) {
                                RankingM irank = MainActivity.ranklistM.get(j);
                                RankingM iirank = MainActivity.ranklistM.get(j+1);
                                if (irank.getScore() < iirank.getScore()) {
                                    RankingM swap_rank = irank;
                                    irank.setRanking(iirank);
                                    iirank.setRanking(swap_rank);
                                }
                            }
                        }

                        SharedPreferences prefs = getSharedPreferences("RankM", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        for(int i=0; i<size; i++) {
                            editor.putString(i + "rank_name", MainActivity.ranklistM.get(i).getName());
                            editor.putInt(i + "rank_score", MainActivity.ranklistM.get(i).getScore());
                        }
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
                alert.show();
            }
        } else if (human_hand.equals("ROCK")) {
            if (cpu_hand.equals("SCISSORS")) {
                winner.setText("Your Turn!");
                your_turn = 1;
            } else {
                winner.setText("CPU Turn!");
                your_turn = 2;
            }
        } else if (human_hand.equals("PAPER")) {
            if (cpu_hand.equals("ROCK")) {
                winner.setText("Your Turn!");
                your_turn = 1;
            } else {
                winner.setText("CPU Turn!");
                your_turn = 2;
            }
        } else {
            if (cpu_hand.equals("PAPER")) {
                winner.setText("Your Turn!");
                your_turn = 1;
            } else {
                winner.setText("CPU Turn!");
                your_turn = 2;
            }
        }
    }
}