package example.com.rockpaperscissors;

import android.app.Activity;
/**
 * This class is to store user name, score and draws for rps and get them at other class.
 * @author HanGunHee
 */

public class RankingR extends Activity{
    String name;
    int score;
    int draw;
    public RankingR(String n, int s, int d) {
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

    void setRanking(RankingR r) {
        this.name = r.getName();
        this.score = r.getScore();
        this.draw = r.getDraw();
    }
}
