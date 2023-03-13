package Entities;

import Map.Point;

/**
 * Player class that represents the Player entity
 * Class includes HP of the player and other dynamic entity
 */
public class Player extends Entity implements DynamicEntity{
    private int score;
//    TODO:
//    private Spell currSpell;

    public int getScore() {
        return score;
    }

    public Player(Point location){
        super(location);
        score = 100;
    }

    // decrement hp by amt and return new HP
    public int decrementScore(int amt){
        score -= amt;
        // cant let health go below 0
        if (score < 0){
            score = 0;
        }
        return score;
    }

    // increment hp by amt and return new HP
    public int incrementScore(int amt){
        score += amt;
        // cant overheal
        if (score > 100){
            score = 100;
        }
        return score;
    }

    @Override
    public void move(Point location) {
        this.location = location;
    }
}