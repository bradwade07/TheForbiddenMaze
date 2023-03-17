package Map;

import State.MoveDirection;

/**
 * A class to contain the cell block number in a 2d space of cells and entities
 *
 * @author  Tawheed Sarker Aakash
 * */

public class Point {
	private int x;
	private int y;

	/**
	 * constructor for a Point object, has an x and y coordinate
	 * @param x
	 * @param y
	 */
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Return x coordinate of a point
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Return y coordinate of a point
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Change the x coordinate of a point
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * change the y coordinate of a point
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Override function to check if two points are equal
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(this.getClass() != obj.getClass()){
			return false;
		}

		Point other = (Point) obj;

		boolean sameX = (this.x == other.x);
		boolean sameY = (this.y == other.y);

		return (sameX && sameY) ;
	}

	/**
	 * Returns to a set of coordinates depending on a movement input
	 * @param move
	 * @return new Point(newX,newY)
	 */
	public Point newMoveLocation(MoveDirection move){
		int newX = x;
		int newY = y;

		switch(move){
			case UP:
				newY++;
				break;
			case DOWN:
				newY--;
				break;
			case RIGHT:
				newX++;
				break;
			case LEFT:
				newY--;
				break;
			default:
				// nothing happened
		}
		return new Point(newX,newY);
	}

}
