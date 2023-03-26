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
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Maze {

	private static final double WALL_REMOVE_PROBABILITY = 0.5;
	private Cell[][] maze;

	private final int ROWS = 18;
	private final int COLS = 32;
	private Point exitCell;

	/**
	 * Constructor of a maze of specified dimensions ROWS x COLS
	 */
	public Maze() {
		this.maze = new Cell[ROWS][COLS];
		createCanvas();
		createMaze();
		addLoopsToMaze();
		setExitCellOpen();
	}

	/**
	 * Begins the initialization of the maze, sets all the cells to barricade and then updates the perimeter to walls
	 */
	private void createCanvas() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				maze[i][j] = new Cell(i, j, CellType.wall);
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
		return newMaze;
	}

	/**
	 * Function using Randomized Prim's Algorithm to generate the maze
	 * Takes the fact that all the inner cells were previously set to barricades and set them to paths
	 */
	public void createMaze() { //Using Randomized Prim's Algorithm to generate the maze
		createCanvas();
		// Maintain a list of candidate locations to change from a wall to a space.
		List<Point> candidates = new ArrayList<>();

		// Start with the middle element in the maze.
		candidates.add(new Point(ROWS/2,COLS/2));

		// While there are spots to investigate, keep looping.
		while (candidates.size() > 0) {
			// Randomly pick a candidate cell to investigate.
			Collections.shuffle(candidates);
			Point location = candidates.get(0);
			candidates.remove(0);

			// Remove the wall, if possible
			if (okToRemoveWall(location)) {
				int x = location.getX();
				int y = location.getY();

				// Remove wall
				maze[y][x].setCellType(CellType.path);

				// Add surrounding squares to list to explore.
				candidates.add(new Point(x + 1, y));
				candidates.add(new Point(x - 1, y));
				candidates.add(new Point(x, y + 1));
				candidates.add(new Point(x, y - 1));
			}
		}
	}

	private boolean okToRemoveWall(Point location) {
		int x = location.getX();
		int y = location.getY();
		boolean isWall = maze[y][x].getCellType().equals(CellType.wall);
		boolean isTop = (y == 0);
		boolean isBottom = (y == COLS - 1);
		boolean isLeft = (x == 0);
		boolean isRight = (x == ROWS - 1);
		boolean isEdge = isTop || isBottom || isLeft || isRight;

		if (!isWall || isEdge) {
			return false;
		}

		final int MIN_WALL_COUNT_WHEN_NOT_CONNECTED = 2;
		boolean breaksMaze = (countWallsAroundCell(location) <= MIN_WALL_COUNT_WHEN_NOT_CONNECTED);
		return !breaksMaze;
	}

	private int countWallsAroundCell(Point location) {
		int x = location.getX();
		int y = location.getY();
		int wallCount = 0;
		wallCount += maze[y+1][x].getCellType().equals(CellType.wall) ? 1 : 0;
		wallCount += maze[y][x+1].getCellType().equals(CellType.wall) ? 1 : 0;
		wallCount += maze[y-1][x].getCellType().equals(CellType.wall) ? 1 : 0;
		wallCount += maze[y][x-1].getCellType().equals(CellType.wall) ? 1 : 0;
		return wallCount;
	}

	private void addLoopsToMaze() {
		for (int y = 1; y < ROWS - 1; y++) {
			for (int x = 1; x < COLS - 1; x++) {
				if (maze[y][x].getCellType().equals(CellType.wall)) {
					boolean shouldRemove = Math.random() <= WALL_REMOVE_PROBABILITY;
					boolean doesRemovalMakeEmptySquare = removalMakesEmptySquare(x, y);
					if (shouldRemove && !doesRemovalMakeEmptySquare) {
						maze[y][x].setCellType(CellType.path);
					}
				}
			}
		}
	}

	private boolean removalMakesEmptySquare(int x, int y) {
		for (int dy = -1; dy <= 1; dy += 2) {
			for (int dx = -1; dx <= 1; dx +=2) {
				boolean isEmptyCorner = !(maze[y + dy][x + dx].getCellType().equals(CellType.wall));
				boolean isEmptyHorizontally = !(maze[y + dy][x].getCellType().equals(CellType.wall));
				boolean isEmptyVertically = !(maze[y][x + dx].getCellType().equals(CellType.wall));
				if (isEmptyCorner && isEmptyHorizontally && isEmptyVertically) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Return the ROWS count for maze
	 * @return ROWS
	 */
	public int getROWS() {
		return ROWS;
	}

	/**
	 * Return the COLS count for maze
	 * @return COLS
	 */
	public int getCOLS() {
		return COLS;
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
		int x = cell.getLocation().getX();
		int y = cell.getLocation().getY();
		maze[x][y] = cell;
	}

	/**
	 * Checks if the point is outside the map (including outer walls)
	 * @param location
	 * @return (outOfRangeX || outOfRangeY)
	 */
	private boolean outOfRange(Point location) {
		int x = location.getX();
		int y = location.getY();
		boolean outOfRangeX = (x < 1) || (x >= ROWS - 1);
		boolean outOfRangeY = (y < 1) || (y >= COLS - 1);
		return (outOfRangeX || outOfRangeY);
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

		int x = location.getX();
		int y = location.getY();

		CellType cellType = maze[x][y].getCellType();
		return (cellType.equals(CellType.path) || cellType.equals(CellType.exit_cell));
	}

	/**
	 * Return the entity at a given location in the maze
	 * @param location
	 * @return Entity
	 */
	public Entity getEntity(Point location) {
		int x = location.getX();
		int y = location.getY();
		return maze[x][y].getEntity();
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
		int x = location.getX();//entity.getLocation().getX();
		int y = location.getY();//entity.getLocation().getY();
		maze[x][y].setEntity(entity);
	}

	/**
	 * return the cell type at a given coordinate
	 * @param x
	 * @param y
	 * @return
	 */
	public CellType getCellType(int x, int y) {
		return maze[x][y].getCellType();
	}

	/**
	 * Swaps the entities at two points and updating accordingly
	 * @param point1
	 * @param point2
	 */
	public void swapEntity(Point point1, Point point2) {
		int x1 = point1.getX();
		int y1 = point1.getY();
		int x2 = point2.getX();
		int y2 = point2.getY();

		Entity entity1 = maze[x1][y1].getEntity();
		Entity entity2 = maze[x2][y2].getEntity();
		maze[x1][y1].setEntity(entity2);
		maze[x2][y2].setEntity(entity1);

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
		for(int i =1; i < ROWS - 1;i++){
			potentialExitCells.add(new Point(i,COLS - 1));
		}
		//Adding south walls in the list
		for(int i =1; i < COLS - 1;i++){
			potentialExitCells.add(new Point(ROWS - 1,i));
		}
		Random random = new Random();
		Point choice = potentialExitCells.get(random.nextInt(potentialExitCells.size()));

		int chosenX = choice.getX();
		int chosenY = choice.getY();
		if(maze[chosenX][chosenY].getCellType().equals(CellType.wall)){
			maze[chosenX][chosenY].setCellType(CellType.exit_cell);
			exitCell = choice;
		}
	}

	/**
	 * Removes all the rewards from the maze
	 */
	public void removeAllRewards(){
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if(maze[i][j].getEntity().getEntityType() == EntityType.reward){
					maze[i][j].setEntity(new Empty(EntityType.empty,new Point(i,j)));
				}
			}
		}
	}
}
