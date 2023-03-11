package Entities;

import Map.Point;

public class Entity {
    protected Point location;

    Entity(int x, int y){
        location.setX(x);
        location.setY(y);
    }

}
