package Entities;

import Map.Point;

/**
 * Player class that represents the Player entity
 * Class includes score of the player and other dynamic entity methods
 */
public class Player extends Entity {
    private int score;
    private boolean alive;
//    TODO:
//    private Spell currSpell;



    public Player(EntityType entityType, Point location){
        super(entityType,location);
        score = 100;
        alive = true;
    }

    // decrement score by amt and return new score
    public int decrementScore(int amt){
        score -= amt;
        // cant let health go below 0
        if (score < 0){
            score = 0;
        }
        return score;
    }

    // increment score by amt and return new score
    public int incrementScore(int amt){
        score += amt;
        return score;
    }
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
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
