package Entities;

import Map.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void getEntityType() {
        Entity e = new Entity(EntityType.player, new Point(1,1));
        assertEquals(EntityType.player, e.getEntityType());
    }

    @Test
    void setEntityType() {
        Entity e = new Entity(EntityType.player, new Point(1,1));
        e.setEntityType(EntityType.enemy);
        assertEquals(EntityType.enemy, e.getEntityType());
    }

    @Test
    void getLocation() {
        Entity e = new Entity(EntityType.player, new Point(1,1));
        assertEquals(new Point(1,1), e.getLocation());
    }

    @Test
    void setLocation() {
        Entity e = new Entity(EntityType.player, new Point(1,1));
        e.setLocation(new Point(3,3));
        assertEquals(new Point(3,3), e.getLocation());
    }
}