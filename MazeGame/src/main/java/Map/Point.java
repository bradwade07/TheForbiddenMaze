package Map;

/**
 * A class to contain the cell block number in a 2d space of cells and entities
 *
 * @author  Tawheed Sarker Aakash
 *
 */

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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

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

	// Returns a copy of the new move location depending on move input
	public Point newMoveLocation(MoveDirection move){
		int vertical = this.x;
		int horizontal = this.y;

		switch(move){
			case UP:
				vertical--;
				break;
			case DOWN:
				vertical++;
				break;
			case RIGHT:
				horizontal++;
				break;
			case LEFT:
				horizontal--;
				break;
			default:
				// nothing happened
		}
		return new Point(vertical,horizontal);
	}

}
