package Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void getWidth() {
        Point p = new Point(5,10);
        assertEquals(10, p.getWidth());
    }

    @Test
    void getHeight() {
        Point p = new Point(5,10);
        assertEquals(5, p.getHeight());
    }

    @Test
    void setWidth() {
        Point p = new Point(5,10);
        p.setWidth(20);
        assertEquals(20, p.getWidth());
    }

    @Test
    void setHeight() {
        Point p = new Point(5,10);
        p.setHeight(30);
        assertEquals(30, p.getHeight());
    }

    @Test
    void testEquals() {
        Point p1 = new Point(5,10);
        Point p2 = new Point(5,10);
        assertTrue(p1.equals(p2));
    }

    @Test
    void newMoveLocation() {
        Point p1 = new Point(5,10);
        Point p2 = p1.newMoveLocation(MoveDirection.UP);
        assertEquals(4,p2.getHeight());
    }
}