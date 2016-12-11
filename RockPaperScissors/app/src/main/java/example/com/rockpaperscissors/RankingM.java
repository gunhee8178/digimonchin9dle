package example.com.rockpaperscissors;

import android.app.Activity;

public class RankingM extends Activity{
    String name;
    int score;
    public RankingM(String n, int s) {
        this.name = n;
        this.score = s;
    }

    String getName() {
        return this.name;
    }
    int getScore() {
        return this.score;
    }

    void setRanking(RankingM m) {
        this.name = m.getName();
        this.score = m.getScore();
    }
}
