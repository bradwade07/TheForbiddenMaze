package Group2;

import EventHandler.EventHandler;
import TextTesting.Display;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

public class App extends Application {
    private static String title = "Forbidden Maze";
    private EventHandler eventHandler;
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Rectangle2D screen = Screen.getPrimary().getBounds();

            double  screenWidth = screen.getMaxX();
            double screenHeight = screen.getMaxY();

            Group root = new Group();
            Scene scene = new Scene(root, screenWidth, screenHeight);
            Canvas canvas = new Canvas(screenWidth, screenHeight);

            stage.setTitle(title);
            stage.setFullScreen(true);
            stage.setScene(scene);
            stage.show();

            eventHandler = new EventHandler(stage, root, scene, canvas);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}