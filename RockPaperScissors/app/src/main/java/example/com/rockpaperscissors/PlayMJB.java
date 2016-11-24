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

public class PlayMJB extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {            // MainActivity 가 실행되도록 함
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mjb_play_screen);
    }
}