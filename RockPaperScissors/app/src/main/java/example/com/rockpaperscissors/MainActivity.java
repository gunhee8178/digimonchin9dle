package example.com.rockpaperscissors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    static final String STATE_PLAY_BUTTON = "play_button";
    TextView title;
    Button playRPS, playMJB, quit;

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
        startActivity(intent);
    }

    public void playMJB(View v) {
        Intent intent = new Intent(getApplicationContext(), PlayMJB.class);
        startActivity(intent);
    }

    public void setLife(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        final EditText edit = new EditText(this);
        alert.setTitle("Life Setting");
        alert.setMessage("Set you life.");
        alert.setView(edit);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                String strlife = edit.getText().toString();
                Life.life = Integer.parseInt(strlife);
                startActivity(intent);
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
        startActivity(intent);
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
