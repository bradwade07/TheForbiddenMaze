package Entities;

import Map.Point;

/**
 * A enemy class that creates the enemy entity
 * Enemies randomly move towards the player
 */
public class Empty extends Entity{

    /**
     * Constructor for empty entity type
     * @param entityType
     * @param location
     */
    public Empty(EntityType entityType, Point location){
        super(entityType, location);
    }

    /**
     * Get the entity type of empty entity, calls supers function
     * @return EntityType
     */
    public EntityType getEntityType() {
        return super.getEntityType();
    }

    /**
     * Set the EntityType of an empty entity, calls supers function
     * @param entityType
     */
    public void setEntityType(EntityType entityType) {
        super.setEntityType(entityType);
    }

    /**
     * Return the location of an empty entity, calls supers function
     * @return location
     */
    public Point getLocation() {
        return super.getLocation();
    }

    /**
     * get the location of an empty entity, calls supers function
     * @param location
     */
    public void setLocation(Point location) {
        super.setLocation(location);
    }
}

