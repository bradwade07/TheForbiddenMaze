package Map;

import Entities.*;

/**
 * A class that creates the cells for the maze
 *
 * @author  Tawheed Sarker Aakash
 * */
public class Cell {
	private Point location;
	private CellType cellType;
	private Entity entity;

	/**
	 * Constructor for cell, has an width and height coordinate for the 2d maze array, and a CellType
	 * @param location
	 * @param cellType
	 */
	public Cell(Point location, CellType cellType) {
		this.location = location;
		this.cellType = cellType;
	}

	/**
	 * Return location of a cell
	 * @return location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Return the cellType of a cell
	 * @return cellType
	 */
	public CellType getCellType() {
		return cellType;
	}

	/**
	 * set the cellType of a cell
	 * @param cellType
	 */
	public void setCellType(CellType cellType) {this.cellType = cellType;}

	/**
	 * return the entity attached to the cell
	 * @return entity
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Set the entity of the cell
	 * @param entity
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	public boolean isWallOrBarricade(){
		return cellType.equals(CellType.wall) || cellType.equals(CellType.barricade);
	}
}