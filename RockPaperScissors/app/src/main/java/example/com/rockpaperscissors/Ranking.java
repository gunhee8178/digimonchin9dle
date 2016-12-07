package example.com.rockpaperscissors;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by baesh on 2016-12-05.
 */

public class Ranking extends Activity{
    String name;
    int score;
    public Ranking(String n, int s) {
        this.name = n;
        this.score = s;
    }

    int getScore() {
        return this.score;
    }
    String getName() {
        return this.name;
    }
}
