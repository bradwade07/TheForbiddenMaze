package Map;

/**
 * A class to contain the cell block number in a 2d space of cells and entities
 *
 * @author  Tawheed Sarker Aakash
 * */

public class Point {
	private int x;
	private int y;
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
