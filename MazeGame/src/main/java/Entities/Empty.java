package Entities;

import Map.Point;

/**
 * A enemy class that creates the enemy entity
 * Enemies randomly move towards the player
 */
public class Empty extends Entity{

    //private final Game gameManager;

    public Empty(EntityType entityType, Point location){
        super(entityType, location);
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

