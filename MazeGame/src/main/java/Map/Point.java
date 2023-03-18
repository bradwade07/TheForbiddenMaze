package Map;

//import State.MoveDirection;

/**
 * A class to contain the cell block number in a 2d space of cells and entities
 *
 * @author  Tawheed Sarker Aakash
 * */

public class Point {
	private int Width;
	private int Height;

	/**
	 * constructor for a Point object, has an x and y coordinate
	 * @param width
	 * @param height
	 */
	public Point(int height, int width){
		this.Height = height;
		this.Width = width;
	}

	/**
	 * Return x coordinate of a point
	 * @return x
	 */
	public int getWidth() {
		return Width;
	}

	/**
	 * Return y coordinate of a point
	 * @return y
	 */
	public int getHeight() {
		return Height;
	}

	/**
	 * Change the x coordinate of a point
	 * @param width
	 */
	public void setWidth(int width) {
		this.Width = width;
	}

	/**
	 * change the y coordinate of a point
	 * @param height
	 */
	public void setHeight(int height) {
		this.Height = height;
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

		boolean sameWidth = (this.Width == other.Width);
		boolean sameHeight = (this.Height == other.Height);

		return (sameWidth && sameHeight) ;
	}

	/**
	 * Returns to a set of coordinates depending on a movement input
	 * @param move
	 * @return new Point(newX,newY)
	 */
	public Point newMoveLocation(MoveDirection move){
		int newWidth = Width;
		int newHeight = Height;

		switch(move){
			case UP:
				newHeight--;
				break;
			case DOWN:
				newHeight++;
				break;
			case RIGHT:
				newWidth++;
				break;
			case LEFT:
				newWidth--;
				break;
			default:
				// nothing happened
		}
		return new Point(newHeight,newWidth);
	}

}
