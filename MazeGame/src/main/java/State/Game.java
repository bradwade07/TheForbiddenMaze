package State;


import Entities.*;
import Map.*;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Game class that handles all the gameplay
 */
public class Game extends Application {

    private Player player;
    private List<Enemy> enemyList = new ArrayList<>();
    private Maze maze;

    //private int score;
    // add Cheese

    @Override
    public void start(Stage stage) throws Exception {

        maze = Maze.generateRandomizedMaze();
        player = new Player(EntityType.player, new Point(1,1));
        for(int i =0; i < enemyCount;i++){
            enemyGenerator();
        }
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
                    movePlayer(selection);
            }

            // other entities move
            keepRunning = false;
        } while (keepRunning);

    }







    // pass in an KeyEvent Handler and returns MoveDirection
    private MoveDirection getUserInput() {
        Scanner userInput = new Scanner(System.in);
        MoveDirection move;

        char input;
//        System.out.print("Enter your move [WASD?]: ");
        input = userInput.next().trim().charAt(0);
        input = Character.toLowerCase(input);

        switch (input) {
            case ('w') -> move = MoveDirection.UP;
            case ('a') -> move = MoveDirection.LEFT;
            case ('s') -> move = MoveDirection.DOWN;
            case ('d') -> move = MoveDirection.RIGHT;
            default -> throw new RuntimeException("MazeGame movePlayer(): Invalid input");
        }

        return move;
    }

    public boolean hasPlayerLost(){
        return (getPlayerScore() < 0);
    }

    public boolean hasPlayerWon(){
        // TODO: and rewards are collected
        return (player.getLocation().equals(maze.getExitCell()));
    }

    public int getPlayerScore(){
        return player.getScore();
    }

    private void placeEntitiesOnMap(){
        maze.setEntity(player, player.getLocation());
        for(int i =0; i < enemyList.size();i++){
            maze.setEntity(enemyList.get(i), enemyList.get(i).getLocation());
        }
    }

//    private void loadPlayer(){ 1 line of code, deprecated by Tawheed
//        // arbritrary values
//        player = new Player(new Point(1,1));
//    }

//    private void loadMaze(){ 1 line of code, deprecated by Tawheed
//        maze = Maze.generateRandomizedMaze();
//    }

    //public int getScore() {
//        return score;
//    }


    public boolean checkEnemyCollision(){
        return isEnemyAtLocation(player.getLocation());
    }

    // should be called whenever a movement occurs

    //TODO: get back to it
//    private void moveAndUpdateMap(Point newPos, Entity entity) {
//        if(!maze.isCellOpen(newPos)){
//            return; // cell is not open so don't move
//        }
//        // Remove entity from old position
//        int oldRow = entity.getLocation().getX();
//        int oldCol = entity.getLocation().getY();
//        // this seems like a bad idea setting it to null
//        maze.getMaze()[oldRow][oldCol].setEntity(null);
//
//        // Place entity in new position
//        int newRow = newPos.getX();
//        int newCol = newPos.getY();
//        //TODO: fix
//        //maze.getMaze()[newRow][newCol].setEntity(entity);
//
//        entity.setLocation(newPos);
//    }
//
//
//    public void movePlayer(MoveDirection move){
//        if(!isPlayerMoveValid(move)){
//            return; // throw a message maybe? or just bounce back
//        }
//
//        Point newLocation = player.getLocation().newMoveLocation(move);
//        moveAndUpdateMap(newLocation, player);
//    }
//
//
//    public boolean isPlayerMoveValid(MoveDirection move){
//        Point playerLocation = player.getLocation();
//        return maze.isCellOpen(playerLocation.newMoveLocation(move));
//    }
    //TODO: make comparator
//    public boolean isPlayerAtLocation(Point location){
//        return player.getLocation().equals(location);
//    }
//
//    public boolean isEnemyAtLocation(Point location){
//        for(Enemy enemy : enemyList){
//            Point enemyCoords = enemy.getLocation();
//            if(enemyCoords.equals(location)){
//                return true;
//            }
//        }
//        return false;
//    }

    public void enemyGenerator(){
        while (true){
            Random random = new Random();
            int x = (Math.abs(random.nextInt()) % maze.getROWS() - 5) + 5;
            random = new Random();
            int y = (Math.abs(random.nextInt()) % maze.getCOLS() - 5) + 5;
            Point newLocation = new Point(x,y);
            if(maze.isCellOpen(newLocation)){
                if(enemyList.size() == 0){
                    entityMaker(EntityType.enemy,newLocation);
                    break;
                }
                else{
                    boolean canPlace = true;
                    for (Enemy enemy : enemyList) {
                        if (enemy.getLocation().equals(newLocation)) {
                            canPlace = false;
                            break;
                        }
                    }
                    if (canPlace) {
                        entityMaker(EntityType.enemy, newLocation);
                        break;
                    }
                }
            }
        }
    }
    public void entityMaker(EntityType entityType,Point location){//has flexibility for cheese
        if(entityType.equals(EntityType.enemy)){
            Enemy newEnemy =new Enemy(EntityType.enemy, location);
            enemyList.add(newEnemy);
        }
    }

    public void enemyMovement(){
        for(int i =0; i < enemyList.size(); i++){
            Enemy currentEnemy = enemyList.get(i);
            Point playerLocation = player.getLocation();
            Point enemyLocation = currentEnemy.getLocation();
            if(isPlayerinRange(playerLocation, enemyLocation)){ //follow player
                if(playerLocation.getX() - enemyLocation.getX() < 0){ moveEntity(enemyLocation, -1, 0);}
                else if(playerLocation.getX() - enemyLocation.getX() > 0){ moveEntity(enemyLocation, 1, 0);}
                else if(playerLocation.getY() - enemyLocation.getY() < 0){ moveEntity(enemyLocation, 0, -1);}
                else if(playerLocation.getY() - enemyLocation.getY() > 0){ moveEntity(enemyLocation, 0, 1);}
            }
            else{ //random movement
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
    public boolean isPlayerinRange(Point playerlocation, Point enemyLocation){
        int playerX = playerlocation.getX();
        int playerY = playerlocation.getY();
        int enemyX = enemyLocation.getX();
        int enemyY = enemyLocation.getY();
        return (Math.abs(playerX - enemyX) == 5 || Math.abs(playerY - enemyY) == 5);//checks if player is in 5 cell of every direction

    }
    public void moveEntity(Point entityLocation,int addX,int addY){ // takes entity position and changes by adding X and/or Y
        int oldX = entityLocation.getX();
        int oldY = entityLocation.getY();
        maze.swapEntity(oldX, oldY, oldX + addX, oldY + addY);
    }
}