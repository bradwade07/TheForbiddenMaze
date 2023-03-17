package UI;

import Map.Cell;
import Map.Maze;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
	private Image title;
	private Image playGame;
	private Image howToPlay;
	private Image edge;
	private Image floor;
	private Image barrier;
	private Image portal;

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
		barrier = new Image("barrier.png", screenWidth / 34, screenHeight / 18, false, false);
		portal = new Image("/portal.png", screenWidth / 32, screenHeight / 18, false, false);
	}

	private void ClearCanvas() {
		graphicsContext.clearRect(0, 0, screenWidth, screenHeight);
	}

	public void RenderMenu() {
		ClearCanvas();
		graphicsContext.drawImage(background, 0, 0);
		graphicsContext.drawImage(title, screenWidth / 3, screenHeight / 8);
		graphicsContext.drawImage(playGame, screenWidth / 7 * 2, screenHeight / 8 * 2);
		graphicsContext.drawImage(howToPlay, screenWidth / 7 * 2, screenHeight / 8 * 3);
	}

	public void RenderGame(Maze maze) {
		ClearCanvas();
		Cell matrix[][] = maze.getMaze();

		for (int i = 0; i < 32; i ++) {
			for (int j = 0; j < 18; j++) {
				switch (matrix[i][j].getCellType()) {
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
	}





}
