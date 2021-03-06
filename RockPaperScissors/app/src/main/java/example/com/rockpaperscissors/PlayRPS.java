package example.com.rockpaperscissors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class is for playing Rock-Paper-Scissors.
 * @author bring from github. BaeSeongHun. HanGunHee
 */
public class PlayRPS extends Activity {
    TextView humanDraw, cpuDraw, humanScore, ties, cpuScore, winner;
    Button rock, paper, scissors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_rps);

        rock = (Button) findViewById(R.id.rock);
        paper = (Button) findViewById(R.id.paper);
        scissors = (Button) findViewById(R.id.scissors);

        humanDraw = (TextView) findViewById(R.id.humandraw);
        cpuDraw = (TextView) findViewById(R.id.cpudraw);
        winner = (TextView) findViewById(R.id.winner);
        humanScore = (TextView) findViewById(R.id.humanscore);
        ties = (TextView) findViewById(R.id.tiescount);
        cpuScore = (TextView) findViewById(R.id.cpuscore);

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humanDraw.setText("ROCK");
                cpuDraw.setText(GameAI.getDraw());
                checkDrawWinner("ROCK", cpuDraw.getText().toString());
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humanDraw.setText("PAPER");
                cpuDraw.setText(GameAI.getDraw());
                checkDrawWinner("PAPER", cpuDraw.getText().toString());
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humanDraw.setText("SCISSORS ");
                cpuDraw.setText(GameAI.getDraw());
                checkDrawWinner("SCISSORS", cpuDraw.getText().toString());
            }
        });
    }

    /**
     * This method shows
     * 1) who wins
     * 2) score of human and cpu
     * 3) when finish the game, show human's win and draw score, take user name, and save the data on the class RankingR
     * @param humanDraw what human hand
     * @param cpuDraw what cpu hand
     */
    private void checkDrawWinner(String humanDraw, String cpuDraw) {
        if (humanDraw.equals(cpuDraw)) {
            winner.setText("TIE!");
            Integer tie = Integer.parseInt(ties.getText().toString());
            tie++;
            ties.setText(tie.toString());
        } else if (humanDraw.equals("ROCK")) {
            if (cpuDraw.equals("SCISSORS")) {
                winner.setText("ROCK WINS!");
                Integer humanWins = Integer.parseInt(humanScore.getText().toString());
                humanWins++;
                humanScore.setText(humanWins.toString());
            } else {
                winner.setText("PAPER WINS!");
                Integer cpuWins = Integer.parseInt(cpuScore.getText().toString());
                cpuWins++;
                cpuScore.setText(cpuWins.toString());
            }
        } else if (humanDraw.equals("PAPER")) {
            if (cpuDraw.equals("ROCK")) {
                winner.setText("PAPER WINS!");
                Integer humanWins = Integer.parseInt(humanScore.getText().toString());
                humanWins++;
                humanScore.setText(humanWins.toString());
            } else {
                winner.setText("SCISSORS WINS!");
                Integer cpuWins = Integer.parseInt(cpuScore.getText().toString());
                cpuWins++;
                cpuScore.setText(cpuWins.toString());
            }
        } else {
            if (cpuDraw.equals("PAPER")) {
                winner.setText("SCISSORS WINS");
                Integer humanWins = Integer.parseInt(humanScore.getText().toString());
                humanWins++;
                humanScore.setText(humanWins.toString());
            } else {
                winner.setText("PAPER WINS!");
                Integer cpuWins = Integer.parseInt(cpuScore.getText().toString());
                cpuWins++;
                cpuScore.setText(cpuWins.toString());
            }
        }

        if(Integer.parseInt(cpuScore.getText().toString())==Life.life){
            AlertDialog.Builder alert = new AlertDialog.Builder(PlayRPS.this);
            alert.setTitle("Game Over!");
            alert.setMessage("Your score is "+Integer.parseInt(humanScore.getText().toString())+" wins, "+Integer.parseInt(ties.getText().toString())+" ties!");

            final EditText edit = new EditText(this);
            edit.setHint("Enter your name");
            alert.setView(edit);

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = edit.getText().toString();
                    int score = Integer.parseInt(humanScore.getText().toString());
                    int draw = Integer.parseInt(ties.getText().toString());
                    RankingR rank = new RankingR(name, score, draw);
                    MainActivity.ranklistR.add(rank);
                    int size = MainActivity.ranklistR.size();
                    for(int i=0; i<size-1; i++) {
                        for (int j = 0; j<size-1-i; j++) {
                            RankingR irank = MainActivity.ranklistR.get(j);
                            RankingR iirank = MainActivity.ranklistR.get(j+1);
                            if (irank.getScore() < iirank.getScore() || (irank.getScore() == iirank.getScore() && irank.getDraw() < iirank.getDraw())) {
                                RankingR swap_rank = irank;
                                irank.setRanking(iirank);
                                iirank.setRanking(swap_rank);
                            }
                        }
                    }

                    SharedPreferences prefs = getSharedPreferences("RankR", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    for(int i=0; i<size; i++) {
                        editor.putString(i + "rank_name", MainActivity.ranklistR.get(i).getName());
                        editor.putInt(i + "rank_score", MainActivity.ranklistR.get(i).getScore());
                        editor.putInt(i + "rank_draw", MainActivity.ranklistR.get(i).getDraw());
                    }
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            });
            alert.show();
        }
    }
}
