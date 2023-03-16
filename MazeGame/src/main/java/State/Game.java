package State;


import Entities.*;
import Map.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game class that handles all the gameplay
 */
public class Game {

	private Player player;
	private List<Enemy> enemyList;
	private List<Reward> rewardList;
	private List<Trap> trapList;
	private Maze myMaze;

	public Game() {
		this.player =  new Player(EntityType.player, new Point(1, 1));
		this.enemyList =new ArrayList<>();
		this.rewardList = new ArrayList<>();
		this.trapList = new ArrayList<>();
	}

	public void start(int enemyCount, int rewardCount, int trapCount) {
		myMaze = Maze.generateRandomizedMaze();
		entityGenrator(enemyCount,rewardCount,trapCount);
		placeEntitiesOnMap();

	}


	public boolean isPlayerAlive(){
		return player.isAlive();
	}

	public boolean hasPlayerLost() {
		return (getPlayerScore() < 0 || !player.isAlive());
	}

	public boolean hasPlayerWon() {
		// TODO: and rewards are collected
		return (player.getLocation().equals(myMaze.getExitCell()));
	}

	public int getPlayerScore() {
		return player.getScore();
	}

	private void placeEntitiesOnMap() {
		myMaze.setEntity(player, player.getLocation());
		for (int i = 0; i < enemyList.size(); i++) {
			myMaze.setEntity(enemyList.get(i), enemyList.get(i).getLocation());
		}
	}

	public CollisionType entityCollision(Point location1) { // subject to change based on other entities.
		Entity entity1 = myMaze.getEntity(location1);
		if (entity1.getEntityType().equals(EntityType.empty)) {
			return CollisionType.noCollision;
		} else if (entity1.getEntityType().equals(EntityType.enemy)) {
			return CollisionType.playerEnemy;
		} else if (entity1.getEntityType().equals(EntityType.reward)) {
			return CollisionType.playerReward;
		} else if (entity1.getEntityType().equals(EntityType.trap)) {
			return CollisionType.playerTrap;
		} else {
			throw new RuntimeException("Game.java entityCollision(): collision with non-allowed entity1");
		}
	}

	public void movePlayer(char move) {
		move = Character.toLowerCase(move);
		if (!isPlayerMoveValid(move)) {
			System.out.println("Game.java movePlayer(): Player move not valid, bounce back");
			return;
		}

		Point newLocation = player.getLocation().newMoveLocation(move);
		CollisionType collision = entityCollision(newLocation);

		System.out.println("Collision Type: " + collision);
		switch (collision) {
			case noCollision:
				break;
			case playerEnemy:
				player.setAlive(false);
				// todo animation for death maybe?
				return;
			case playerReward:
				myMaze.setEntity(new Empty(EntityType.empty, newLocation), newLocation);
//   TODO             player.incrementScore();
				break;
			case playerTrap:
				myMaze.setEntity(new Empty(EntityType.empty, newLocation), newLocation);
//  TODO              player.decrementScore();
				break;
			default:
				throw new RuntimeException("Game.java movePlayer(): collision type is invalid");

		}


		int playerX = player.getLocation().getX();
		int playerY = player.getLocation().getY();
		int expectedX = newLocation.getX();
		int expectedY = newLocation.getY();

		myMaze.swapEntity(playerX, playerY, expectedX, expectedY);
		player.setLocation(newLocation);

	}

	public boolean isPlayerMoveValid(char move) {
		Point playerLocation = player.getLocation();
		return myMaze.isCellOpen(playerLocation.newMoveLocation(move));
	}

