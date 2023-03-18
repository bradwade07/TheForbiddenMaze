package Entities;

import Map.Point;

/**
 * Super class for entity
 */
public class Entity{

    protected EntityType entityType;
    protected Point location;

    /**
     * Constructor for Entity
     * @param entityType
     * @param location
     */
    public Entity(EntityType entityType, Point location){
        this.entityType = entityType;
        this.location = location;
    }

    /**
     * Get the EntityType of an entity
     * @return entityType
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * Set the EntityType of an entity
     * @param entityType
     */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * Return the location of an entity
     * @return location
     */
    public Point getLocation() {
        return location;
    }

    /**
     * get the location of an entity
     * @param location
     */
    public void setLocation(Point location) {
        this.location = location;
    }

}
