package example.com.rockpaperscissors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends Activity {
    static final String STATE_PLAY_BUTTON = "play_button";
    TextView title;
    Button playRPS, playMJB, quit;
    static ArrayList<Ranking> ranklist = new ArrayList<Ranking>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playRPS = (Button) findViewById(R.id.playRPS);
        quit = (Button) findViewById(R.id.quit);
        playMJB = (Button) findViewById(R.id.playMJB);
        title = (TextView) findViewById(R.id.titleView);
    }

    public void playRPS(View v) {
        Intent intent = new Intent(getApplicationContext(), PlayRPS.class);
        finish();
        startActivity(intent);
    }

    public void playMJB(View v) {
        Intent intent = new Intent(getApplicationContext(), PlayMJB.class);
        finish();
        startActivity(intent);
    }

    public void setLife(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        final EditText edit = new EditText(this);
        edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        edit.setHint("Set your life");
        alert.setTitle("Life Setting");
        alert.setView(edit);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strlife = edit.getText().toString();
                if(strlife.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "Please Enter", Toast.LENGTH_SHORT).show();
                } else {
                    Life.life = Integer.parseInt(strlife);
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    public void Gamble(View v) {
        Intent intent = new Intent(getApplicationContext(), gamble.class);
        finish();
        startActivity(intent);
    }

    public void showRanking(View v) {
        setContentView(R.layout.show_ranking);
        SharedPreferences mPrefs = getSharedPreferences("Rank", MODE_PRIVATE);
        TextView t = (TextView) findViewById(R.id.text);
        t.setText("Ranking!!");
        for(int i=0; i<ranklist.size(); i++) {
            String rank_name = mPrefs.getString(i + "rank_name", null);
            int rank_score = mPrefs.getInt(i + "rank_score", 0);
            t.append("\n"+(i+1)+". "+rank_name);
            t.append(" - "+rank_score+" points");
        }
    }

    public void quit(View v) {
        finish();
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {      // 화면 전환 시 데이터를 유지
//        // Save the play buttons state so it stays invisible on restart
//        outState.putInt(STATE_PLAY_BUTTON, playRPS.getVisibility());
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().findFragmentById(R.id.fragment_holder) != null) {
//            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_holder)).commit();
//            final Animation fadeIn = new AlphaAnimation(0.f, 1.f);
//            fadeIn.setDuration(1500);
//            fadeIn.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                }
//                public void onAnimationRepeat(Animation animation) {
//                }
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    playRPS.setVisibility(View.VISIBLE);
//                }
//            });
//            playRPS.startAnimation(fadeIn);
//            super.onBackPressed();
//        }
//    }
}
