package Entities;

import Map.Point;

public class Reward extends Entity{

    private boolean active;
    private int score;

    public Reward(EntityType entityType, Point location, int score){
        super(entityType, location);
        active = true;
        this.score = score;
    }

    public void obtainCheese(Player player){
        active = false;
        player.incrementScore(score);
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
