/**
 * A class that creates maze using Randomized Prim's Algorithm
 *
 * @author Tawheed Sarker Aakash
 * @see {@link https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Prim's_algorithm}
 */
package Map;

import Entities.Empty;
import Entities.Entity;
import Entities.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
	private Cell[][] maze;

	private final int Height = 18;
	private final int Width = 32;
	private Point exitCell;

	/**
	 * Constructor of a maze of specified dimensions ROWS x COLS
	 */
	public Maze() {
		this.maze = new Cell[Height][Width];
	}

	/**
	 * Begins the initialization of the maze, sets all the cells to barricade and then updates the perimeter to walls
	 */
	private void createCanvas() {
		for (int i = 0; i < Height; i++) {
			for (int j = 0; j < Width; j++) {
				maze[i][j] = new Cell(i, j, CellType.barricade);
			}
		}
		for (int i = 0; i < Height; i++) {
			maze[i][0].setCellType(CellType.wall);
			maze[i][Width - 1].setCellType(CellType.wall);
		}
		for (int i = 0; i < Width; i++) {
			maze[0][i].setCellType(CellType.wall);
			maze[Height - 1][i].setCellType(CellType.wall);
		}
		for (int i = 0; i < Height; i++) {
			for (int j = 0; j < Width; j++) {
				maze[i][j].setEntity(new Empty(EntityType.empty,new Point(i,j)));
			}
		}
	}

	/**
	 * Factory to instantiate a maze and make it
	 * @return newMaze
	 */
	public static Maze generateRandomizedMaze() {
		Maze newMaze = new Maze();
		newMaze.createMaze();
		return newMaze;
	}

	/**
	 * Function using Randomized Prim's Algorithm to generate the maze
	 * Takes the fact that all the inner cells were previously set to barricades and set them to paths
	 */
	public void createMaze() { //Using Randomized Prim's Algorithm to generate the maze
		createCanvas();
		List<Cell> neighborCells = new ArrayList<>();
		Cell currentCell = maze[1][1];
		currentCell.setCellType(CellType.path);

		Cell northNeighbor = null, eastNeighbor = null, westNeighbor = null, southNeighbor = null;
		if (currentCell.getLocation().getHeight() - 2 > 0) {
			northNeighbor = maze[currentCell.getLocation().getHeight() - 2][currentCell.getLocation().getWidth()];
		}
		if (currentCell.getLocation().getWidth() + 2 < Width - 1) {
			eastNeighbor = maze[currentCell.getLocation().getHeight()][currentCell.getLocation().getWidth() + 2];
		}
		if (currentCell.getLocation().getWidth() - 2 > 0) {
			westNeighbor = maze[currentCell.getLocation().getHeight()][currentCell.getLocation().getWidth() - 2];
		}
		if (currentCell.getLocation().getHeight() + 2 < Height - 1) {
			southNeighbor = maze[currentCell.getLocation().getHeight() + 2][currentCell.getLocation().getWidth()];
		}

		if (northNeighbor != null) {
			if (!northNeighbor.getCellType().equals(CellType.wall) || !northNeighbor.getCellType().equals(CellType.barricade)) {
				neighborCells.add(northNeighbor);
			}
		}
		if (eastNeighbor != null) {
			if (!eastNeighbor.getCellType().equals(CellType.wall) || !eastNeighbor.getCellType().equals(CellType.barricade)) {
				neighborCells.add(eastNeighbor);
			}
		}
		if (westNeighbor != null) {
			if (!westNeighbor.getCellType().equals(CellType.wall) || !westNeighbor.getCellType().equals(CellType.barricade)) {
				neighborCells.add(westNeighbor);
			}
		}
		if (southNeighbor != null) {
			if (!southNeighbor.getCellType().equals(CellType.wall) || !southNeighbor.getCellType().equals(CellType.barricade)) {
				neighborCells.add(southNeighbor);
			}
		}
		while (neighborCells.size() > 0) {
			Random random = new Random();
			int choice = Math.abs(random.nextInt()) % neighborCells.size();
			currentCell = neighborCells.get(choice);
			currentCell.setCellType(CellType.path);
			Point currentCellLocation = currentCell.getLocation();
			random = new Random();
			int choice2;
			boolean status = true;
			while (status) { //This loop chooses from 1 of the 4 neighboring cells of the chosen neighborCell and connects tries to connect back to the generated maze structure
				choice2 = Math.abs(random.nextInt()) % 4;
				switch (choice2) {
					case 0 -> {
						if ((currentCellLocation.getHeight() + 2 < Height - 1) && maze[currentCellLocation.getHeight() + 2][currentCellLocation.getWidth()].getCellType().equals(CellType.path)) {
							maze[currentCellLocation.getHeight() + 1][currentCellLocation.getWidth()].setCellType(CellType.path);
							status = false;

						}
					}
					case 1 -> {
						if ((currentCellLocation.getHeight() - 2 > 0) && maze[currentCellLocation.getHeight() - 2][currentCellLocation.getWidth()].getCellType().equals(CellType.path)) {
							maze[currentCellLocation.getHeight() - 1][currentCellLocation.getWidth()].setCellType(CellType.path);
							status = false;
						}
					}
					case 2 -> {
						if ((currentCellLocation.getWidth() + 2 < Width - 1) && maze[currentCellLocation.getHeight()][currentCellLocation.getWidth() + 2].getCellType().equals(CellType.path)) {
							maze[currentCellLocation.getHeight()][currentCellLocation.getWidth() + 1].setCellType(CellType.path);
							status = false;
						}
					}
					case 3 -> {
						if ((currentCellLocation.getWidth() - 2 > 0) && maze[currentCellLocation.getHeight()][currentCellLocation.getWidth() - 2].getCellType().equals(CellType.path)) {
							maze[currentCellLocation.getHeight()][currentCellLocation.getWidth() - 1].setCellType(CellType.path);
							status = false;
						}
					}
				}
			}

			//this set of 4 if else blocks chooses potential neighbors to be added to the neighborCells list
			if (currentCell.getLocation().getHeight() - 2 > 0) {
				northNeighbor = maze[currentCell.getLocation().getHeight() - 2][currentCell.getLocation().getWidth()];
			} else {
				northNeighbor = null;
			}
			if (currentCell.getLocation().getWidth() + 2 < Width - 1) {
				eastNeighbor = maze[currentCell.getLocation().getHeight()][currentCell.getLocation().getWidth() + 2];
			} else {
				eastNeighbor = null;
			}
			if (currentCell.getLocation().getWidth() - 2 > 0) {
				westNeighbor = maze[currentCell.getLocation().getHeight()][currentCell.getLocation().getWidth() - 2];
			} else {
				westNeighbor = null;
			}
			if (currentCell.getLocation().getHeight() + 2 < Height - 1) {
				southNeighbor = maze[currentCell.getLocation().getHeight() + 2][currentCell.getLocation().getWidth()];
			} else {
				southNeighbor = null;
			}

			//this set of 4 if statements check if the potential neighbors can be added by checking conditions required before adding
			if (northNeighbor != null && !northNeighbor.getCellType().equals(CellType.path)) {
				if (!northNeighbor.getCellType().equals(CellType.wall) || !northNeighbor.getCellType().equals(CellType.barricade)) {
					neighborCells.add(northNeighbor);
				}
			}
			if (eastNeighbor != null && !eastNeighbor.getCellType().equals(CellType.path)) {
				if (!eastNeighbor.getCellType().equals(CellType.wall) || !eastNeighbor.getCellType().equals(CellType.barricade)) {
					neighborCells.add(eastNeighbor);
				}
			}
			if (westNeighbor != null && !westNeighbor.getCellType().equals(CellType.path)) {
				if (!westNeighbor.getCellType().equals(CellType.wall) || !westNeighbor.getCellType().equals(CellType.barricade)) {
					neighborCells.add(westNeighbor);
				}
			}
			if (southNeighbor != null && !southNeighbor.getCellType().equals(CellType.path)) {
				if (!southNeighbor.getCellType().equals(CellType.wall) || !southNeighbor.getCellType().equals(CellType.barricade)) {
					neighborCells.add(southNeighbor);
				}
			}
			neighborCells.remove(choice);
		}
		for (int i = 1; i < Height - 1; i++) {
			maze[i][Width - 2].setCellType(CellType.path);
		}
		for (int i = 1; i < Width - 1; i++) {
			maze[Height - 2][i].setCellType(CellType.path);
		}
	}

	/**
	 * Return the ROWS count for maze
	 * @return ROWS
	 */
	public int getHeight() {
		return Height;
	}

	/**
	 * Return the COLS count for maze
	 * @return COLS
	 */
	public int getWidth() {
		return Width;
	}

	/**
	 * Return the maze as a 2d array of cells
	 * @return maze
	 */
	public Cell[][] getMaze() {
		return maze;
	}

	/**
	 * pass in a cell and update Maze to include it at the new cells x and y coordinate
	 * @param cell
	 */
	public void setCell(Cell cell) {
		int width = cell.getLocation().getWidth();
		int height = cell.getLocation().getHeight();
		maze[height][width] = cell;
	}

	/**
	 * Checks if the point is outside the map (including outer walls)
	 * @param location
	 * @return (outOfRangeX || outOfRangeY)
	 */
	private boolean outOfRange(Point location) {
		int width = location.getWidth();
		int height = location.getHeight();
		boolean outOfRangeWidth = (width < 1) || (width >= Width - 1);
		boolean outOfRangeHeight = (height < 1) || (height >= Height - 1);
		return (outOfRangeWidth || outOfRangeHeight);
	}

	/**
	 * Check if the cell is open
	 * @param location
	 * @return
	 */
	public boolean isCellOpen(Point location) {
		if (outOfRange(location)) {
			if(!location.equals(exitCell))
				return false;
		}

		int width = location.getWidth();
		int height = location.getHeight();

		CellType cellType = maze[height][width].getCellType();
		return (cellType.equals(CellType.path) || cellType.equals(CellType.exit_cell));
	}

	/**
	 * Return the entity at a given location in the maze
	 * @param location
	 * @return Entity
	 */
	public Entity getEntity(Point location) {
		int width = location.getWidth();
		int height = location.getHeight();
		return maze[height][width].getEntity();
	}

	/**
	 * return the coordinate of the exit cell
	 * @return exitCell
	 */
	public Point getExitCell() {
		return exitCell;
	}

	/**
	 * Set the entity at a specified location to a new entity passed in as a parameter
	 * @param entity
	 * @param location
	 */
	public void setEntity(Entity entity, Point location) {
		int width = location.getWidth();//entity.getLocation().getX();
		int height = location.getHeight();//entity.getLocation().getY();
		maze[height][width].setEntity(entity);
	}

	/**
	 * return the cell type at a given coordinate
	 * @param width
	 * @param height
	 * @return
	 */
	public CellType getCellType(int width, int height) {
		return maze[width][height].getCellType();
	}

	/**
	 * Swaps the entities at two points and updating accordingly
	 * @param point1
	 * @param point2
	 */
	public void swapEntity(Point point1, Point point2) {
		int width1 = point1.getWidth();
		int height1 = point1.getHeight();
		int width2 = point2.getWidth();
		int height2 = point2.getHeight();

		Entity entity1 = maze[height1][width1].getEntity();
		Entity entity2 = maze[height2][width2].getEntity();
		maze[height1][width1].setEntity(entity2);
		maze[height2][width2].setEntity(entity1);

		Point temp = entity1.getLocation();


		entity1.setLocation(entity2.getLocation());
		entity2.setLocation(temp);

	}

	/**
	 * Sets the Exit Cell open
	 */
	public void setExitCellOpen() {
		List<Point> potentialExitCells = new ArrayList<>();
		//Adding east walls in the list
		for(int i = 1; i < Height - 1; i++){
			potentialExitCells.add(new Point(i, Width - 1));
		}
		//Adding south walls in the list
		for(int i = 1; i < Width - 1; i++){
			potentialExitCells.add(new Point(Height - 1, i));
		}
		Random random = new Random();
		Point choice = potentialExitCells.get(random.nextInt(potentialExitCells.size()));

		int chosenWidth = choice.getWidth();
		int chosenHeight = choice.getHeight();
		if(maze[chosenHeight][chosenWidth].getCellType().equals(CellType.wall)){
			maze[chosenHeight][chosenWidth].setCellType(CellType.exit_cell);
			exitCell = choice;
		}
	}

	/**
	 * Removes all the rewards from the maze
	 */
	public void removeAllRewards(){
		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				if(maze[j][i].getEntity().getEntityType() == EntityType.reward){
					maze[j][i].setEntity(new Empty(EntityType.empty,new Point(j,i)));
				}
			}
		}
	}
}
