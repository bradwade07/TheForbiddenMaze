package TextTesting;

import Map.Cell;
import Map.CellType;
import State.*;
import Entities.*;

public class Display {
	public static void print(Game myGame) {
		System.out.println();
		System.out.println("Maze: ");
		for (int i = 0; i < myGame.getMyMaze().getROWS(); i++) {
			for (int j = 0; j < myGame.getMyMaze().getCOLS(); j++) {
				Cell cell = myGame.getMyMaze().getMaze()[i][j];

				if (cell.getCellType().equals(CellType.wall)) {
					System.out.print("#");
				} else if (cell.getCellType().equals(CellType.barricade)) {
					System.out.print("#");
				} else if (cell.getCellType().equals(CellType.path)) {
					if (cell.getEntity().getEntityType().equals(EntityType.player)) {
						System.out.print("@");
					} else if (cell.getEntity().getEntityType().equals(EntityType.enemy)) {
						System.out.print("!");
					} else if (cell.getEntity().getEntityType().equals(EntityType.reward)) {
						System.out.print("$");
					} else if (cell.getEntity().getEntityType().equals(EntityType.trap)) {
						System.out.print("X");
					} else{
						System.out.print(" ");
					}
				}
			}
			System.out.println();
		}
	}
}
