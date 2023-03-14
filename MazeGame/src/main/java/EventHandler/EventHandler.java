package EventHandler;

import UI.GameRenderer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    public List<ChangeMenuEvent> menuChangeListeners;
    public List<ChangeGameEvent> gameChangeListeners;
    public List<KeyEvents> keyEventsListeners;

    public GameRenderer gameRenderer;

    public EventHandler(Stage stage) {
        this.menuChangeListeners = new ArrayList<>();
        this.gameChangeListeners = new ArrayList<>();
        this.keyEventsListeners = new ArrayList<>();
        gameRenderer = new GameRenderer(stage, newScene());
    }

    public void startGameButton() {
        gameRenderer.Render_Terrain(newScene());
        for (ChangeGameEvent listener: gameChangeListeners) {
            listener.onGameChange(gameRenderer);
        }
    }

    public void startMenuButton() {
        for (ChangeMenuEvent listener: menuChangeListeners) {
            listener.onMenuChange(gameRenderer);
        }
    }

    public void keyPressed(char keycode) {
        for (KeyEvents listener: keyEventsListeners) {
            listener.onKeyPress(keycode);
        }
    }

    public Scene newScene() {
        Rectangle2D screen = Screen.getPrimary().getBounds();

        double screenWidth = screen.getMaxY();
        double screenHeight = screen.getMaxX();
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        Scene newScene = new Scene(new StackPane(canvas), screenWidth, screenHeight);
        newScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode() == KeyCode.ENTER) {
                startGameButton();
                System.out.println("Enter Pressed");
            }

            switch (key.getCode()) {
                case KeyCode.W:
                    keyPressed('w');
                    break;
                case KeyCode.A:
                    keyPressed('a');
                    break;
                case KeyCode.S:
                    keyPressed('s');
                    break;
                case KeyCode.D:
                    keyPressed('d');
                    break;
                default:
                    // code block
            }
        });

        return newScene;
    }

    // Key press
    // Scene Change

    public void addGameListener(ChangeGameEvent gameEvent) {
        gameChangeListeners.add(gameEvent);
    }

    public void addMenuListener(ChangeMenuEvent menuEvent) {
        menuChangeListeners.add(menuEvent);
    }

    public void addKeyListener(KeyEvents keyEvent) {keyEventsListeners.add(keyEvent); }



}
