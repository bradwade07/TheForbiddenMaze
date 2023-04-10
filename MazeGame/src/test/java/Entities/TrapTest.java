package Entities;

import Map.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrapTest {

    @Test
    void dealDamage() {
        Player p = new Player(EntityType.player, new Point(1,1));
        Trap t = new Trap(EntityType.trap, new Point(1,1), 50);
        t.dealDamage(p);
        assertFalse(t.trapStatus());
        assertEquals(50,p.getScore());
    }

    @Test
    void trapStatus() {
        Trap t = new Trap(EntityType.trap, new Point(1,1), 50);
        assertTrue(t.trapStatus());
    }

    @Test
    void getDamage() {
        Trap t = new Trap(EntityType.trap, new Point(1,1), 50);
        assertEquals(50, t.getDamage());
    }
}