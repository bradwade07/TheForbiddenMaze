package Map;

import Entities.Entity;
import Entities.EntityType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    @Test
    void getHeight() {
        Maze m = new Maze();
        assertEquals(18, m.getHeight());
    }

    @Test
    void getWidth() {
        Maze m = new Maze();
        assertEquals(32, m.getWidth());
    }

    @Test
    void getMaze() {
        Maze m = new Maze();
        assertNotNull(m.getMaze());
    }

    @Test
    void setCell() {
        Maze m = new Maze();
        Point p = new Point(5,10);
        Cell c = new Cell(p,CellType.exit_cell);
        m.setCell(c);
        assertEquals(CellType.exit_cell, m.getCellType(5,10));
    }

    @Test
    void startCell() {
        Maze m = new Maze();
        assertEquals(CellType.path,m.getCellType(1,1));
    }

    @Test
    void getExitCell() {
        Maze m = new Maze();
        assertNull(m.getExitCell());
        m.setExitCellOpen();
        assertNotNull(m.getExitCell());
    }

    @Test
    void getCellType() {
        Maze m = new Maze();
        assertEquals(CellType.path, m.getCellType(1,1));
        assertEquals(CellType.wall, m.getCellType(0,0));
    }

    @Test
    void swapEntity() {
        Maze m = new Maze();
        Point p1 = new Point(5,10);
        Point p2 = new Point(7,12);
        Entity e1 = new Entity(EntityType.enemy, p1);
        Entity e2 = new Entity(EntityType.player, p2);
        m.setEntity(e1,p1);
        m.setEntity(e2,p2);
        m.swapEntity(p1,p2);
        assertEquals(EntityType.player, m.getEntity(p1).getEntityType());
        assertEquals(EntityType.enemy, m.getEntity(p2).getEntityType());
    }
}