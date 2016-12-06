package example.com.rockpaperscissors;

import java.util.Comparator;

/**
 * Created by baesh on 2016-12-05.
 */

public class MiniComparator implements Comparator<Ranking> {
    @Override
    public int compare(Ranking one, Ranking two) {
        int s1 = Integer.parseInt(one.score);
        int s2 = Integer.parseInt(two.score);
        if(s1 > s2) {
            return -1;
        } else if(s1 < s2) {
            return 1;
        } else {
            return 0;
        }
    }
}
