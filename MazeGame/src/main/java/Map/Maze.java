/**
 * A class that creates maze using Randomized Prim's Algorithm
 * @see {@link https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Prim's_algorithm} Maze
 *
 * @author  Tawheed Sarker Aakash
 * */
package Map;

import Entities.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
	private Cell[][] maze;

	//TODO: update ROWS and COLS based on screen ratio values
	private final int ROWS = 32;
	private final int COLS = 18;

	private Point exitCell;


	public Maze() {
		this.maze = new Cell[ROWS][COLS];
	}
	private void createCanvas() { //Creates the outer walls of the maze and sets the inner cells as wall to create the maze
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				maze[i][j] = new Cell(i,j, CellType.barricade);
			}
		}

		for(int i =0; i < ROWS; i++){
			maze[i][0].setCellType(CellType.wall);
			maze[i][COLS - 1].setCellType(CellType.wall);
		}
		for(int i = 0; i < COLS;i++){
			maze[0][i].setCellType(CellType.wall);
			maze[ROWS - 1][i].setCellType(CellType.wall);
		}

		// choose a random outer wall and set as exit cell
		Random random = new Random();
		int x = random.nextInt(ROWS);
		random = new Random();
		int y =random.nextInt(COLS);
		random = new Random();
		int side = random.nextInt(2);

		switch(side){
			case(0):
				maze[x][0].setCellType(CellType.exit_cell);
				exitCell = maze[x][0].getLocation();
				break;
			case(1):
				maze[0][y].setCellType(CellType.exit_cell);
				exitCell = maze[0][y].getLocation();
				break;
			default:
				throw new RuntimeException("Maze.java createCanvas(): Exit cell not created");
		}

	}

	// Static factory method to create maze
	public static Maze generateRandomizedMaze(){
		Maze maze = new Maze();
		maze.createMaze();
		return maze;
	}


	public void createMaze(){ //Using Randomized Prim's Algorithm to generate the maze
		createCanvas();
		List<Cell> neighborCells = new ArrayList<>();
		Cell currentCell = maze[1][1];
		currentCell.setCellType(CellType.path);

		Cell northNeighbor = null, eastNeighbor = null, westNeighbor = null, southNeighbor = null;
		if(currentCell.getLocation().getY() - 2 > 0){northNeighbor = maze[currentCell.getLocation().getX()][currentCell.getLocation().getY() - 2];}
		if(currentCell.getLocation().getX() + 2 < ROWS - 1){eastNeighbor = maze[currentCell.getLocation().getX() + 2][currentCell.getLocation().getY()];}
		if(currentCell.getLocation().getX() - 2 > 0){westNeighbor = maze[currentCell.getLocation().getX() - 2][currentCell.getLocation().getY()];}
		if(currentCell.getLocation().getY() + 2 < COLS - 1 ){southNeighbor = maze[currentCell.getLocation().getX()][currentCell.getLocation().getY() +2];}

		if(northNeighbor != null){
			if(!northNeighbor.getCellType().equals(CellType.wall)|| !northNeighbor.getCellType().equals(CellType.barricade)){
				neighborCells.add(northNeighbor);
			}
		}
		if(eastNeighbor != null){
			if(!eastNeighbor.getCellType().equals(CellType.wall) || !eastNeighbor.getCellType().equals(CellType.barricade)){
				neighborCells.add(eastNeighbor);
			}
		}
		if(westNeighbor != null){
			if(!westNeighbor.getCellType().equals(CellType.wall) || !westNeighbor.getCellType().equals(CellType.barricade)){
				neighborCells.add(westNeighbor);
			}
		}
		if(southNeighbor != null){
			if(!southNeighbor.getCellType().equals(CellType.wall) || !southNeighbor.getCellType().equals(CellType.barricade)){
				neighborCells.add(southNeighbor);
			}
		}
		while(neighborCells.size() > 0) {
			Random random = new Random();
			int choice = Math.abs( random.nextInt()) % neighborCells.size();
			currentCell = neighborCells.get(choice);
			currentCell.setCellType(CellType.path);
			Point currentCellLocation = currentCell.getLocation();
			random = new Random();
			int choice2;
			boolean status = true;
			while(status){ //This loop chooses from 1 of the 4 neighboring cells of the chosen neighborCell and connects tries to connect back to the generated maze structure
				choice2 = Math.abs( random.nextInt()) % 4;
				switch (choice2) {
					case 0 -> {
						if ((currentCellLocation.getY() + 2 < COLS - 1) && maze[currentCellLocation.getX()][currentCellLocation.getY() + 2].getCellType().equals(CellType.path)) {
							maze[currentCellLocation.getX()][currentCellLocation.getY() + 1].setCellType(CellType.path);
							status = false;

						}
					}
					case 1 -> {
						if ((currentCellLocation.getY() - 2 > 0) && maze[currentCellLocation.getX()][currentCellLocation.getY() - 2].getCellType().equals(CellType.path)) {
							maze[currentCellLocation.getX()][currentCellLocation.getY() - 1].setCellType(CellType.path);
							status = false;
						}
					}
					case 2 -> {
						if ((currentCellLocation.getX() + 2 < ROWS - 1) && maze[currentCellLocation.getX() + 2][currentCellLocation.getY()].getCellType().equals(CellType.path)) {
							maze[currentCellLocation.getX() + 1][currentCellLocation.getY()].setCellType(CellType.path);
							status = false;
						}
					}
					case 3 -> {
						if ((currentCellLocation.getX() - 2 > 0) && maze[currentCellLocation.getX() - 2][currentCellLocation.getY()].getCellType().equals(CellType.path)) {
							maze[currentCellLocation.getX() - 1][currentCellLocation.getY()].setCellType(CellType.path);
							status = false;
						}
					}
				}
			}

			//this set of 4 if else blocks chooses potential neighbors to be added to the neighborCells list
			if (currentCell.getLocation().getY() - 2 > 0) {
				northNeighbor = maze[currentCell.getLocation().getX()][currentCell.getLocation().getY() - 2];
			}
			else{
				northNeighbor = null;
			}
			if (currentCell.getLocation().getX() + 2 < ROWS - 1) {
				eastNeighbor = maze[currentCell.getLocation().getX() + 2][currentCell.getLocation().getY()];
			}
			else{
				eastNeighbor = null;
			}
			if (currentCell.getLocation().getX() - 2 > 0) {
				westNeighbor = maze[currentCell.getLocation().getX() - 2][currentCell.getLocation().getY()];
			}
			else{
				westNeighbor = null;
			}
			if (currentCell.getLocation().getY() + 2 < COLS - 1) {
				southNeighbor = maze[currentCell.getLocation().getX()][currentCell.getLocation().getY() + 2];
			}
			else{
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
		// TODO: was made for mouse placement, see if the logic sticks
//		maze[1][1].setCellType(CellType.path);
//		maze[1][18].setCellType(CellType.path);
//		maze[13][1].setCellType(CellType.path);
//		maze[13][18].setCellType(CellType.path);
	}
	public int getROWS() {
		return ROWS;
	}

	public int getCOLS() {
		return COLS;
	}
	public Cell[][] getMaze() {
		return maze;
	}
	public void setCell(Cell cell){
		int x = cell.getLocation().getX();
		int y = cell.getLocation().getY();
		maze[x][y] = cell;
	}

	private boolean outOfRange(Point location){
		int x = location.getX();
		int y = location.getY();
		boolean outOfRangeX = (x < 0) || (x >= ROWS);
		boolean outOfRangeY = (y < 0) ||  (y >= COLS);
		return (outOfRangeX || outOfRangeY);
	}

	public boolean isCellWall(Point location){
		if(outOfRange(location)){
			return false;
		}
		int x = location.getX();
		int y = location.getY();

		return (maze[x][y].getCellType() == CellType.wall);
	}

	public boolean isCellOpen(Point location){
		if(outOfRange(location)){
			return false;
		}

		int x = location.getX();
		int y = location.getY();
		return !(isCellWall(location));
	}


	public Point getExitCell() {
		return exitCell;
	}

	//TODO: update based on entity package (4)
	public void setEntity(Entity entity){
		int x = entity.getLocation().getX();
		int y =  entity.getLocation().getY();
		maze[x][y].setEntity(entity);
	}
	public CellType getCellType(int x, int y){
		return maze[x][y].getCellType();
	}

	//TODO: update based on entity package (5)
	//public Entity getEntity(int x, int y){return maze[x][y].getEntity();}
}
