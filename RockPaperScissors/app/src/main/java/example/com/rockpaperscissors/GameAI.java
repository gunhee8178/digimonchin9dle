package example.com.rockpaperscissors;

import java.util.Random;

/**
 * This is for return one of "Rock", "Paper" and "Scissors".
 * @author bring from github
 */
public class GameAI {
    private static final String[] DRAW_ARRAY = {"ROCK", "PAPER", "SCISSORS"};

    /**
     * @return one of "Rock", "Paper" and "Scissors" randomly
     */
    protected static String getDraw () {
        return DRAW_ARRAY[new Random().nextInt(DRAW_ARRAY.length)];
    }
}
