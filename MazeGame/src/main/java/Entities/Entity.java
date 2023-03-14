package Entities;

import Map.Point;

public class Entity{

    protected EntityType entityType;
    protected Point location;



    public Entity(EntityType entityType, Point location){
        this.entityType = entityType;
        this.location = location;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

}
