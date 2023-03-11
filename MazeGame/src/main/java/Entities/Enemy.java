package Entities;

import Map.Point;
import State.Game;

/**
 * A enemy class that creates the enemy entity
 * Enemies randomly move towards the player
 */
public class Enemy extends Entity implements DynamicEntity{

    private final Game gameManager;

    Enemy(Game game, Point location){
        super(location);
        this.gameManager = game;
    }

    @Override
    public void move(Point location) {

    }
}
