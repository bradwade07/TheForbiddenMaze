package Entities;

import Map.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void isAlive() {
        Player p = new Player(EntityType.player, new Point(1,1));
        assertTrue(p.isAlive());
    }

    @Test
    void setAlive() {
        Player p = new Player(EntityType.player, new Point(1,1));
        p.setAlive(false);
        assertFalse(p.isAlive());
    }

    @Test
    void setScore() {
        Player p = new Player(EntityType.player, new Point(1,1));
        p.setScore(80);
        assertEquals(80,p.getScore());
    }

    @Test
    void decrementScore() {
        Player p = new Player(EntityType.player, new Point(1,1));
        int result = p.decrementScore(10);
        assertEquals(90,result);
    }

    @Test
    void incrementScore() {
        Player p = new Player(EntityType.player, new Point(1,1));
        int result = p.incrementScore(20);
        assertEquals(120,result);
    }

    @Test
    void getScore() {
        Player p = new Player(EntityType.player, new Point(1,1));
        assertEquals(100,p.getScore());
    }
}