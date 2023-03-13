package Entities;

import Map.Point;


/**
 * Punishment class that holds a set score
 */
public class Punishment extends Entity{
    // artrirary value
    private final int SCORE = -20;
    Punishment(Point location) {
        super(location);
    }

    public int getScore() {
        return SCORE;
    }
}
