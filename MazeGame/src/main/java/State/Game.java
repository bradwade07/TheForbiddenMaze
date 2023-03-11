package State;


import Entities.*;
import Map.*;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Game class that handles all the gameplay
 */
public class Game extends Application {

    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private Maze maze;

    private int score;


    @Override
    public void start(Stage stage) throws Exception {

        loadMaze();
        loadPlayer();
        placeEntitiesOnMap();

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

    public int getScore() {
        return score;
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
}


