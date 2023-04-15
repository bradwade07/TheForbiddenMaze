package State;

import Entities.*;
import Map.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Game class that handles all the gameplay
 */
public class Game {

	private final Player player;
	private final List<Enemy> enemyList;
	public final List<Reward> rewardList;
	private final List<Trap> trapList;
	private Maze myMaze;
	boolean isExitCellOpen;
	GameState gameState;
	List<Point> usedPoints = new ArrayList<>();
	private int initialEnemyListSize;
	private int initialRewardListSize;
	private int initialTrapListSize;

	/**
	 * Enum representing the different states of the game.
	 */
	public enum GameState {
		WIN, LOST, RUNNING, IDLE, gameOver,gameStart,howToPlay
	}

	/**
	 * Enum representing the different types of moves that can be made by the player.
	 */
	public enum MoveCheck {
		validMove, killPlayer, enemyToTrap, enemyToReward
	}

	/**
	 * Constructor for Game class.
	 * Initializes the player, enemy list, reward list, trap list, and sets the initial game state to "gameStart".
	 */
	public Game() {
		this.player = new Player(EntityType.player, new Point(1,1));
		this.enemyList = new ArrayList<>();
		this.rewardList = new ArrayList<>();
		this.trapList = new ArrayList<>();
		this.gameState = GameState.gameStart;
		isExitCellOpen = false;
	}


	/**
	 * Starts the level and instantiates everything in the level
	 *
	 * @param enemyCount - enemy count to spawn enemies
	 * @param rewardCount - reward count to spawn rewards
	 * @param trapCount - trap count to spawn traps
	 */
	public void generateMap(int enemyCount, int rewardCount, int trapCount) {
		myMaze = Maze.generateRandomizedMaze();
		entityGenerator(enemyCount, rewardCount, trapCount);
		placeEntitiesOnMap();
		initialEnemyListSize = enemyCount;
		initialRewardListSize = rewardCount;
		initialTrapListSize = trapCount;
	}
	/**
	 *  Resets the game to its initial values and generates a new map
	 * @param enemyCount - amount of enemies to generate
	 * @param rewardCount - amount of rewards to generate
	 * @param trapCount - amount of traps to generate
	 */
	public void reset(int enemyCount, int rewardCount, int trapCount, boolean isFullReset){
		setGameStateToStart();
		resetEntityLists();
		resetPlayer(isFullReset);
		isExitCellOpen = false;
		generateMap(enemyCount, rewardCount, trapCount);

	}

	/**
	 * Retrieves the current game state.
	 * @return the current GameState of the Game object
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Runs one tick of the game, contains enemy movement and checks the current gameState
	 */
	public void runOneTick() {
		runEnemyMovement();
		updateGameState();

		if (getRewardListSize() == 0) {
			setExitCell();
		}

	}

	/**
	 * Retrieves the current Maze object.
	 * @return the current Maze object of the Game object
	 */
	public Maze getMyMaze() {
		return myMaze;
	}
	/**
	 * Retrieves the current size of the reward list.
	 * @return the current size of the reward list
	 */
	private int getRewardListSize() {
		return rewardList.size();
	}

	/**
	 * RRetrieves the list of rewards in the game.
	 * @return the list of rewards in the game
	 */
	public List<Reward> getRewardList() {
		return rewardList;
	}
	/**
	 * Checks whether the game is currently running.
	 * @return true if the game is running, false otherwise
	 */
	public boolean isGameRunning() {
		return (gameState == GameState.RUNNING);
	}

	/**
	 * Checks whether the game is currently idle.
	 * @return true if the game is idle, false otherwise
	 */
	public boolean isGameIdle(){
		return (gameState == GameState.IDLE);
	}

	/**
	 * Checks whether the game has been won.
	 * @return true if the game has been won, false otherwise
	 */
	public boolean isGameWon(){
		return (gameState == GameState.WIN);
	}

	/**
	 * Checks whether the game has been lost.
	 * @return true if the game has been lost, false otherwise
	 */
	public boolean isGameLost(){
		return (gameState == GameState.LOST);
	}

	/**
	 * this should be called when we pause the game
	 */
	public void setGameToIdle() {
		gameState = GameState.IDLE;
	}

	/**
	 * Sets the game to run
	 */
	public void setGameToRun(){
		gameState = GameState.RUNNING;
	}

