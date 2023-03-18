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
		cellWidth = screenWidth / 32;

		background = new Image("/Castle.jpg", screenWidth, screenHeight, false, false);
		title = new Image("/Title.png", screenWidth / 3, screenHeight / 8, false, false);
		playGame = new Image("/startGame.png", screenWidth / 7, screenHeight / 8, false, false);
		howToPlay = new Image("/howToPlay.png", screenWidth / 7, screenHeight / 8, false, false);
		edge = new Image("/pit.png", screenWidth / 32, screenHeight / 18, false, false);
		floor = new Image("/floor.png", screenWidth / 32, screenHeight / 18, false, false);
		barrier = new Image("/barrier.png", screenWidth / 34, screenHeight / 18, false, false);
		portal = new Image("/portal.png", screenWidth / 32, screenHeight / 18, false, false);
		player = new Image("/adventurer-idle-00.png", screenWidth / 32 / 2, screenHeight / 18, false, false);
		enemy = new Image("/skeleton.png", screenWidth / 32, screenHeight / 18, false, false);
		reward = new Image("/crystal_01d.png", screenWidth / 32, screenHeight / 18, false, false);
		trap = new Image("/spike_4.png", screenWidth / 32, screenHeight / 18, false, false);
		howToPlayScreen = new Image("/HowToPlayScreen.jpg", screenWidth, screenHeight, false, false);
		gameOverScreen = new Image("/GameOver.jpg", screenWidth, screenHeight, false, false);

		RenderMenu();
		Game newGame = new Game();
	}

	private void ClearCanvas() {
		graphicsContext.clearRect(0, 0, screenWidth, screenHeight);
	}

	public void RenderMenu() {
		ClearCanvas();
		graphicsContext.drawImage(background, 0, 0);
		graphicsContext.drawImage(title, screenWidth / 3, screenHeight / 8);
		graphicsContext.drawImage(playGame, screenWidth / 7 * 3, screenHeight / 8 * 3);
		graphicsContext.drawImage(howToPlay, screenWidth / 7 * 3, screenHeight / 8 * 5);
	}

	public void RenderGame(Maze maze, Player player, List<Enemy> enemyList, List<Reward> rewardList, List<Trap> trapList, int score) {
		ClearCanvas();
		Cell matrix[][] = maze.getMaze();

		for (int i = 0; i < 32; i ++) {
			for (int j = 0; j < 18; j++) {
				switch (matrix[j][i].getCellType()) {
					case path:
						graphicsContext.drawImage(floor, cellWidth * i, cellWidth * j);
						break;
					case wall:
						graphicsContext.drawImage(floor, cellWidth * i, cellWidth * j);
						graphicsContext.drawImage(barrier, cellWidth * i, cellWidth * j);
						break;
					case barricade:
						graphicsContext.drawImage(edge, cellWidth * i, cellWidth * j);
						break;
					case exit_cell:
						graphicsContext.drawImage(floor, cellWidth * i, cellWidth * j);
						graphicsContext.drawImage(portal, cellWidth * i, cellWidth * j);
						break;
				}
			}
		}

		RenderEntity(player, enemyList, rewardList, trapList);
		graphicsContext.strokeText("Score:"  + score, cellWidth * 15, cellWidth /2);
	}

	public void RenderEntity(Player player_e, List<Enemy> enemies, List<Reward> rewardList, List<Trap> trapList) {
		Point temp = player_e.getLocation();
		graphicsContext.drawImage(player, temp.getY()*cellWidth, temp.getX()*cellWidth);

		for (Enemy enemy_e: enemies) {
			temp = enemy_e.getLocation();
			graphicsContext.drawImage(enemy, temp.getY()*cellWidth, temp.getX()*cellWidth);
		}

		for (Reward reward_e: rewardList) {
			temp = reward_e.getLocation();
			graphicsContext.drawImage(reward, temp.getY()*cellWidth, temp.getX()*cellWidth);
		}

		for (Trap trap_e: trapList) {
			temp = trap_e.getLocation();
			graphicsContext.drawImage(trap, temp.getY()*cellWidth, temp.getX()*cellWidth);
		}
	}

	public void RenderHowToPlay() {
		ClearCanvas();
		graphicsContext.drawImage(howToPlayScreen, 0, 0);
	}

	public void RenderGameOver() {
		ClearCanvas();
		graphicsContext.drawImage(gameOverScreen, 0, 0);
	}

}
