package Entities;

import Map.Point;
import State.Game;

/**
 * A enemy class that creates the enemy entity
 * Enemies randomly move towards the player
 */
public class Enemy extends Entity implements DynamicEntity{


    Enemy(Point location){
        super(location);
    }

    @Override
    public void move(Point location) {

    }
}
