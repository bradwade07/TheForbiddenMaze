package Group2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
           // FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/main_menu.fxml"));
           // Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
            //stage.setTitle("Hello!");
            //stage.setScene(scene);
            //stage.show();

            Parent root = FXMLLoader.load(getClass().getResource("/main_menu.fxml"));
            Scene scene = new Scene(root, 1920, 1080);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}