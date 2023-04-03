package Map;

import Entities.Entity;
import Entities.EntityType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void getLocation() {
        Point p = new Point(5,10);
        Cell c = new Cell(p,CellType.path);
        assertEquals(new Point(5,10), c.getLocation());
    }

    @Test
    void getCellType() {
        Point p = new Point(5,10);
        Cell c = new Cell(p,CellType.path);
        assertEquals(CellType.path, c.getCellType());
    }

    @Test
    void setCellType() {
        Point p = new Point(5,10);
        Cell c = new Cell(p,CellType.path);
        c.setCellType(CellType.wall);
        assertEquals(CellType.wall, c.getCellType());
    }

//    @Test
//    void getEntity() {
//        Cell c = new Cell(5,10,CellType.path);
//        c.setEntity(new Entity(EntityType.player, new Point(5,10)));
//        assertEquals(new Entity(EntityType.player, new Point(5,10)), c.getEntity());
//    }
//
//    @Test
//    void setEntity() {
//        Cell c = new Cell(5,10,CellType.path);
//        c.setEntity(new Entity(EntityType.enemy, new Point(5,10)));
//        assertEquals(new Entity(EntityType.enemy, new Point(5,10)), c.getEntity());
//    }
}