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

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getScore() {
		return score;
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
