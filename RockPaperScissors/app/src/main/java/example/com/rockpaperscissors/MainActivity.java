package example.com.rockpaperscissors;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static Integer life=1;

    static final String STATE_PLAY_BUTTON = "play_button";
    TextView title;
    Button playButton;
    Button quit;
    Button MJB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {            // MainActivity 가 실행되도록 함
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);             // xml 문서를 프로그램 실행될때 메모리에 올리는 과정
        playButton = (Button) findViewById(R.id.playButton);
        quit = (Button) findViewById(R.id.quit);
        MJB = (Button) findViewById(R.id.MJB);
        title = (TextView) findViewById(R.id.titleView);

        if (savedInstanceState != null) {
            if (savedInstanceState.getInt(STATE_PLAY_BUTTON) == View.GONE)
                playButton.setVisibility(View.GONE);
        }

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        if (playButton != null) {
//            playButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Animation fadeout = new AlphaAnimation(1.f, 0.f);
//                    fadeout.setDuration(1500);
//                    fadeout.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//                        }
//
//                        public void onAnimationRepeat(Animation a) {
//                        }
//
//                        public void onAnimationEnd(Animation a) {
//                            playButton.setVisibility(View.GONE);
//                            quit.setVisibility(View.GONE);
//                            MJB.setVisibility(View.GONE);
//                            title.setVisibility(View.INVISIBLE);
//                            getFragmentManager().beginTransaction()
//                                    .add(R.id.fragment_holder, new GameFragment())
//                                    .commit();
//                        }
//                    });
//                    playButton.startAnimation(fadeout);
//                }
//            });
//        }
    }
    public void playRPS(View v){
        Intent intent = new Intent(getApplicationContext(), GameFragment.class);
        startActivity(intent);
    }


    public void playMJB(View v){
        Intent intent = new Intent(getApplicationContext(), PlayMJB.class);
        startActivity(intent);
    }

    public void lifeSetting(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Set your life");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                life = Integer.parseInt(text);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {      // 화면 전환 시 데이터를 유지
        // Save the play buttons state so it stays invisible on restart
        outState.putInt(STATE_PLAY_BUTTON, playButton.getVisibility());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().findFragmentById(R.id.fragment_holder) != null) {
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.fragment_holder)).commit();
            final Animation fadeIn = new AlphaAnimation(0.f, 1.f);
            fadeIn.setDuration(1500);
            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                public void onAnimationRepeat(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    playButton.setVisibility(View.VISIBLE);
                }
            });
            playButton.startAnimation(fadeIn);
            super.onBackPressed();
        }
    }
}
