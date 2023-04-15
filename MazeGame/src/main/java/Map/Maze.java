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
	private Cell[][] maze;

	private final int HEIGHT = 18;
	private final int WIDTH = 32;
	private Point exitCell;

	/**
	 * Constructor of a maze of specified dimensions ROWS x COLS
	 */
	public Maze() {
		this.maze = new Cell[HEIGHT][WIDTH];
		do{
			makeRandomMaze();
		} while (!hasAllCornersConnected()
				|| !hasPathToAllOpenCells());
	}
	/**
	 * Creates the maze using Randomized Prim's Algorithm.
	 */
	private void makeRandomMaze() {
		createCanvas();
		buildMazePaths();
		maze[1][1].setCellType(CellType.path);
		addLoopsToMaze();
		makeSidesOpenForExitCell();

	}
	/**
	 * Makes the sides open for the exit cell.
	 */
	private void makeSidesOpenForExitCell() {
		for(int i = 1; i< HEIGHT - 2; i++){
			maze[i][1].setCellType(CellType.path);
			maze[i][WIDTH - 2].setCellType(CellType.path);
		}
		for(int i = 1; i< WIDTH - 2; i++){
			maze[1][i].setCellType(CellType.path);
			maze[HEIGHT - 2][i].setCellType(CellType.path);
		}
	}
	/**
	 * Adds loops to the maze.
	 */
	private void addLoopsToMaze() {
		for (int i = 1; i < HEIGHT - 1; i++) {
			for (int j = 1; j < WIDTH - 1; j++) {
				boolean isWall = maze[i][j].isWallOrBarricade() || maze[i][j].isWallOrBarricade();
				if (isWall) {
					boolean shouldRemove = Math.random() <= 0.5;
					boolean doesRemovalMakeEmptySquare = removalMakesEmptySquare(j, i);
					if (shouldRemove && !doesRemovalMakeEmptySquare) {
						maze[i][j].setCellType(CellType.path);
					}
				}
			}
		}
	}
	/**
	 * Determines if removing a wall would make an empty square.
	 *
	 * @param width the width of the wall to remove
	 * @param height the height of the wall to remove
	 * @return {@code true} if removing the wall would make an empty square; {@code false} otherwise
	 */
	private boolean removalMakesEmptySquare(int width, int height) {
		for (int dHeight = -1; dHeight <= 1; dHeight += 2) {
			for (int dWidth = -1; dWidth <= 1; dWidth +=2) {
				boolean isEmptyCorner = !maze[height + dHeight][width + dWidth].isWallOrBarricade();
				boolean isEmptyHorizontally = !maze[height + dHeight][width].isWallOrBarricade();
				boolean isEmptyVertically = !maze[height][width + dWidth].isWallOrBarricade();
				if (isEmptyCorner && isEmptyHorizontally && isEmptyVertically) {
					return true;
				}
			}
		}
		return false;

	}
	/**
	 * Begins the initialization of the maze, sets all the cells to barricade and then updates the perimeter to walls
	 */
	private void createCanvas() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				maze[i][j] = new Cell(new Point(i, j), CellType.barricade);
			}
		}
		for (int i = 0; i < HEIGHT; i++) {
			maze[i][0].setCellType(CellType.wall);
			maze[i][WIDTH - 1].setCellType(CellType.wall);
		}
		for (int i = 0; i < WIDTH; i++) {
			maze[0][i].setCellType(CellType.wall);
			maze[HEIGHT - 1][i].setCellType(CellType.wall);
		}
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				maze[i][j].setEntity(new Empty(EntityType.empty,new Point(i,j)));
			}
		}
	}
	/**
	 * Builds the maze pathways
	 */
	private void buildMazePaths() {
		List<Point> candidates = new ArrayList<>();
		candidates.add(new Point(HEIGHT /2, WIDTH /2));
		while(candidates.size() > 0){
			Collections.shuffle(candidates);
			Point location = candidates.get(0);
			candidates.remove(0);
			if (okToRemoveWall(location)) {
				int height = location.getHeight();
				int width  = location.getWidth();
				maze[height][width].setCellType(CellType.path);
				candidates.add(new Point(height + 1, width));
				candidates.add(new Point(height - 1, width));
				candidates.add(new Point(height, width + 1));
				candidates.add(new Point(height, width - 1));
			}

		}
	}
	/**
	 * Determines if its okay to remove a wall
	 * @param location the location of the cell
	 */
	private boolean okToRemoveWall(Point location) {
		int width = location.getWidth();
		int height = location.getHeight();
		if(width == WIDTH || height == HEIGHT){ return false;}
		boolean isWall = maze[height][width].isWallOrBarricade() || maze[height][width].isWallOrBarricade();
		boolean isTop = (height == 0);
		boolean isBottom = (height == HEIGHT - 1);
		boolean isLeft = (width == 0);
		boolean isRight = (width == WIDTH - 1);
		boolean isEdge = isTop || isBottom || isLeft || isRight;

		if (!isWall || isEdge) {
			return false;
		}

		final int MIN_WALL_COUNT_WHEN_NOT_CONNECTED = 2;
		boolean breaksMaze = (countWallsAroundCell(location) <= MIN_WALL_COUNT_WHEN_NOT_CONNECTED);
		return !breaksMaze;
	}
	/**
	 * Counts how many walls around the cell
	 * @param location the location of the cell
	 * @return wallCount
	 */
	private int countWallsAroundCell(Point location) {
		int width = location.getWidth();
		int height = location.getHeight();
		int wallCount = 0;
		wallCount += (maze[height+1][width].isWallOrBarricade() || maze[height+1][width].isWallOrBarricade()) ? 1 : 0;
		wallCount += (maze[height][width+1].isWallOrBarricade() || maze[height][width+1].isWallOrBarricade()) ? 1 : 0;
		wallCount += (maze[height-1][width].isWallOrBarricade() || maze[height-1][width].isWallOrBarricade()) ? 1 : 0;
		wallCount += (maze[height][width-1].isWallOrBarricade() || maze[height][width-1].isWallOrBarricade()) ? 1 : 0;
		return wallCount;
	}
	/**
	 * Determines if all the corners of the maze is connected
	 */
	private boolean hasAllCornersConnected(){
		final Point LOCATION_TOP_LEFT     = new Point(1, 1);
		final Point LOCATION_TOP_RIGHT    = new Point(HEIGHT - 2, 1);
		final Point LOCATION_BOTTOM_LEFT  = new Point(1, WIDTH - 2);
		final Point LOCATION_BOTTOM_RIGHT = new Point(HEIGHT - 2, WIDTH - 2);

		PathFinder pathfinder = new PathFinder(maze);

		return pathfinder.hasPath(LOCATION_TOP_LEFT, LOCATION_TOP_RIGHT)
				&& pathfinder.hasPath(LOCATION_TOP_LEFT, LOCATION_BOTTOM_LEFT)
				&& pathfinder.hasPath(LOCATION_TOP_LEFT, LOCATION_BOTTOM_RIGHT);
	}
	/**
	 * Determines if all thc cells are accessible
	 */
	private boolean hasPathToAllOpenCells(){
		final Point LOCATION_TOP_LEFT = new Point(1, 1);
		PathFinder pathfinder = new PathFinder(maze);
		for (int y = 1; y < WIDTH - 1; y++) {
			for (int x = 1; x < HEIGHT - 1; x++) {
				Point loc = new Point(x, y);
				boolean openCell = !isCellAWall(loc);
				boolean hasNoPath = !pathfinder.hasPath(LOCATION_TOP_LEFT, loc);
				if (openCell && hasNoPath) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Determines if a cell is of type wall
	 */
	private boolean isCellAWall(Point loc) {
		if (outOfRange(loc)) {
			return false;
		}
		int Width = loc.getWidth();
		int Height = loc.getHeight();
		return maze[Height][Width].isWallOrBarricade();
	}


	/**
	 * Factory to instantiate a maze and make it
	 * @return newMaze
	 */
	public static Maze generateRandomizedMaze() {
		Maze newMaze = new Maze();
		//newMaze.createMaze();
		return newMaze;
	}

	/**
	 * Return the ROWS count for maze
	 * @return ROWS
	 */
	public int getHeight() {
		return HEIGHT;
	}

	/**
	 * Return the COLS count for maze
	 * @return COLS
	 */
	public int getWidth() {
		return WIDTH;
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
		boolean outOfRangeWidth = (width < 1) || (width >= WIDTH - 1);
		boolean outOfRangeHeight = (height < 1) || (height >= HEIGHT - 1);
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
		for(int i = 1; i < HEIGHT - 1; i++){
			potentialExitCells.add(new Point(i, WIDTH - 1));
		}
		//Adding south walls in the list
		for(int i = 1; i < WIDTH - 1; i++){
			potentialExitCells.add(new Point(HEIGHT - 1, i));
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
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if(maze[j][i].getEntity().getEntityType() == EntityType.reward){
					maze[j][i].setEntity(new Empty(EntityType.empty,new Point(j,i)));
				}
			}
		}
	}
}