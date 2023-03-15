package UI;

import State.Game;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameRenderer {

	private Stage stage;
	private Scene scene;
	private Parent root;
	private Image edge;
	private GraphicsContext graphicsContext;

	private double screenWidth;
	private double screenHeight;
	private Game myGame;
	public GameRenderer(Stage stage_p, Scene scene_p) {
		Rectangle2D screen = Screen.getPrimary().getBounds();

		screenWidth = screen.getMaxY();
		screenHeight = screen.getMaxX();

		stage = stage_p;
		scene = scene_p;

		edge = new Image("/pit.png", 60, 60, false, false);

		RenderMenu();
		myGame = new Game();
		myGame.start(1,1,1);
	}

	public void RenderMenu() {
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		graphicsContext = canvas.getGraphicsContext2D();
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	public void Render_Terrain(Scene scene_p) {

		Rectangle2D screen = Screen.getPrimary().getBounds();
		System.out.println("Everlong");
		Canvas canvas = new Canvas(screen.getMaxY(), screen.getMaxX());
		graphicsContext = canvas.getGraphicsContext2D();
		scene = new Scene(new StackPane(canvas), screen.getMaxY(), screen.getMaxX());
		stage.setScene(scene);
		stage.setFullScreen(true);

		double cellWidth =  screen.getMaxX() / 18;
		// Set top and bottom edge
		for (int i = 0; i < 32; i++) {
			// Top Edge
			graphicsContext.drawImage(edge, i * cellWidth, 0);

			// Bottom Edge
			graphicsContext.drawImage(edge, i * cellWidth, cellWidth * 8);
		}

		// Set left and right edges
		// Start at 1 and end at 15 to avoid redrawing corners
		for (int i = 1; i < 15; i++) {
			graphicsContext.drawImage(edge, 0 ,cellWidth * i);
			graphicsContext.drawImage(edge, cellWidth * 15 ,cellWidth * i);
		}

		stage.show();
	}

	public void execution() {
		//listener;
		myGame.enemyMovement();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);//TODO check this exception
		}
	}

}