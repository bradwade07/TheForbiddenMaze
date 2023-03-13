package Entities;

import Map.Point;

/**
 * Player class that represents the Player entity
 * Class includes HP of the player and other dynamic entity
 */
public class Player extends Entity implements DynamicEntity{
    private int hp;
//    TODO:
//    private Spell currSpell;


    public Player(Point location){
        super(location);
        hp = 100;
    }

    // decrement hp by amt and return new HP
    public int decrementHP(int amt){
        hp -= amt;
        // cant let health go below 0
        if (hp < 0){
            hp = 0;
        }
        return hp;
    }

    // increment hp by amt and return new HP
    public int incrementHP(int amt){
        hp += amt;
        // cant overheal
        if (hp > 100){
            hp = 100;
        }
        return hp;
    }

    @Override
    public void move(Point location) {
        this.location = location;
    }
}