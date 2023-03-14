package Entities;

import Map.Point;

public class Trap extends Entity{

    private boolean active;
    private int damage;

    public Trap(EntityType entityType, Point location, int damage){
        super(entityType, location);
        active = true;
        this.damage = damage;
    }

    public void dealDamage(Player player){
        active = false;
        player.decrementHP(damage);
    }

    public boolean trapStatus(){
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
