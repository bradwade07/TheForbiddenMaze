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

    private final Player player;
    private final List<Enemy> enemyList;
    public final List<Reward> rewardList;
    private final List<Trap> trapList;
    private Maze myMaze;
    boolean isExitCellOpen;
    GameState gameState;
    private int TRAP_DAMAGE = 0;
    private final int REWARD_SCORE = 20;

    public enum GameState {
        WIN, LOST, RUNNING, IDLE, gameOver,gameStart,howToPlay
    }


    public enum MoveCheck {
        validMove, killPlayer, enemyToTrap, enemyToReward
    }

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
     * @param enemyCount
     * @param rewardCount
     * @param trapCount
     */
    public void generateMap(int enemyCount, int rewardCount, int trapCount) {
        myMaze = Maze.generateRandomizedMaze();
        this.player.setLocation(new Point(getMyMaze().getHeight()/2, getMyMaze().getWidth()/2));
        entityGenerator(enemyCount, rewardCount, trapCount);
        placeEntitiesOnMap();
    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * Runs one tick of the game, contains enemy movement and checks the current gamestate
     */


    public void runOneTick() {
        runEnemyMovement();
        updateGameState();

        if (getRewardListSize() == 0) {
            setExitCell();
        }

    }

    public Maze getMyMaze() {
        return myMaze;
    }

    private int getRewardListSize() {
        return rewardList.size();
    }

    public List<Reward> getRewardList() {
        return rewardList;
    }

    public boolean isGameRunning() {
//		System.out.println("GameState: " + gameState);
        return (gameState == GameState.RUNNING);
    }

    public boolean isGameIdle(){
        return (gameState == GameState.IDLE);
    }

    public boolean isGameWon(){
        return (gameState == GameState.WIN);
    }

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

    public void setGameStateToOver(){
        gameState = GameState.gameOver;
    }
    public void setGameStateToStart(){
        gameState = GameState.gameStart;
    }
    public void setGameStateToHowToPlay(){
        gameState = GameState.howToPlay;
    }
    /**
     * Checks if the exit cell should be opened
     */
    private void setExitCell() {
        if (rewardList.isEmpty() && isExitCellOpen == false) {
            myMaze.setExitCellOpen();
            isExitCellOpen = true;
        }
    }


    // For testing purposes
    public void removeAllRewards() {
        myMaze.removeAllRewards();
        rewardList.removeAll(rewardList);
    }

    public int getPlayerScore() {
        return player.getScore();
    }

    /**
     * Moves the player entity
     *
     * @param input
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
            if (isPlayerinRange(playerLocation, enemyLocation)) { //follow player
                move = getEnemyFollowMovement(enemy, playerLocation);
            } else { //random movement
                move = getEntityRandomMovement(enemy);
            }
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
     * @param entity
     * @param location
     * @return boolean to determine if there needs to be a swap
     */
    private MoveCheck checkEntityCollision(Entity entity, Point location) {
        CollisionType collision = entityCollision(location);
        MoveCheck moveCheck = MoveCheck.validMove;
        switch (collision) {
            case noCollision:
                moveCheck = MoveCheck.validMove;
                break;

            case player://enemy moves to player
                if (entity instanceof Enemy) {
                    moveCheck = MoveCheck.killPlayer;
                }
                break;
            case playerEnemy:
                if (entity instanceof Player) {
                    moveCheck = MoveCheck.killPlayer;
                }
                break;
            case playerReward:
                if (entity instanceof Player) {
                    collectReward(location);
                } else if (entity instanceof Enemy) {
                    moveCheck = MoveCheck.enemyToReward;
                }
                break;
            case playerTrap:
                if (entity instanceof Player) {
                    steppedOnTrap(location);
                    //player.addScore(TRAP_DAMAGE);
                } else if (entity instanceof Enemy) {
                    moveCheck = MoveCheck.enemyToTrap;
                }
                break;
            default:
                throw new RuntimeException("Game.java checkEntityCollision(): collision type is invalid");
        }
        return moveCheck;
    }

    /**
     * Generates the entities at the start of the level
     *
     * @param enemyCount
     * @param rewardCount
     * @param trapCount
     */
    private void entityGenerator(int enemyCount, int rewardCount, int trapCount) { //calls entityMaker and checks if the entity is placeable
        while (enemyList.size() < enemyCount) {
            placeEntityRandomlyOnMap(EntityType.enemy, 0);
        }
        while (rewardList.size() < rewardCount) {
            placeEntityRandomlyOnMap(EntityType.reward, REWARD_SCORE);
            }
        TRAP_DAMAGE = (((rewardList.size()*REWARD_SCORE) + 100)/trapCount) + 1;
        while (trapList.size() < trapCount){
            placeEntityRandomlyOnMap(EntityType.trap, TRAP_DAMAGE);
        }
    }


    /**
     * Places an entity randomly on the map
     * @param entityType
     * @param score
     */
    private void placeEntityRandomlyOnMap(EntityType entityType, int score){
        List<Point> usedPoints = new ArrayList<>();
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
     * @param entityType
     * @param location
     * @param score
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
     * @param entityThatFollows
     * @param followLocation
     * @return move
     */
    private MoveDirection getEnemyFollowMovement(Entity entityThatFollows, Point followLocation) {
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
     *
     * @param entity
     * @return move
     */
    private MoveDirection getEntityRandomMovement(Entity entity) {
        MoveDirection move = MoveDirection.NONE;
        do {
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
        } while (!isEntityMoveValid(entity, move));

        return move;
    }

    /**
     * Checks if the player location is within 5 cells of the location given
     *
     * @param playerlocation
     * @param enemyLocation
     * @return
     */
    private boolean isPlayerinRange(Point playerlocation, Point enemyLocation) {
        int playerlocationWidth = playerlocation.getWidth();
        int playerlocationHeight = playerlocation.getHeight();
        int enemyLocationWidth = enemyLocation.getWidth();
        int enemyLocationHeight = enemyLocation.getHeight();
        return (Math.abs(playerlocationWidth - enemyLocationWidth) == 5 || Math.abs(playerlocationHeight - enemyLocationHeight) == 5);//checks if player is in 5 cell of every direction

    }

    /**
     * Entity given is moved depending on direction
     * Precondition: Move should be valid
     *
     * @param entity
     * @param move
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
            newLocation = newLocation.newMoveLocation(move);
            myMaze.swapEntity(oldLocation, newLocation);
        }
    }

    /**
     * Checks if the entity move is valid
     *
     * @param entity
     * @param move
     * @return moveValid
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
     * Checks the collisiontype at location
     *
     * @param location
     * @return collisiontype
     */
    private CollisionType entityCollision(Point location) { // subject to change based on other entities.
        Entity entity1 = myMaze.getEntity(location);
        EntityType entityType = entity1.getEntityType();

        switch (entityType) {
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
     *
     * @param input
     * @return move
     */
    private MoveDirection convertCharToMoveDirection(char input) {
        input = Character.toLowerCase(input);
        switch (input) {
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
     * Returns reward at location
     *
     * @param location
     * @return reward
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
     * @param location
     * @return trap
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
     * @param location
     */
    private void collectReward(Point location) {
        Reward reward = getReward(location);
        myMaze.setEntity(new Empty(EntityType.empty, location), location);
        player.incrementScore(reward.getScore());
        rewardList.remove(reward);
    }

    /**
     * Called when player steps on a trap
     *
     * @param location
     */
    private void steppedOnTrap(Point location) {
        Trap trap = getTrap(location);
        myMaze.setEntity(new Empty(EntityType.empty, location), location);
        player.decrementScore(trap.getDamage());
        if(player.getScore() < 0){
            System.out.println(player.getScore());
//            player.setScore(0);
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

    private boolean isPlayerAlive() {
        return player.isAlive();
    }

    private boolean hasPlayerLost() {
        return (getPlayerScore() < 0 || !isPlayerAlive());
    }

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

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public List<Trap> getTrapList() {
        return trapList;
    }
}
