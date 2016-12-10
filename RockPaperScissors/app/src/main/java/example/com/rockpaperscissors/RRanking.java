package example.com.rockpaperscissors;

import android.app.Activity;

public class RRanking extends Activity{
    String name;
    int score;
    int draw;
    public RRanking(String n, int s, int d) {
        this.name = n;
        this.score = s;
        this.draw = d;
    }

    String getName() {
        return this.name;
    }
    int getScore() {
        return this.score;
    }
    int getDraw() {
        return this.draw;
    }

    void setRanking(String n, int s, int d) {
        this.name = n;
        this.score = s;
        this.draw = d;
    }
}
