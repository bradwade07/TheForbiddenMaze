package TextTesting;

import State.Game;

public class Main {
	public static void main(String[] args) {
		Display myDisplay = new Display();
		Game myGame = new Game();
		myGame.start(1,1,1);
		Display.print(myGame);
		//System.out.println(myGame.getMyMaze().getMaze()[30][16].getCellType());
//		ReturnType returnType = null;
//		while((myMazeGame.getCheeseToCollect() > myMazeGame.getCheeseCollected())){
//			boolean breakLoop = true;
//			Display.print(myMazeGame.getMyMaze());
//			System.out.println("Cheese collected: " + myMazeGame.getCheeseCollected() + " of " + myMazeGame.getCheeseToCollect());
//			while(breakLoop){
//				System.out.print("Enter your move [WASD?]:");
//				Scanner sChoice = new Scanner(System.in);
//				char choice = sChoice.next().charAt(0);
//				if(choice == 'W' || choice == 'w' || choice == 'A' || choice == 'a' || choice == 'S' || choice == 's' || choice == 'D' || choice == 'd' || choice == 'M' || choice == 'm'|| choice == 'C' || choice == 'c'){
//					returnType = myMazeGame.mouseMovementLogic(choice);
//					breakLoop = false;
//				}
//				else{
//					System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
//				}
//
//				if(returnType == ReturnType.eaten){
//					System.out.println("I'm sorry, you have been eaten!");
//					myMazeGame.exploredLogic();
//					Display.print(myMazeGame.getMyMaze());
//
//					System.out.println("GAME OVER; please try again.");
//					System.out.println("Cheese collected: " + myMazeGame.getCheeseCollected() + " of " + myMazeGame.getCheeseToCollect());
//					return;
//				}
//				else if(returnType == ReturnType.block){
//					System.out.println("Invalid move: you cannot move through walls!");
//				}
//			}
//			myMazeGame.moveCats();
//		}
//		System.out.println("Congratulations! You won!");
//		myMazeGame.exploredLogic();
//		Display.print(myMazeGame.getMyMaze());
//		System.out.println("Cheese collected: " + myMazeGame.getCheeseCollected() + " of " + myMazeGame.getCheeseToCollect());
	}
}