	/**
	 * Sets the current game state to game over.
	 */
	public void setGameStateToOver(){
		gameState = GameState.gameOver;
	}

	/**
	 * Sets the current game state to game start.
	 */
	public void setGameStateToStart(){
		gameState = GameState.gameStart;
	}

	/**
	 * Sets the current game state to how to play.
	 */
	public void setGameStateToHowToPlay(){
		gameState = GameState.howToPlay;
	}
	/**
	 * Checks if the exit cell should be opened
	 */
	private void setExitCell() {
		if (rewardList.isEmpty() && !isExitCellOpen) {
			myMaze.setExitCellOpen();
			isExitCellOpen = true;
		}
	}


	/**
	 * Removes all rewards from the game by removing them from the maze and clearing the reward list.
	 */
	public void removeAllRewards() {
		myMaze.removeAllRewards();
		rewardList.removeAll(rewardList);
	}

	/**
	 * Returns the current score of the player.
	 *
	 * @return The player's score.
	 */
	public int getPlayerScore() {
		return player.getScore();
	}

	/**
	 * Moves the player entity
	 *
	 * @param input - takes a char input to move the player in the map
	 */
	public void movePlayer(char input) {
		input = Character.toLowerCase(input);
		MoveDirection move = convertCharToMoveDirection(input);
		if (!isEntityMoveValid(player, move)) {
			return;
		}
		moveEntity(player, move);
		setExitCell();
		updateGameState();
	}

