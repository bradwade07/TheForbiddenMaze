package Entities;

public class Player extends Entity implements DynamicEntity{
    private int hp;
//    TODO:
//    private Spell currSpell;

    Player(int x, int y){
        super(x, y);
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
    public void move(int x, int y){
        location.setX(x);
        location.setY(y);
    }

}
