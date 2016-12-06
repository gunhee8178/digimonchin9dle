package example.com.rockpaperscissors;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by baesh on 2016-12-05.
 */

public class Ranking extends Activity{

    String name;
    String score;
    public Ranking(String n, String s) {
        this.name = n;
        this.score = s;
    }

    String getScore() {
        return this.score;
    }
    String getName() {
        return this.name;
    }

    void setScore(String s) {
        this.score = s;
    }
    void setName(String n) {
        this.name = n;
    }
}
