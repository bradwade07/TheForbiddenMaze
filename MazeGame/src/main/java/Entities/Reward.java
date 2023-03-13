package Entities;

import Map.Point;

/**
 * Reward class that holds a set score
 */
public class Reward extends Entity {
    // artrirary value
    private final int SCORE = 20;
    Reward(Point location) {
        super(location);
    }

    public int getScore() {
        return SCORE;
    }
}
