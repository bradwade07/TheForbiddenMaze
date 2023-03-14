package Entities;

import Map.Point;

/**
 * Dynamic entity interface that supports entity movement
 */
public interface DynamicEntity{
    public EntityType getEntityType();
    public void setEntityType(EntityType entityType);

    public Point getLocation();

    public void setLocation(Point location);
}

