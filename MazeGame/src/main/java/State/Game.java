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
    private List<Enemy> enemyList = new ArrayList<>();
	private List<Reward> rewardList = new ArrayList<>();
	private List<Trap> trapList = new ArrayList<>();
	private Maze maze;


    public void start(int enemyCount, int rewardCount, int trapCount) {

        maze = Maze.generateRandomizedMaze();
        player = new Player(EntityType.player, new Point(1, 1));
	    entityGenrator(enemyCount,rewardCount,trapCount);
        placeEntitiesOnMap();

    }

    public void runGame() {
        boolean keepRunning = true;
        do {
            // get Player input
            // move player
            // move enemies
            // update map?
            // check collision
            // check win conditions
            MoveDirection selection = getUserInput();

            switch (selection) {
                case NONE:
                    // no movement
                    break;
                default:
//                    movePlayer(selection);
            }

            // other entities move
            keepRunning = false;
        } while (keepRunning);

    }


    // pass in an KeyEvent Handler and returns MoveDirection
    private MoveDirection getUserInput() {
//            Scanner userInput = new Scanner(System.in);
        MoveDirection move;

        char input = 'w';
//        System.out.print("Enter your move [WASD?]: ");
//            input = userInput.next().trim().charAt(0);
//            input = Character.toLowerCase(input);

        switch (input) {
            case ('w') -> move = MoveDirection.UP;
            case ('a') -> move = MoveDirection.LEFT;
            case ('s') -> move = MoveDirection.DOWN;
            case ('d') -> move = MoveDirection.RIGHT;
            default -> throw new RuntimeException("MazeGame movePlayer(): Invalid input");
        }

        return move;
    }

    public boolean hasPlayerLost() {
        return (getPlayerScore() < 0 || !player.isAlive());
    }

    public boolean hasPlayerWon() {
        // TODO: and rewards are collected
        return (player.getLocation().equals(maze.getExitCell()));
    }

    public int getPlayerScore() {
        return player.getScore();
    }

    private void placeEntitiesOnMap() {
        maze.setEntity(player, player.getLocation());
        for (int i = 0; i < enemyList.size(); i++) {
            maze.setEntity(enemyList.get(i), enemyList.get(i).getLocation());
        }
    }

    public CollisionType entityCollision(Point location1) { // subject to change based on other entities.
        Entity entity1 = maze.getEntity(location1);
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

    public void movePlayer(MoveDirection move) {
        if (!isPlayerMoveValid(move)) {
            return; // throw a message maybe? or just bounce back
        }

        Point newLocation = player.getLocation().newMoveLocation(move);
        CollisionType collision = entityCollision(newLocation);
        Entity entity = maze.getEntity(newLocation);

        switch (collision) {
            case empty:
                break;
            case playerEnemy:
                player.setAlive(false);
                // todo animation for death maybe?
                return;
            case playerReward:
                maze.setEntity(new Empty(EntityType.empty, newLocation), newLocation);
//   TODO             player.incrementScore();
                break;
            case playerTrap:
                maze.setEntity(new Empty(EntityType.empty, newLocation), newLocation);
//  TODO              player.decrementScore();
                break;
            default:
                throw new RuntimeException("Game.java movePlayer(): collision type is invalid");

        }
        int playerX = player.getLocation().getX();
        int playerY = player.getLocation().getY();
        int expectedX = newLocation.getX();
        int expectedY = newLocation.getY();

        maze.swapEntity(playerX, playerY, expectedX, expectedY);

    }

    //
//
    public boolean isPlayerMoveValid(MoveDirection move) {
        Point playerLocation = player.getLocation();
        return maze.isCellOpen(playerLocation.newMoveLocation(move));
    }

//    public void enemyGenerator() {
//        while (true) {
//            Random random = new Random();
//            int x = (Math.abs(random.nextInt()) % maze.getROWS() - 5) + 5;
//            random = new Random();
//            int y = (Math.abs(random.nextInt()) % maze.getCOLS() - 5) + 5;
//            Point newLocation = new Point(x, y);
//            if (maze.isCellOpen(newLocation)) {
//                if (enemyList.size() == 0) {
//                    entityMaker(EntityType.enemy, newLocation, 0);
//                    break;
//                } else {
//                    boolean canPlace = true;
//                    for (Enemy enemy : enemyList) {
//                        if (enemy.getLocation().equals(newLocation)) {
//                            canPlace = false;
//                            break;
//                        }
//                    }
//                    if (canPlace) {
//                        entityMaker(EntityType.enemy, newLocation, 0);
//                        break;
//                    }
//                }
//            }
//        }
//    }
	public void entityGenrator(int enemyCount, int rewardCount, int trapCount){ //calls entityMaker and checks if the entity is placeable
		List<Point> usedPoints = new ArrayList<>();
		Random random;
		int x, y;
		Point newPoint;
		while(enemyList.size()< enemyCount){
			boolean status = true;
			while(status){
				random = new Random();
				x = random.nextInt();
				random = new Random();
				y = random.nextInt();
				newPoint = new Point(x,y);
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
				x = random.nextInt();
				random = new Random();
				y = random.nextInt();
				newPoint = new Point(x,y);
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
				x = random.nextInt();
				random = new Random();
				y = random.nextInt();
				newPoint = new Point(x,y);
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
                int move = random.nextInt() % 4;
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
        maze.swapEntity(oldX, oldY, oldX + addX, oldY + addY);
    }
}

