package UI;

import Entities.Enemy;
import Entities.Player;
import Entities.Reward;
import Entities.Trap;
import Map.Cell;
import Map.Maze;
import Map.Point;
import State.Game;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;

public class UI {
	private Stage stage;
	private Scene scene;
	private Group root;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private double screenWidth;
	private double screenHeight;
	private double cellWidth;

	// IMAGES
	private Image background;
	private Image howToPlayScreen;
	private Image gameOverScreen;
	private Image title;
	private Image playGame;
	private Image howToPlay;
	private Image edge;
	private Image floor;
	private Image barrier;
	private Image portal;
	private Image player;
	private Image enemy;
	private Image reward;
	private Image trap;

	// These are what we multiply cell width to get the location where to render score
	private const double scoreXMultiplier = 15;
	private const double scoreYMultiplier = 0.5;

	// These are what we multiply screen height and width by to get size of a cell that makes up the maze
	private const double screenWidthMultiplier = 1/32;
	private const double screenHeightMultiplier = 1/18;

	// These are what we multiply  width by to get where the howToPlay and playGame buttons are rendered
	private const double buttonLocationMultiplierX = 3/7;

	/**
	 * Constructs UI. The stage MUST be the primary stage for the game.
	 * @param stage_p
	 * @param scene_p
	 * @param group_p
	 * @param canvas_p
	 */
	public UI(Stage stage_p, Scene scene_p, Group group_p, Canvas canvas_p) {
		stage = stage_p;
		scene = scene_p;
		root = group_p;
		canvas = canvas_p;

		graphicsContext = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);

		Rectangle2D screen = Screen.getPrimary().getBounds();

		screenWidth = screen.getMaxX();
		screenHeight = screen.getMaxY();
		cellWidth = screenWidth * screenWidthMultiplier;

		background = new Image("/Castle.jpg", screenWidth, screenHeight, false, false);
		title = new Image("/Title.png", screenWidth / 3, screenHeight / 8, false, false);
		playGame = new Image("/startGame.png", screenWidth / 7, screenHeight / 8, false, false);
		howToPlay = new Image("/howToPlay.png", screenWidth / 7, screenHeight / 8, false, false);
		edge = new Image("/pit.png", screenWidth * screenWidthMultiplier, screenHeight * screenHeightMultiplier, false, false);
		floor = new Image("/floor.png", screenWidth * screenWidthMultiplier, screenHeight * screenHeightMultiplier, false, false);
		barrier = new Image("/barrier.png", screenWidth / 34, screenHeight * screenHeightMultiplier, false, false);
		portal = new Image("/portal.png", screenWidth * screenWidthMultiplier, screenHeight * screenHeightMultiplier, false, false);
		player = new Image("/adventurer-idle-00.png", screenWidth * screenWidthMultiplier, screenHeight * screenHeightMultiplier, false, false);
		enemy = new Image("/skeleton.png", screenWidth * screenWidthMultiplier, screenHeight * screenHeightMultiplier, false, false);
		reward = new Image("/crystal_01d.png", screenWidth * screenWidthMultiplier, screenHeight * screenHeightMultiplier, false, false);
		trap = new Image("/spike_4.png", screenWidth * screenWidthMultiplier, screenHeight * screenHeightMultiplier, false, false);
		howToPlayScreen = new Image("/HowToPlayScreen.jpg", screenWidth, screenHeight, false, false);
		gameOverScreen = new Image("/GameOver.jpg", screenWidth, screenHeight, false, false);

		RenderMenu();
	}

	/**
	 *  Clears Canvas. It is easier to clear the canvas than make a new scene (it also is more efficient) so this is called every time we redraw any game scene
	 */
	private void ClearCanvas() {
		graphicsContext.clearRect(0, 0, screenWidth, screenHeight);
	}

	/**
	 * Renders the Menu
	 */
	public void RenderMenu() {
		ClearCanvas();
		graphicsContext.drawImage(background, 0, 0);
		graphicsContext.drawImage(title, screenWidth / 3, screenHeight / 8);
		graphicsContext.drawImage(playGame, screenWidth * buttonLocationMultiplierX, screenHeight / 8 * 3);
		graphicsContext.drawImage(howToPlay, screenWidth * buttonLocationMultiplierX, screenHeight / 8 * 5);
	}

	/**
	 * Renders game.
	 * @param maze
	 * @param player
	 * @param enemyList
	 * @param rewardList
	 * @param trapList
	 * @param score
	 */
	public void RenderGame(Maze maze, Player player, List<Enemy> enemyList, List<Reward> rewardList, List<Trap> trapList, int score) {
		ClearCanvas();
		Cell matrix[][] = maze.getMaze();

		for (int i = 0; i < maze.getHeight(); i++){
			 for (int j = 0; j < maze.getWidth(); j++) {
				switch (matrix[i][j].getCellType()) {
					case path:
						graphicsContext.drawImage(floor, cellWidth * j, cellWidth * i);
						break;
					case wall:
						graphicsContext.drawImage(floor, cellWidth * j, cellWidth * i);
						graphicsContext.drawImage(barrier, cellWidth * j, cellWidth * i);
						break;
					case barricade:
						graphicsContext.drawImage(edge, cellWidth * j, cellWidth * i);
						break;
					case exit_cell:
						graphicsContext.drawImage(floor, cellWidth * j, cellWidth * i);
						graphicsContext.drawImage(portal, cellWidth * j, cellWidth * i);
						break;
				}
			}
		}

		RenderEntity(player, enemyList, rewardList, trapList);
		if(score < 0){
			graphicsContext.strokeText("Score:"  + 0, cellWidth * scoreXMultiplier, cellWidth * scoreYMultiplier);
		}
		else{
			graphicsContext.strokeText("Score:"  + score, cellWidth * scoreXMultiplier, cellWidth * scoreYMultiplier);
		}

	}

	/**
	 * Renders all entities in the game
	 * @param player_e
	 * @param enemies
	 * @param rewardList
	 * @param trapList
	 */
	public void RenderEntity(Player player_e, List<Enemy> enemies, List<Reward> rewardList, List<Trap> trapList) {
		Point temp = player_e.getLocation();
		graphicsContext.drawImage(player, temp.getWidth()*cellWidth, temp.getHeight()*cellWidth);

		for (Enemy enemy_e: enemies) {
			temp = enemy_e.getLocation();
			graphicsContext.drawImage(enemy, temp.getWidth()*cellWidth, temp.getHeight()*cellWidth);
		}

		for (Reward reward_e: rewardList) {
			temp = reward_e.getLocation();
			graphicsContext.drawImage(reward, temp.getWidth()*cellWidth, temp.getHeight()*cellWidth);
		}

		for (Trap trap_e: trapList) {
			temp = trap_e.getLocation();
			graphicsContext.drawImage(trap, temp.getWidth()*cellWidth, temp.getHeight()*cellWidth);
		}
	}

	/**
	 * Renders the how to play scene
	 */
	public void RenderHowToPlay() {
		ClearCanvas();
		graphicsContext.drawImage(howToPlayScreen, 0, 0);
	}

	/**
	 * Renders the Game over Scene
	 */
	public void RenderGameOver() {
		ClearCanvas();
		graphicsContext.drawImage(gameOverScreen, 0, 0);
	}

}
