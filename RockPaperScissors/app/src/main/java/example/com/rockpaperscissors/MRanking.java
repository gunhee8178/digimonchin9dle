package example.com.rockpaperscissors;

import android.app.Activity;

public class MRanking extends Activity{
    String name;
    int score;
    public MRanking(String n, int s) {
        this.name = n;
        this.score = s;
    }

    String getName() {
        return this.name;
    }
    int getScore() {
        return this.score;
    }

    void setRanking(MRanking m) {
        this.name = m.getName();
        this.score = m.getScore();
    }
}
