package Map;

import Entities.Entity;

/**
 * A class that creates the cells for the maze
 *
 * @author  Tawheed Sarker Aakash
 * */
public class Cell {
	private Point location;
	private CellType cellType;
	//TODO: update based on entity package
	private Entity entity;


	public Cell(int x, int y, CellType cellType) {
		this.location = new Point(x,y);
		this.cellType = cellType;
		// TODO: update based on entity package(2)
		//this.entity = new Empty(x,y);
	}

	public Point getLocation() {
		return location;
	}


	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {this.cellType = cellType;}

//  TODO: update based on entity package (3)
//	public Entity getEntity() {
//		return entity;
//	}
//
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
