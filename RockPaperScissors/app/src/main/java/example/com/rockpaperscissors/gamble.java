package example.com.rockpaperscissors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import static example.com.rockpaperscissors.Money.money;

public class gamble extends Activity {

        TextView humanDraw, cpuDraw, humanScore, ties, cpuScore, winner, txmoney;
        Button rock, paper, scissors, showMoney;
        int money = 500;
        int best = money;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.play_rps);

            rock = (Button) findViewById(R.id.rock);
            paper = (Button) findViewById(R.id.paper);
            scissors = (Button) findViewById(R.id.scissors);
            showMoney = (Button) findViewById(R.id.showMoney);

            humanDraw = (TextView) findViewById(R.id.humandraw);
            cpuDraw = (TextView) findViewById(R.id.cpudraw);
            winner = (TextView) findViewById(R.id.winner);
            humanScore = (TextView) findViewById(R.id.humanscore);
            ties = (TextView) findViewById(R.id.tiescount);
            cpuScore = (TextView) findViewById(R.id.cpuscore);
            txmoney = (TextView) findViewById(R.id.money);

//            Integer money = Integer.parseInt(txmoney.getText().toString());
//            money = Money.money;
//            txmoney.setText("500");
            rock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    humanDraw.setText("ROCK");
//                    Integer money = Integer.parseInt(txmoney.getText().toString());
                    money -= 100;
//                    txmoney.setText(money.toString());
                    cpuDraw.setText(GameAI.getDraw());
                    checkDrawWinner("ROCK", cpuDraw.getText().toString());
                }
            });

            paper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    humanDraw.setText("PAPER");
//                    Integer money = Integer.parseInt(txmoney.getText().toString());
                    money -= 100;
//                    txmoney.setText(money.toString());
                    cpuDraw.setText(GameAI.getDraw());
                    checkDrawWinner("PAPER", cpuDraw.getText().toString());

                }
            });

            scissors.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    humanDraw.setText("SCISSORS ");
//                    Integer money = Integer.parseInt(txmoney.getText().toString());
                    money -= 100;
//                    txmoney.setText(money.toString());
                    cpuDraw.setText(GameAI.getDraw());
                    checkDrawWinner("SCISSORS", cpuDraw.getText().toString());

                }
            });
            showMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Toast.makeText(getApplicationContext(), money+"원이 있습니다", Toast.LENGTH_LONG).show();

                }
            });


        }



        private void checkDrawWinner(String humanDraw, String cpuDraw) {
            if (humanDraw.equals(cpuDraw)) {
                winner.setText("TIE! DO It ONEMORE!");
//                Integer money = Integer.parseInt(txmoney.getText().toString());
                money += 100;
//                txmoney.setText(money.toString());
                Integer tie = Integer.parseInt(ties.getText().toString());
                tie++;
                ties.setText(tie.toString());
            } else if (humanDraw.equals("ROCK")) {
                if (cpuDraw.equals("SCISSORS")) {
                    winner.setText("WIN!");
                    Integer humanWins = Integer.parseInt(humanScore.getText().toString());
                    humanWins++;
//                    Integer money = Integer.parseInt(txmoney.getText().toString());
                    money+=200;
                    if(money > best){ best = money;}
                    humanScore.setText(humanWins.toString());
//                    txmoney.setText(money.toString());
                } else {
                    winner.setText("LOSE!");
                    Integer cpuWins = Integer.parseInt(cpuScore.getText().toString());
                    cpuWins++;
                    cpuScore.setText(cpuWins.toString());
                }
            } else if (humanDraw.equals("PAPER")) {
                if (cpuDraw.equals("ROCK")) {
                    winner.setText("WIN!");
                    Integer humanWins = Integer.parseInt(humanScore.getText().toString());
                    humanWins++;
//                    Integer money = Integer.parseInt(txmoney.getText().toString());
                    money+=200;
                    if(money > best){ best = money;}
                    humanScore.setText(humanWins.toString());
//                    txmoney.setText(money.toString());
                } else {
                    winner.setText("LOSE!");
                    Integer cpuWins = Integer.parseInt(cpuScore.getText().toString());
                    cpuWins++;
                    cpuScore.setText(cpuWins.toString());
                }
            } else {
                if (cpuDraw.equals("PAPER")) {
                    winner.setText("WIN!");
                    Integer humanWins = Integer.parseInt(humanScore.getText().toString());
                    humanWins++;
//                    Integer money = Integer.parseInt(txmoney.getText().toString());
                    money+=200;
                    if(money > best){ best = money;}
                    humanScore.setText(humanWins.toString());
//                    txmoney.setText(money.toString());
                } else {
                    winner.setText("LOSE!");
                    Integer cpuWins = Integer.parseInt(cpuScore.getText().toString());
                    cpuWins++;
                    cpuScore.setText(cpuWins.toString());
                }
            }

            if(money == 0){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setTitle("Game Over!");
                alert.setMessage("Your Highest money : "+ best );
                alert.show();
            }
        }
    }

