package example.com.rockpaperscissors;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by baesh on 2016-12-05.
 */

public class Ranking extends Activity{

    String score;
    public Ranking(String s) {
        this.score = s;
    }

    String getScore() {
        return this.score;
    }

    void setScore(String s) {
        this.score = s;
    }
}
