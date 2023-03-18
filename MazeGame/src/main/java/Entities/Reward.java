package Entities;

import Map.Point;

/**
 * Reward Class for static reward entities
 */
public class Reward extends Entity{

    private boolean active;
    private int score;

    /**
     * Constructor for static reward, calls supers constructor, sets current status to active
     * and score to score param
     * @param entityType
     * @param location
     * @param score
     */
    public Reward(EntityType entityType, Point location, int score){
        super(entityType, location);
        active = true;
        this.score = score;
    }

    /**
     * Disable the reward and add its score to player
     * @param player
     */
    public void obtainReward(Player player){
        active = false;
        player.incrementScore(score);
    }

    /**
     * get status of reward
     * @return active
     */
    public boolean rewardStatus(){
        return active;
    }

    /**
     * Get the EntityType of a reward, calls supers function
     * @return entityType
     */
    public EntityType getEntityType() {
        return super.getEntityType();
    }

    /**
     * Set the EntityType of a reward, calls supers function
     * @param entityType
     */
    public void setEntityType(EntityType entityType) {
        super.setEntityType(entityType);
    }

    /**
     * Return the location of a reward, calls supers function
     * @return location
     */
    public Point getLocation() {
        return super.getLocation();
    }

    /**
     * get the location of a reward, calls supers function
     * @param location
     */
    public void setLocation(Point location) {
        super.setLocation(location);
    }

    public int getScore() {
        return score;
    }
}
