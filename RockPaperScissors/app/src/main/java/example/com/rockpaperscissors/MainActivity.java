package example.com.rockpaperscissors;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    static final String STATE_PLAY_BUTTON = "play_button";
    TextView title;
    Button playButton;
    Button quit;
    Button playKorean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {            // MainActivity 가 실행되도록 함
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);             // xml 문서를 프로그램 실행될때 메모리에 올리는 과정
        playButton = (Button) findViewById(R.id.playButton);
        quit = (Button) findViewById(R.id.quit);
        playKorean = (Button) findViewById(R.id.playKorean);
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

        if (playButton != null) {
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation fadeout = new AlphaAnimation(1.f, 0.f);
                    fadeout.setDuration(1500);
                    fadeout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        public void onAnimationRepeat(Animation a) {
                        }

                        public void onAnimationEnd(Animation a) {
                            playButton.setVisibility(View.GONE);
                            quit.setVisibility(View.GONE);
                            playKorean.setVisibility(View.GONE);
                            title.setVisibility(View.INVISIBLE);
                            getFragmentManager().beginTransaction()
                                    .add(R.id.fragment_holder, new GameFragment())
                                    .commit();
                        }
                    });
                    playButton.startAnimation(fadeout);
                }
            });
        }
    }

    public void playMJB(View v){
        Intent intent = new Intent(getApplicationContext(), PlayMJB.class);
        startActivity(intent);
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
