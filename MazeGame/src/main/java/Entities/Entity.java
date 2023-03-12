package Entities;

import Map.Point;

public class Entity {
    protected Point location;

    Entity(Point location){
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
