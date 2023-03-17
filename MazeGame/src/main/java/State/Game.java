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
		this.enemyList = new ArrayList<>();
		this.rewardList = new ArrayList<>();
		this.trapList = new ArrayList<>();
	}

	/**
	 * Starts the level and instantiates everything in the level
	 * @param enemyCount
	 * @param rewardCount
	 * @param trapCount
	 */
	public void start(int enemyCount, int rewardCount, int trapCount) {
		myMaze = Maze.generateRandomizedMaze();
		entityGenerator(enemyCount,rewardCount,trapCount);
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

	/**
	 * Returns reward at location
	 * @param location
	 * @return
	 */
	private Reward getReward(Point location){
		for(Reward reward : rewardList){
			if(location.equals(reward.getLocation())){
				return reward;
			}
		}
		return new Reward(EntityType.empty, location, 0);
	}

	/**
	 * Returns trap at location
	 * @param location
	 * @return
	 */
	private Trap getTrap(Point location){
		for(Trap trap : trapList){
			if(location.equals(trap.getLocation())){
				return trap;
			}
		}
		return new Trap(EntityType.empty, location, 0);
	}


	private void killPlayer(){
		player.setAlive(false);
		// todo death animation?
	}

	/**
	 * Places all entities on the map
	 */
	private void placeEntitiesOnMap() {
		myMaze.setEntity(player, player.getLocation());
		for (Enemy enemy : enemyList) {
			myMaze.setEntity(enemy, enemy.getLocation());
		}
		for (Reward reward : rewardList) {
			myMaze.setEntity(reward, reward.getLocation());
		}
		for (Trap trap : trapList) {
			myMaze.setEntity(trap, trap.getLocation());
		}
	}

	/**
	 * Method is called when player moves,
	 * Checks the collisiontype at location
	 * @param location
	 * @return
	 */
	private CollisionType entityCollision(Point location) { // subject to change based on other entities.
		Entity entity1 = myMaze.getEntity(location);
		EntityType entityType = entity1.getEntityType();

		switch(entityType){
			case empty:
				return CollisionType.noCollision;
			case player:
				return CollisionType.player;
			case enemy:
				return CollisionType.playerEnemy;
			case reward:
				return CollisionType.playerReward;
			case trap:
				return CollisionType.playerTrap;
			default:
				throw new RuntimeException("Game.java entityCollision(): collision with non-allowed entity1");
		}

	}

	/**
	 * Converts char to MoveDirection enum
	 * @param input
	 * @return
	 */
	private MoveDirection convertCharToMoveDirection(char input){
		input = Character.toLowerCase(input);
		switch(input){
			case 'w':
				return MoveDirection.UP;
			case 's':
				return MoveDirection.DOWN;
			case 'd':
				return MoveDirection.RIGHT;
			case 'a':
				return MoveDirection.LEFT;
			default:
				return MoveDirection.NONE;
		}
	}

	/**
	 * Moves the player entity
	 * @param input
	 */
	public void movePlayer(char input) {
		input = Character.toLowerCase(input);
		MoveDirection move = convertCharToMoveDirection(input);
		if (!isEntityMoveValid(player, move)) {
			System.out.println("Game.java movePlayer(): Player move not valid, bounce back");
			return;
		}

		Point newLocation = player.getLocation().newMoveLocation(move);


		moveEntity(player, move);
	}

	/**
	 * Checks the entity collision at a  specific point
	 * @param entity
	 * @param location
	 */
	private void checkEntityCollision(Entity entity, Point location){
		CollisionType collision = entityCollision(location);

//		System.out.println("Collision Type: " + collision);
		switch (collision) {
			case noCollision:
				break;
			case player:
				if(entity instanceof Enemy){
					killPlayer();
				}
				break;
			case playerEnemy:
				if(entity instanceof Player){
					killPlayer();
				}
				return;
			case playerReward:
				if(entity instanceof Player) {
					Reward reward = getReward(location);
					myMaze.setEntity(new Empty(EntityType.empty, location), location);
					player.incrementScore(reward.getScore());
				}
				break;
			case playerTrap:
				if(entity instanceof Player){
					Trap trap = getTrap(location);
					myMaze.setEntity(new Empty(EntityType.empty, location), location);
					player.decrementScore(trap.getDamage());
				}
				break;
			default:
				System.out.println("Collision: " + collision);
//				throw new RuntimeException("Game.java checkEntityCollision(): collision type is invalid");

		}
	}

	/**
	 * Checks if the entity move is valid
	 * @param entity
	 * @param move
	 * @return
	 */
	private boolean isEntityMoveValid(Entity entity, MoveDirection move) {
		Point entityLocation = entity.getLocation();
		if(move == MoveDirection.NONE){
			return true;
		}
		return myMaze.isCellOpen(entityLocation.newMoveLocation(move));
	}

	/**
	 * Creates the entities at the start of the level
	 * @param enemyCount
	 * @param rewardCount
	 * @param trapCount
	 */
	private void entityGenerator(int enemyCount, int rewardCount, int trapCount){ //calls entityMaker and checks if the entity is placeable
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
					// todo hardcoded for now, maybe have a level multipler later
					entityMaker(EntityType.reward, newPoint, 100);
					usedPoints.add(newPoint);
					status = false;
					break;
				}
				for(int j = 0; j < usedPoints.size(); j++){
					if (!usedPoints.get(j).equals(newPoint)){
						entityMaker(EntityType.reward, newPoint, 100); //set to zero but planned to hardcode it
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
					entityMaker(EntityType.trap, newPoint, 10);
					usedPoints.add(newPoint);
					status = false;
					break;
				}
				for(int j = 0; j < usedPoints.size(); j++){
					if (!usedPoints.get(j).equals(newPoint)){
						entityMaker(EntityType.trap, newPoint, 10);//set to zero but planned to hardcode it
						usedPoints.add(newPoint);
						status = false;
						break;
					}
				}
			}
		}
	}

	/**
	 * Creates the appropriate entity dependent on entityType
	 * @param entityType
	 * @param location
	 * @param score
	 */
	private void entityMaker(EntityType entityType, Point location, int score) {
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

	/**
	 * Runs the enemy movement algorithm.
	 * Enemies will randomly move if they are not close to player
	 */
	public void runEnemyMovement() {
		Point playerLocation = player.getLocation();
		for (Enemy enemy : enemyList) {
			Point enemyLocation = enemy.getLocation();
			MoveDirection move;
			if (isPlayerinRange(playerLocation, enemyLocation)) { //follow player
				move = getEnemyFollowMovement(enemy, playerLocation);
			} else { //random movement
				move = getEnemyRandomMovement(enemy);
			}
			Point newEnemyLocation = enemyLocation.newMoveLocation(move);
			moveEntity(enemy, move);
		}
	}

	/**
	 *  Returns a valid enemy follow movement move
	 * @param entityThatFollows
	 * @param followLocation
	 * @return
	 */
	private MoveDirection getEnemyFollowMovement(Entity entityThatFollows, Point followLocation){
		MoveDirection move = MoveDirection.NONE;
		Point entityLocation = entityThatFollows.getLocation();
//		do{
//			//TODO INFINITE LOOP HERE
//			System.out.println("MoveDirection: " + move);
//			if (followLocation.getX() - entityLocation.getX() < 0) {
//				move = MoveDirection.UP;
//			} else if (followLocation.getX() - entityLocation.getX() > 0) {
//				move = MoveDirection.DOWN;
//			} else if (followLocation.getY() - entityLocation.getY() < 0) {
//				move = MoveDirection.LEFT;
//			} else if (followLocation.getY() - entityLocation.getY() > 0) {
//				move = MoveDirection.RIGHT;
//			}else{
//				move = MoveDirection.NONE;
//			}
//
//		}while(!isEntityMoveValid(entityThatFollows, move) );

		return move;
	}

	/**
	 * Returns a valid enemy random movement move
	 * @param entity
	 * @return
	 */
	private MoveDirection getEnemyRandomMovement(Entity entity){
		MoveDirection move = MoveDirection.NONE;
		do{
			Random random = new Random();
			int randomInt = random.nextInt(4);
			switch (randomInt) {
				case 0 -> {
					move = MoveDirection.UP;
				}
				case 1 -> {
					move = MoveDirection.RIGHT;
				}
				case 2 -> {
					move = MoveDirection.DOWN;
				}
				case 3 -> {
					move = MoveDirection.LEFT;
				}
			}
		}while(!isEntityMoveValid(entity, move));

		return move;
	}

	private boolean isPlayerinRange(Point playerlocation, Point enemyLocation) {
		int playerX = playerlocation.getX();
		int playerY = playerlocation.getY();
		int enemyX = enemyLocation.getX();
		int enemyY = enemyLocation.getY();
		return (Math.abs(playerX - enemyX) == 5 || Math.abs(playerY - enemyY) == 5);//checks if player is in 5 cell of every direction

	}

	/**
	 * Entity given is moved depending on direction
	 * Precondition: Move should be valid
	 * @param entity
	 * @param move
	 */
	private void moveEntity(Entity entity, MoveDirection move) { // takes entity position and changes by adding X and/or Y
		if(!isEntityMoveValid(entity, move)){
			throw new IllegalArgumentException("Game.java moveEntity(): invalid move called");
		}

		Point oldLocation = entity.getLocation();
		Point newLocation = entity.getLocation().newMoveLocation(move);
		checkEntityCollision(entity, newLocation);

		myMaze.swapEntity(oldLocation, newLocation);

	}

	public Maze getMyMaze() {
		return myMaze;
	}
}
