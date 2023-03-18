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
     * @param alive
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Constructor for player, initializes score to 100 and set the player status to alive
     * @param entityType
     * @param location
     */
    public Player(EntityType entityType, Point location){
        super(entityType,location);
        score = 100;
        alive = true;
    }

    /**
     * Decrement players score by a certain amount
     * @param amt
     * @return score
     */
    public int decrementScore(int amt){
        score -= amt;
        // cant let health go below 0
        if (score < 0){
            score = 0;
        }
        return score;
    }

    /**
     * Increment players score by a certain amount
     * @param amt
     * @return score
     */
    public int incrementScore(int amt){
        score += amt;
        // cant overheal
        if (score > 100){
            score = 100;
        }
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
     * Set the players score
     * @param score
     */
    public void addScore(int score) {
        //TODO: clean
//        System.out.println("inside addScore");
        this.score += score;
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
     * @param entityType
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
     * @param location
     */
    public void setLocation(Point location) {
        super.setLocation(location);
    }

}
