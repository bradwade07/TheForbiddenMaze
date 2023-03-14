package Entities;

import Map.Point;

public class Cheese extends Entity{

    private boolean active;
    private int score;

    public Cheese(EntityType entityType, Point location, int score){
        super(entityType, location);
        active = true;
        this.score = score;
    }

    public void obtainCheese(Player player){
        active = false;
        player.incrementHP(score);
    }

    public boolean cheeseStatus(){
        return active;
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
