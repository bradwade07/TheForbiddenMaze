package Entities;

import Map.Point;

/**
 * Trap class for static trap entities
 */
public class Trap extends Entity{

    private boolean active;
    private int damage;

    /**
     * Constructor for trap, calls superclass, sets status to active, and damage to damage param
     * @param entityType
     * @param location
     * @param damage
     */
    public Trap(EntityType entityType, Point location, int damage){
        super(entityType, location);
        active = true;
        this.damage = damage;
    }

    /**
     * Deal damage to the player and disable trap
     * @param player
     */
    public void dealDamage(Player player){
        active = false;
        player.decrementScore(damage);
    }

    /**
     * get trap status
     * @return active
     */
    public boolean trapStatus(){
        return active;
    }

    /**
     * Get the EntityType of a trap, calls supers function
     * @return entityType
     */
    public EntityType getEntityType() {
        return super.getEntityType();
    }

    /**
     * Set the EntityType of a trap, calls supers function
     * @param entityType
     */
    public void setEntityType(EntityType entityType) {
        super.setEntityType(entityType);
    }

    /**
     * Return the location of a trap, calls supers function
     * @return location
     */
    public Point getLocation() {
        return super.getLocation();
    }

    /**
     * get the location of a trap, calls supers function
     * @param location
     */
    public void setLocation(Point location) {
        super.setLocation(location);
    }
    public int getDamage(){
        return this.damage;
    }

}
