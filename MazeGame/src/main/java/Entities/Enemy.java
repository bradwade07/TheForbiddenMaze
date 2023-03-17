package Entities;

import Map.Point;
import State.Game;

/**
 * A enemy class that creates the enemy entity
 * Enemies randomly move towards the player
 */
public class Enemy extends Entity{

    /**
     * Constructor for Enemy, calls superclass
     * @param entityType
     * @param location
     */
    public Enemy(EntityType entityType, Point location){
        super(entityType, location);
    }

    /**
     * Get the EntityType of an enemy, calls supers function
     * @return entityType
     */
    public EntityType getEntityType() {
        return super.getEntityType();
    }

    /**
     * Set the EntityType of an enemy, calls supers function
     * @param entityType
     */
    public void setEntityType(EntityType entityType) {
        super.setEntityType(entityType);
    }

    /**
     * Return the location of an enemy, calls supers function
     * @return location
     */
    public Point getLocation() {
        return super.getLocation();
    }

    /**
     * get the location of an enemy, calls supers function
     * @param location
     */
    public void setLocation(Point location) {
        super.setLocation(location);
    }
}


