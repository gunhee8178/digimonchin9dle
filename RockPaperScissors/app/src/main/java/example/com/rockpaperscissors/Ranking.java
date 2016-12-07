package example.com.rockpaperscissors;

import android.app.Activity;

public class Ranking extends Activity{
    String name;
    int score;
    public Ranking(String n, int s) {
        this.name = n;
        this.score = s;
    }

    String getName() {
        return this.name;
    }
    int getScore() {
        return this.score;
    }

    void setRanking(String n, int s) {
        this.name = n;
        this.score = s;
    }
}