	public void entityGenrator(int enemyCount, int rewardCount, int trapCount){ //calls entityMaker and checks if the entity is placeable
		List<Point> usedPoints = new ArrayList<>();
		Random random;
		int x, y;
		Point newPoint;
		while(enemyList.size()< enemyCount){
			boolean status = true;
			while(status){
				random = new Random();
				x = random.nextInt(myMaze.getROWS());
				random = new Random();
				y = random.nextInt(myMaze.getCOLS());
				newPoint = new Point(x,y);
				if(usedPoints.size() == 0){
					entityMaker(EntityType.enemy, newPoint, 0);
					usedPoints.add(newPoint);
					status = false;
					break;
				}
				for(int j = 0; j < usedPoints.size(); j++){
					if (!usedPoints.get(j).equals(newPoint)){
						entityMaker(EntityType.enemy, newPoint, 0);
						usedPoints.add(newPoint);
						status = false;
						break;
					}
				}
			}
		}
		while(rewardList.size()< rewardCount){
			boolean status = true;
			while(status){
				random = new Random();
				x = random.nextInt(myMaze.getROWS());
				random = new Random();
				y = random.nextInt(myMaze.getCOLS());
				newPoint = new Point(x,y);
				if(usedPoints.size() == 0){
					entityMaker(EntityType.reward, newPoint, 0);
					usedPoints.add(newPoint);
					status = false;
					break;
				}
				for(int j = 0; j < usedPoints.size(); j++){
					if (!usedPoints.get(j).equals(newPoint)){
						entityMaker(EntityType.reward, newPoint, 0); //set to zero but planned to hardcode it
						usedPoints.add(newPoint);
						status = false;
						break;
					}
				}
			}
		}
		while(trapList.size()< trapCount){
			boolean status = true;
			while(status){
				random = new Random();
				x = random.nextInt(myMaze.getROWS());
				random = new Random();
				y = random.nextInt(myMaze.getCOLS());
				newPoint = new Point(x,y);
				if(usedPoints.size() == 0){
					entityMaker(EntityType.trap, newPoint, 0);
					usedPoints.add(newPoint);
					status = false;
					break;
				}
				for(int j = 0; j < usedPoints.size(); j++){
					if (!usedPoints.get(j).equals(newPoint)){
						entityMaker(EntityType.trap, newPoint, 0);//set to zero but planned to hardcode it
						usedPoints.add(newPoint);
						status = false;
						break;
					}
				}
			}
		}
	}

	public void entityMaker(EntityType entityType, Point location, int score) {
		if (entityType.equals(EntityType.enemy)) {
			Enemy newEnemy = new Enemy(EntityType.enemy, location);
			enemyList.add(newEnemy);
		}
		else if(entityType.equals((EntityType.reward))){
			Reward newReward = new Reward(EntityType.reward, location, score);
			rewardList.add(newReward);

		}
		else if(entityType.equals((EntityType.trap))){
			Trap newTrap = new Trap(EntityType.trap, location, score);
			trapList.add(newTrap);
		}
	}

	public void enemyMovement() {
		for (int i = 0; i < enemyList.size(); i++) {
			Enemy currentEnemy = enemyList.get(i);
			Point playerLocation = player.getLocation();
			Point enemyLocation = currentEnemy.getLocation();
			if (isPlayerinRange(playerLocation, enemyLocation)) { //follow player
				if (playerLocation.getX() - enemyLocation.getX() < 0) {
					moveEntity(enemyLocation, -1, 0);
				} else if (playerLocation.getX() - enemyLocation.getX() > 0) {
					moveEntity(enemyLocation, 1, 0);
				} else if (playerLocation.getY() - enemyLocation.getY() < 0) {
					moveEntity(enemyLocation, 0, -1);
				} else if (playerLocation.getY() - enemyLocation.getY() > 0) {
					moveEntity(enemyLocation, 0, 1);
				}
			} else { //random movement
				Random random = new Random();
				int move = random.nextInt(4);
				switch (move) {
					case 0 -> {
						moveEntity(enemyLocation, 0, 1);
					}
					case 1 -> {
						moveEntity(enemyLocation, 0, -1);
					}
					case 2 -> {
						moveEntity(enemyLocation, 1, 0);
					}
					case 3 -> {
						moveEntity(enemyLocation, -1, 0);
					}
				}
			}

		}
	}

	public boolean isPlayerinRange(Point playerlocation, Point enemyLocation) {
		int playerX = playerlocation.getX();
		int playerY = playerlocation.getY();
		int enemyX = enemyLocation.getX();
		int enemyY = enemyLocation.getY();
		return (Math.abs(playerX - enemyX) == 5 || Math.abs(playerY - enemyY) == 5);//checks if player is in 5 cell of every direction

	}

	public void moveEntity(Point entityLocation, int addX, int addY) { // takes entity position and changes by adding X and/or Y
		int oldX = entityLocation.getX();
		int oldY = entityLocation.getY();
		myMaze.swapEntity(oldX, oldY, oldX + addX, oldY + addY);
	}

	public Maze getMyMaze() {
		return myMaze;
	}
}
