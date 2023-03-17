package TextTesting;

import Map.Cell;
import Map.CellType;
import Entities.*;
import State.Game;

import java.util.Scanner;

public class Display {
	private final Game game;

	private final char[] USER_KEYS = {'w','a','s','d', '?', 'm', 'e'};
	private enum GameState {
		WIN,
		LOSE,
		RUNNING
	}

	public Display(){
		game = new Game();
	}


	public void runGame(){
		game.start(4,4,4);
		boolean keepRunning = true;
		GameState stateOfGame;
		displayWelcomeMessage();
		displayInstructionsMessage();
		do {
			printMaze();
			char selection = getUserInput();
			switch (selection) {
				case('m') -> game.removeAllRewards();
				case('e') -> game.setExitCellOpen();
				case ('?') -> displayInstructionsMessage();
				default -> {
					stateOfGame = movePlayer(selection);
					if (stateOfGame == GameState.WIN) {
						printMaze();
						displayWin();
						keepRunning = false;
					} else if (stateOfGame == GameState.LOSE) {
						printMaze();
						displayLoss();
						keepRunning = false;
					}
				}
			}
			game.runEnemyMovement();

		} while (keepRunning);
	}


	// Precondition: input is well-formed i.e. one of w, a, s, d
	// Post-condition: player is moved given that the cell they wish
	//                 to move to isn't a wall or out of bounds. The game's
	//                 view is updated to match.
	private GameState movePlayer(char input) {
		game.movePlayer(input);
		GameState result;

		if (game.hasPlayerLost()) {
			result = GameState.LOSE;
		} else if (game.hasPlayerWon()) {
			result = GameState.WIN;
		}else if (game.isPlayerAlive()) {
			result = GameState.RUNNING;
		} else {
			throw new IllegalArgumentException("Display.java movePlayer(): gameState invalid");
		}
		return result;
	}

	private void displayInstructionsMessage(){
		System.out.println("INSTRUCTIONS: ");
		System.out.println("\tCollect all the rewards before reaching Exit Cell!");
		System.out.println("LEGEND:");
		System.out.println("\t#: Wall");
		System.out.println("\t@: Player");
		System.out.println("\t!: Enemy");
		System.out.println("\t$: Reward");
		System.out.println("\tX: Trap");
		System.out.println("MOVES:");
		System.out.println("\tUse W (up), A (left), S (down) and D (right) to move.");
		System.out.println("\t? to reveal these instructions again");
		System.out.println("\tm to move all rewards");
		System.out.println("\te to open exit cell");
		System.out.println("\t(You must press enter after each input).");
	}

	public char getUserInput() {
		Scanner userInput = new Scanner(System.in);
		boolean formatValid;
		char input;
		do {
			System.out.print("Enter your move [WASD?]: ");
			input = userInput.next().trim().charAt(0);
			input = Character.toLowerCase(input);
			formatValid = isFormatValid(input);

		} while (!formatValid);

		return input;
	}

	// checks if the input is in the correct format
	private boolean isFormatValid(char input) {
		boolean inputValid = false;
		for (char ch : USER_KEYS) {
			if (input == ch) {
				inputValid = true;
				break;
			}
		}

		if (!inputValid) {
			System.out.println("Invalid move. " +
					"Please enter just A (left), S (down), D (right), or W (up).");
		}

		return inputValid;
	}

	private void displayWelcomeMessage(){
		System.out.println("----------------------------------------");
		System.out.println( "Welcome to the Maze!");
		System.out.println("----------------------------------------");
	}


	private void displayLoss() {
		System.out.println("I'm sorry, you've lost!");
		System.out.println("GAME OVER; please try again.");
	}

	private void displayWin() {
		System.out.println("Congratulations! You won!");
	}



	private void printMaze() {
		System.out.println();
		System.out.println("Maze: ");
		for (int i = 0; i < game.getMyMaze().getROWS(); i++) {
			for (int j = 0; j < game.getMyMaze().getCOLS(); j++) {
				Cell cell = game.getMyMaze().getMaze()[i][j];

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
		System.out.println("Player Score: " + game.getPlayerScore());
	}
}
