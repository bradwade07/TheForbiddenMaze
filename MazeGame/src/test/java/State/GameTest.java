package State;

import Entities.EntityType;
import Entities.Player;
import Map.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void generateMap() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertEquals(2,g.getInitialEnemyListSize());
        assertEquals(4,g.getInitialRewardListSize());
        assertEquals(6,g.getInitialTrapListSize());
    }

    @Test
    void reset() {
        Game g = new Game();
        g.generateMap(2,4,6);
        g.reset(3,5,7, true);
        assertFalse(g.isExitCellOpen);
        assertEquals(3,g.getInitialEnemyListSize());
        assertEquals(5,g.getInitialRewardListSize());
        assertEquals(7,g.getInitialTrapListSize());
    }

    @Test
    void getGameState() {
        Game g = new Game();
        g.generateMap(5,5,5);
        assertEquals(Game.GameState.gameStart, g.getGameState());
    }

    @Test
    void getMyMaze() {
        //cant test specific maze as its randomized, check if it exists
        Game g = new Game();
        g.generateMap(5,5,5);
        assertNotNull(g.getMyMaze());
    }

    @Test
    void getRewardList() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertNotNull(g.getRewardList());
        assertEquals(4,g.getRewardList().size());
    }

    @Test
    void isGameRunning() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertFalse(g.isGameRunning());
        g.setGameToRun();
        assertTrue(g.isGameRunning());
    }

    @Test
    void isGameIdle() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertFalse(g.isGameIdle());
        g.setGameToIdle();
        assertTrue(g.isGameIdle());
    }

    @Test
    void isGameWon() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertFalse(g.isGameWon());
        // cant test game won as you would need the player to reach exit cell
    }

    @Test
    void isGameLost() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertFalse(g.isGameLost());
        g.getPlayer().setAlive(false);
        g.runOneTick();
        assertTrue(g.isGameLost());
    }

    @Test
    void setGameToIdle() {
        Game g = new Game();
        g.generateMap(2,4,6);
        g.setGameToIdle();
        assertEquals(Game.GameState.IDLE,g.getGameState());
    }

    @Test
    void setGameToRun() {
        Game g = new Game();
        g.generateMap(2,4,6);
        g.setGameToRun();
        assertEquals(Game.GameState.RUNNING,g.getGameState());
    }

    @Test
    void setGameStateToOver() {
        Game g = new Game();
        g.generateMap(2,4,6);
        g.setGameStateToOver();
        assertEquals(Game.GameState.gameOver,g.getGameState());
    }

    @Test
    void setGameStateToStart() {
        Game g = new Game();
        g.generateMap(2,4,6);
        g.setGameStateToOver();
        assertNotEquals(Game.GameState.gameStart,g.getGameState());
        g.setGameStateToStart();
        assertEquals(Game.GameState.gameStart,g.getGameState());
    }

    @Test
    void setGameStateToHowToPlay() {
        Game g = new Game();
        g.generateMap(2,4,6);
        g.setGameToIdle();
        assertEquals(Game.GameState.IDLE,g.getGameState());
    }

    @Test
    void removeAllRewards() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertEquals(4, g.getRewardList().size());
        g.removeAllRewards();
        assertEquals(0, g.getRewardList().size());
    }

    @Test
    void getPlayerScore() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertEquals(100,g.getPlayerScore());
    }

    @Test
    void movePlayer() {
        Game g = new Game();
        g.generateMap(1,1,1);
        Point p = new Point(2,1);
        if (g.getMyMaze().isCellOpen(p)){
            g.movePlayer('s');
            assertEquals(2,g.getPlayer().getLocation().getHeight());
            assertEquals(1,g.getPlayer().getLocation().getWidth());
        }
        else {
            g.movePlayer('d');
            assertEquals(1,g.getPlayer().getLocation().getHeight());
            assertEquals(2,g.getPlayer().getLocation().getHeight());
        }
    }

    @Test
    void getPlayer() {
        Game g = new Game();
        g.generateMap(2,4,6);
        Player p = new Player(EntityType.player, new Point(1,1));
        assertEquals(p.getLocation(),g.getPlayer().getLocation());
        assertEquals(p.getEntityType(),g.getPlayer().getEntityType());

    }

    @Test
    void getEnemyList() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertNotNull(g.getEnemyList());
    }

    @Test
    void getTrapList() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertNotNull(g.getTrapList());
    }

    @Test
    void getInitialEnemyListSize() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertEquals(2,g.getInitialEnemyListSize());
    }

    @Test
    void getInitialRewardListSize() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertEquals(4,g.getInitialRewardListSize());
    }

    @Test
    void getInitialTrapListSize() {
        Game g = new Game();
        g.generateMap(2,4,6);
        assertEquals(6,g.getInitialTrapListSize());
    }
}