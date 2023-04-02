package Entities;

import Map.Point;

/**
 * Player class that represents the Player entity
 * Class includes HP of the player and other dynamic entity
 */
public class Player extends Entity {
    private int score;
    private boolean alive;

    /**
     * Return status of player
     * @return alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Set status of player
     * @param alive - parameter to set to
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Constructor for player, initializes score to 100 and set the player status to alive
     * @param entityType - Entitytype of the player
     * @param location - location of the player
     */
    public Player(EntityType entityType, Point location){
        super(entityType,location);
        score = 100;
        alive = true;
    }

    /**
     * Sets the score of the player
     * @param score - score to set to
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Decrement players score by a certain amount
     * @param amt - amount to decrement
     * @return score
     */
    public int decrementScore(int amt){
        score -= amt;
        return score;
    }

    /**
     * Increment players score by a certain amount
     * @param amt - amount to increment
     * @return score
     */
    public int incrementScore(int amt){
        score += amt;
        return score;
    }

    /**
     * Returns players current score
     * @return score
     */
    public int getScore() {
        return score;
    }


    /**
     * Get the EntityType of the player, calls supers function
     * @return entityType
     */
    public EntityType getEntityType() {
        return super.getEntityType();
    }

    /**
     * Set the EntityType of the player, calls supers function
     * @param entityType - entitytype to set to
     */
    public void setEntityType(EntityType entityType) {
        super.setEntityType(entityType);
    }

    /**
     * Return the location of the player, calls supers function
     * @return location
     */
    public Point getLocation() {
        return super.getLocation();
    }

    /**
     * get the location of the player, calls supers function
     * @param location - location to set to
     */
    public void setLocation(Point location) {
        super.setLocation(location);
    }

}
