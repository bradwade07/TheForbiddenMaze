package UI;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
public class UI {
	private Stage stage;
	private Scene scene;
	private Parent root;

	public void Switch_to_Menu(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/main_menu.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root, 500, 500);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	public void Switch_to_Game(ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/game.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root, 500, 500);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
