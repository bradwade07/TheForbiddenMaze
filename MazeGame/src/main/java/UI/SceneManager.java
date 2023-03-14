package UI;

import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
public class SceneManager {
	private Stage stage;
	private Scene scene;
	private Parent root;
	private GraphicsContext graphicsContext;

	public SceneManager() {

	}

	public void Switch_to_Menu(ActionEvent event) {
		try {

			Rectangle2D screen = Screen.getPrimary().getBounds();

			root = FXMLLoader.load(getClass().getResource("/main_menu.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root,  screen.getMaxY(), screen.getMaxX());
			stage.setScene(scene);
			stage.setFullScreen(true);
			stage.show();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	public void Switch_to_Game(ActionEvent event) {
		try {

			Rectangle2D screen = Screen.getPrimary().getBounds();

			Canvas canvas = new Canvas(screen.getMaxY(), screen.getMaxX());
			graphicsContext = canvas.getGraphicsContext2D();

			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(new StackPane(canvas), screen.getMaxY(), screen.getMaxX());
			stage.setScene(scene);
			stage.setFullScreen(true);
			stage.show();
		} catch(Exception e) {
			System.out.println(e);
		}


	}
}