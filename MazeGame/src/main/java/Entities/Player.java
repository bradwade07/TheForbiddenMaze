package Entities;

import Map.Point;

/**
 * Player class that represents the Player entity
 * Class includes HP of the player and other dynamic entity
 */
public class Player extends Entity {
    private int score;
//    TODO:
//    private Spell currSpell;


    public Player(EntityType entityType, Point location){
        super(entityType,location);
        score = 100;
    }

    // decrement hp by amt and return new HP
    public int decrementHP(int amt){
        score -= amt;
        // cant let health go below 0
        if (score < 0){
            score = 0;
        }
        return score;
    }

    // increment hp by amt and return new HP
    public int incrementHP(int amt){
        score += amt;
        // cant overheal
        if (score > 100){
            score = 100;
        }
        return score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public EntityType getEntityType() {
        return super.getEntityType();
    }

    public void setEntityType(EntityType entityType) {
        super.setEntityType(entityType);
    }

    public Point getLocation() {
        return super.getLocation();
    }

    public void setLocation(Point location) {
        super.setLocation(location);
    }
}
