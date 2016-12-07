package example.com.rockpaperscissors;

import java.util.Random;

public class GameAI {
    private static final String[] DRAW_ARRAY = {"ROCK", "PAPER", "SCISSORS"};

    protected static String getDraw () {
        return DRAW_ARRAY[new Random().nextInt(DRAW_ARRAY.length)];
    }
}