	/**
	 * Runs the enemy movement algorithm.
	 * Enemies will randomly move if they are not close to player
	 */
	private void runEnemyMovement() {
		Point playerLocation = player.getLocation();
		for (Enemy enemy : enemyList) {
			Point enemyLocation = enemy.getLocation();
			MoveDirection move;
			//if (isPlayerInRange(playerLocation, enemyLocation)) { //follow player
			move = getEnemyFollowMovement(enemy);
//			} else { //random movement
//				move = getEntityRandomMovement(enemy);
//			}
			Point newEnemyLocation = enemyLocation.newMoveLocation(move);
			moveEntity(enemy, move);
		}
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
	 * Checks the entity collision at a  specific point
	 *
	 * @param entity - the entity that collides at the point
	 * @param location - location of the collision
	 * @return boolean to determine if there needs to be a swap
	 */
	private MoveCheck checkEntityCollision(Entity entity, Point location) {
		CollisionType collision = entityCollision(location);
		MoveCheck moveCheck = MoveCheck.validMove;
		switch (collision) {
			case noCollision -> moveCheck = MoveCheck.validMove;
			case player -> {//enemy moves to player
				if (entity instanceof Enemy) {
					moveCheck = MoveCheck.killPlayer;
				}
			}
			case playerEnemy -> {
				if (entity instanceof Player) {
					moveCheck = MoveCheck.killPlayer;
				}
			}
			case playerReward -> {
				if (entity instanceof Player) {
					collectReward(location);
				} else if (entity instanceof Enemy) {
					moveCheck = MoveCheck.enemyToReward;
				}
			}
			case playerTrap -> {
				if (entity instanceof Player) {
					steppedOnTrap(location);
					//player.addScore(TRAP_DAMAGE);
				} else if (entity instanceof Enemy) {
					moveCheck = MoveCheck.enemyToTrap;
				}
			}

			default -> throw new RuntimeException("Game.java checkEntityCollision(): collision type is invalid");
		}
		return moveCheck;
	}

	/**
	 * Generates the entities at the start of the level
	 *
	 * @param enemyCount - enemy count to generate enemies
	 * @param rewardCount - reward count to generate rewards
	 * @param trapCount - trap count to generate rewards
	 */
	private void entityGenerator(int enemyCount, int rewardCount, int trapCount) { //calls entityMaker and checks if the entity is placeable
		while (enemyList.size() < enemyCount) {
			placeEntityRandomlyOnMap(EntityType.enemy, 0);
		}
		int REWARD_SCORE = 20;
		while (rewardList.size() < rewardCount) {
			placeEntityRandomlyOnMap(EntityType.reward, REWARD_SCORE);
		}
		int TRAP_DAMAGE = (((rewardList.size() * REWARD_SCORE) + 100) / trapCount) + 1;
		while (trapList.size() < trapCount){
			placeEntityRandomlyOnMap(EntityType.trap, TRAP_DAMAGE);
		}
	}


	/**
	 * Places an entity randomly on the map
	 * @param entityType - entity type to place on map
	 * @param score - the score that the entity holds
	 */
	private void placeEntityRandomlyOnMap(EntityType entityType, int score){
		int width, height;
		Point newPoint;
		boolean status = true;
		Random random;
		while(status){
			random = new Random();
			width = 5 + random.nextInt(myMaze.getWidth() - 5);
			random = new Random();
			height = 5 + random.nextInt(myMaze.getHeight() - 5);
			newPoint = new Point( height, width);
			if (usedPoints.size() == 0 && myMaze.getMaze()[height][width].getCellType().equals(CellType.path)) {
				entityMaker(entityType, newPoint, score);
				usedPoints.add(newPoint);
				status = false;
				break;
			}
			for (int j = 0; j < usedPoints.size(); j++) {
				if (!usedPoints.get(j).equals(newPoint) && myMaze.getMaze()[height][width].getCellType().equals(CellType.path)) {
					entityMaker(entityType, newPoint, score);
					usedPoints.add(newPoint);
					status = false;
					break;
				}
			}
		}


	}

	/**
	 * Creates the appropriate entity dependent on entityType
	 *
	 * @param entityType - entity type to create
	 * @param location - location of entity to spawn
	 * @param score - score that the entity holds
	 */
	private void entityMaker(EntityType entityType, Point location, int score) {
		if (entityType.equals(EntityType.enemy)) {
			Enemy newEnemy = new Enemy(EntityType.enemy, location);
			enemyList.add(newEnemy);
		} else if (entityType.equals((EntityType.reward))) {
			Reward newReward = new Reward(EntityType.reward, location, score);
			rewardList.add(newReward);

		} else if (entityType.equals((EntityType.trap))) {
			Trap newTrap = new Trap(EntityType.trap, location, score);
			trapList.add(newTrap);
		}
	}


	/**
	 * Returns a valid enemy follow movement move
	 *
	 * @param entityThatFollows - the entity that follows the follow location
	 * @return move - move direction to follow
	 */
	private MoveDirection getEnemyFollowMovement(Entity entityThatFollows) {
		Point entityLocation = entityThatFollows.getLocation();
		Point playerLocation = player.getLocation();
		int down = Math.abs(playerLocation.getHeight() - entityLocation.getHeight() - 1);
		int up = Math.abs(playerLocation.getHeight() - entityLocation.getHeight() + 1);
		int right = Math.abs(playerLocation.getWidth() - entityLocation.getWidth() - 1);
		int left = Math.abs(playerLocation.getWidth() - entityLocation.getWidth() + 1);
		List<Integer> moveList = new ArrayList<>();
		moveList.add(up);
		moveList.add(down);
		moveList.add(left);
		moveList.add(right);
		Collections.sort(moveList);
		for(int i =0; i < moveList.size(); i++){
			if(moveList.get(i).equals(up)){
				if(isEntityMoveValid(entityThatFollows, MoveDirection.UP)){
					return MoveDirection.UP;
				}
			}
			else if(moveList.get(i).equals(down)){
				if(isEntityMoveValid(entityThatFollows, MoveDirection.DOWN)){
					return MoveDirection.DOWN;
				}
			}
			else if(moveList.get(i).equals(left)){
				if(isEntityMoveValid(entityThatFollows, MoveDirection.LEFT)){
					return MoveDirection.LEFT;
				}
			}
			else if(moveList.get(i).equals(right)){
				if(isEntityMoveValid(entityThatFollows, MoveDirection.RIGHT)){
					return MoveDirection.RIGHT;
				}
			}

		}
		return MoveDirection.NONE;
	}

	/**
	 * Returns a valid enemy random movement move
	 *
	 * @param entity - entity to move
	 * @return move - valid move direction of the entity
	 */
	private MoveDirection getEntityRandomMovement(Entity entity) {
		MoveDirection move = MoveDirection.NONE;
		do {
			Random random = new Random();
			int randomInt = random.nextInt(4);
			switch (randomInt) {
				case 0 -> move = MoveDirection.UP;
				case 1 -> move = MoveDirection.RIGHT;
				case 2 -> move = MoveDirection.DOWN;
				case 3 -> move = MoveDirection.LEFT;

			}
		} while (!isEntityMoveValid(entity, move));

		return move;
	}

	/**
	 * Checks if the player location is within 5 cells of the location given
	 *
	 * @param playerLocation - location of the player
	 * @param enemyLocation - location of the enemy
	 * @return boolean whether player is within 5 cells of the entity location
	 */
	private boolean isPlayerInRange(Point playerLocation, Point enemyLocation) {
		int playerLocationWidth = playerLocation.getWidth();
		int playerLocationHeight = playerLocation.getHeight();
		int enemyLocationWidth = enemyLocation.getWidth();
		int enemyLocationHeight = enemyLocation.getHeight();
		return (Math.abs(playerLocationWidth - enemyLocationWidth) == 5 || Math.abs(playerLocationHeight - enemyLocationHeight) == 5);//checks if player is in 5 cell of every direction

	}

	/**
	 * Entity given is moved depending on direction
	 * Precondition: Move should be valid
	 *
	 * @param entity - entity to move
	 * @param move - the direction in which the entity moves
	 */
	private void moveEntity(Entity entity, MoveDirection move) { // takes entity position and changes by adding X and/or Y
		if (!isEntityMoveValid(entity, move)) {
			throw new IllegalArgumentException("Game.java moveEntity(): invalid move called");
		}

		Point oldLocation = entity.getLocation();
		Point newLocation = entity.getLocation().newMoveLocation(move);
		MoveCheck moveCheck = checkEntityCollision(entity, newLocation);

		if (moveCheck == MoveCheck.validMove) {
			myMaze.swapEntity(oldLocation, newLocation);
		} else if (moveCheck == MoveCheck.killPlayer) {
			myMaze.swapEntity(oldLocation, newLocation);
			killPlayer();

			// if enemy moves collide with rewards and traps
		}else if(moveCheck == MoveCheck.enemyToReward
				|| moveCheck == MoveCheck.enemyToTrap){
			Entity entityCollided = myMaze.getEntity(newLocation);
			MoveDirection newMove = getEntityRandomMovement(entityCollided);
			newLocation = newLocation.newMoveLocation(newMove);
			myMaze.swapEntity(oldLocation, newLocation);
		}
	}

	/**
	 * Checks if the entity move is valid
	 *
	 * @param entity - entity to check the move
	 * @param move - move direction to check
	 * @return moveValid - whether the move is valid or not
	 */
	private boolean isEntityMoveValid(Entity entity, MoveDirection move) {
		Point entityLocation = entity.getLocation();
		if (move == MoveDirection.NONE) {
			return true;
		}
		return myMaze.isCellOpen(entityLocation.newMoveLocation(move));
	}

	/**
	 * Method is called when an entity moves,
	 * Checks the collisionType at location
	 *
	 * @param location - location of the collision
	 * @return collisionType that occurred
	 */
	private CollisionType entityCollision(Point location) { // subject to change based on other entities.
		Entity entity1 = myMaze.getEntity(location);
		EntityType entityType = entity1.getEntityType();

		return switch (entityType) {
			case empty -> CollisionType.noCollision;
			case player -> CollisionType.player;
			case enemy -> CollisionType.playerEnemy;
			case reward -> CollisionType.playerReward;
			case trap -> CollisionType.playerTrap;
			default -> throw new RuntimeException("Game.java entityCollision(): collision with non-allowed entity1");
		};

	}

	/**
	 * Converts char to MoveDirection enum
	 *
	 * @param input - char input to convert to MoveDirection
	 * @return move - MoveDirection to return
	 */
	private MoveDirection convertCharToMoveDirection(char input) {
		input = Character.toLowerCase(input);
		return switch (input) {
			case 'w' -> MoveDirection.UP;
			case 's' -> MoveDirection.DOWN;
			case 'd' -> MoveDirection.RIGHT;
			case 'a' -> MoveDirection.LEFT;
			default -> MoveDirection.NONE;
		};
	}

	/**
	 * Returns reward at location
	 *
	 * @param location - location of the reward
	 * @return reward - reward at location
	 */
	private Reward getReward(Point location) {
		for (Reward reward : rewardList) {
			if (location.equals(reward.getLocation())) {
				return reward;
			}
		}
		return new Reward(EntityType.empty, location, 0);
	}

	/**
	 * Returns trap at location
	 *
	 * @param location - location of the trap
	 * @return trap - trap at location
	 */
	private Trap getTrap(Point location) {
		for (Trap trap : trapList) {
			if (location.equals(trap.getLocation())) {
				return trap;
			}
		}
		return new Trap(EntityType.empty, location, 0);
	}


	private void killPlayer() {
		player.setAlive(false);
		Point playerLocation = player.getLocation();
		//getMyMaze().getMaze()[playerLocation.getHeight()][playerLocation.getWidth()].setEntity(new Empty(EntityType.empty, playerLocation));
		myMaze.setEntity(new Empty(EntityType.empty, playerLocation), playerLocation);
	}

	/**
	 * Called when player steps on a reward
	 *
	 * @param location - location of reward
	 */
	private void collectReward(Point location) {
		Reward reward = getReward(location);
		if(reward.getEntityType() != EntityType.reward){
			throw new RuntimeException("Game.java steppedOnTrap(): EntityType is not a trap!");
		}
		myMaze.setEntity(new Empty(EntityType.empty, location), location);
		player.incrementScore(reward.getScore());
		rewardList.remove(reward);


	}

	/**
	 * Called when player steps on a trap
	 *
	 * @param location - location of trap
	 */
	private void steppedOnTrap(Point location) {
		Trap trap = getTrap(location);
		if(trap.getEntityType() != EntityType.trap){
			throw new RuntimeException("Game.java steppedOnTrap(): EntityType is not a trap!");
		}
		myMaze.setEntity(new Empty(EntityType.empty, location), location);
		player.decrementScore(trap.getDamage());
		if(player.getScore() < 0){
			killPlayer();
		}
		trapList.remove(trap);
	}


	/**
	 * Updates the game state,
	 */
	private void updateGameState() {
		gameState = getCurrentGameState();
	}

	/**
	 * Returns true if the player is alive; otherwise, returns false.
	 * @return True if the player is alive; otherwise, false.
	 */
	private boolean isPlayerAlive() {
		return player.isAlive();
	}

	/**
	 * Returns true if the player has lost the game; otherwise, returns false.
	 * @return True if the player has lost the game; otherwise, false.
	 */
	private boolean hasPlayerLost() {
		return (getPlayerScore() < 0 || !isPlayerAlive());
	}

	/**
	 * Returns true if the player has won the game; otherwise, returns false.
	 * @return True if the player has won the game; otherwise, false.
	 */
	private boolean hasPlayerWon() {
		return (player.getLocation().equals(myMaze.getExitCell()));
	}


	/**
	 * Checks if the player has won or lost
	 *
	 * @return current Game State
	 */
	private GameState getCurrentGameState() {
		GameState game = GameState.RUNNING;
		if (hasPlayerLost()) {
			game = GameState.LOST;
		} else if (hasPlayerWon()) {
			game = GameState.WIN;
		}
		return game;
	}

	/**
	 * Clears all the entity lists and resets the used points list.
	 */
	private void resetEntityLists(){
		this.usedPoints.clear();
		this.enemyList.clear();
		this.rewardList.clear();
		this.trapList.clear();
	}

	/**
	 * Resets the player's location and state. If isFullReset is true, the player's score is also set to 100.
	 * @param isFullReset True if a full reset is required; otherwise, false.
	 */
	private void resetPlayer(boolean isFullReset){
		player.setAlive(true);
		player.setLocation(new Point(1, 1));
		if(isFullReset){
			player.setScore(100);
		}
	}

	/**
	 * Returns the player object.
	 * @return The player object.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns a list of all enemies in the game.
	 * @return A list of all enemies in the game.
	 */
	public List<Enemy> getEnemyList() {
		return enemyList;
	}

	/**
	 * Returns a list of all traps in the game.
	 * @return A list of all traps in the game.
	 */
	public List<Trap> getTrapList() {
		return trapList;
	}

	/**
	 * Returns the initial size of the enemy list.
	 * @return The initial size of the enemy list.
	 */
	public int getInitialEnemyListSize() {
		return initialEnemyListSize;
	}

	/**
	 * Returns the initial size of the reward list.
	 * @return The initial size of the reward list.
	 */
	public int getInitialRewardListSize() {
		return initialRewardListSize;
	}

	/**
	 * Returns the initial size of the trap list.
	 * @return The initial size of the trap list.
	 */
	public int getInitialTrapListSize() {
		return initialTrapListSize;
	}
}
