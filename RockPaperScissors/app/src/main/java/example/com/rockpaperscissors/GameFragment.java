package example.com.rockpaperscissors;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Björn Dalberg on 2016-04-05.
 */
public class GameFragment extends Activity {
    TextView humanDraw, cpuDraw, humanScore, ties, cpuScore, winner;
    Button rock, paper, scissors;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState) {
//
//        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme);
//        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
//
//        return localInflater.inflate(R.layout.game_fragment, parentViewGroup, false);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_fragment);

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

    private void checkDrawWinner(String humanDraw, String cpuDraw) {
        if (humanDraw.equals(cpuDraw)) {
            winner.setText("TIE!");
            Integer tie = Integer.parseInt(ties.getText().toString());
            tie++;
            ties.setText(tie.toString());
        }
        else if (humanDraw.equals("ROCK")) {
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
        }
        else if (humanDraw.equals("PAPER")) {
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
        }
        else {
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
    }
}
