package State;


import Entities.*;
import Map.*;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Game class that handles all the gameplay
 */
public class Game {

    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private Maze maze;

    private List<Reward> rewards = new ArrayList<>();

    private final int rewardsToBeCollected = 5;
    private int rewardsCollected = 0;

    GameState gameState = GameState.game;


    public void runGame() {
        boolean keepRunning = true;
        do {
            // get Player input
            // move player
            // move enemies
            // update map?
            // check collision
            //
            MoveDirection selection = getUserInput();

            switch (selection) {
                default -> {
                    movePlayer(selection);
                    // entities move
//                    if (stateOfGame == GameState.WIN) {
//                        displayWin();
//                        keepRunning = false;
//                    } else if (stateOfGame == GameState.LOSE) {
//                        displayLoss();
//                        keepRunning = false;
//                    }
                }
            }
            keepRunning = false;
        } while (keepRunning);

    }

    // Input for UI, calls UI returns MoveDirection
    private MoveDirection getUserInput() {
        Scanner userInput = new Scanner(System.in);
        MoveDirection move;
        boolean formatValid;
        char input;
        System.out.print("Enter your move [WASD?]: ");
        input = userInput.next().trim().charAt(0);
        input = Character.toLowerCase(input);

        switch (input) {
            case ('w') -> move = MoveDirection.UP;
            case ('a') -> move = MoveDirection.LEFT;
            case ('s') -> move = MoveDirection.DOWN;
            case ('d') -> move = MoveDirection.RIGHT;
            default -> throw new RuntimeException("MazeGameManager movePlayer(): Invalid input");
        }

        return move;
    }

    public boolean hasPlayerLost(){
        return (getCurrentScore() < 0);
    }

    public int getCurrentScore(){
        return player.getScore();
    }

    private void placeEntitiesOnMap(){
        maze.setEntity(player);
    }

    private void loadPlayer(){
        // arbritrary values
        player = new Player(new Point(1,1));
    }

    private void loadMaze(){
        maze = Maze.generateRandomizedMaze();
    }


    public boolean checkEnemyCollision(){
        return isEnemyAtLocation(player.getLocation());
    }

    // should be called whenever a movement occurs
    private void moveAndUpdateMap(Point newPos, Entity entity) {
        if(!maze.isCellOpen(newPos)){
            return; // cell is not open so don't move
        }
        // Remove entity from old position
        int oldRow = entity.getLocation().getX();
        int oldCol = entity.getLocation().getY();
        // this seems like a bad idea setting it to null
        maze.getMaze()[oldRow][oldCol].setEntity(null);

        // Place entity in new position
        int newRow = newPos.getX();
        int newCol = newPos.getY();
        maze.getMaze()[newRow][newCol].setEntity(entity);

        entity.setLocation(newPos);
    }


    public void movePlayer(MoveDirection move){
        if(!isPlayerMoveValid(move)){
            return; // throw a message maybe? or just bounce back
        }

        Point newLocation = player.getLocation().newMoveLocation(move);
        moveAndUpdateMap(newLocation, player);
    }


    public boolean isPlayerMoveValid(MoveDirection move){
        Point playerLocation = player.getLocation();
        return maze.isCellOpen(playerLocation.newMoveLocation(move));
    }


    public boolean isPlayerAtLocation(Point location){
        return player.getLocation().equals(location);
    }

    public boolean isEnemyAtLocation(Point location){
        for(Enemy enemy : enemies){
            Point enemyCoords = enemy.getLocation();
            if(enemyCoords.equals(location)){
                return true;
            }
        }
        return false;
    }

    public void enemiesMove(){
        for(Enemy enemy : enemies){

        }

    }


    public int getRewardsToBeCollected() {
        return rewardsToBeCollected;
    }

    public int getRewardsCollected() {
        return rewardsCollected;
    }

}


