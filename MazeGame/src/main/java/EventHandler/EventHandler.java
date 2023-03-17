package EventHandler;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static java.util.concurrent.TimeUnit.*;

import java.util.*;
import java.util.TimerTask;

public class EventHandler {
    public List<ChangeMenuEvent> menuChangeListeners;
    public List<ChangeGameEvent> gameChangeListeners;
    public List<KeyEvents> keyEventsListeners;

    private Stage stage;
    private Scene scene;
    private Group root;
    private Image edge;
    private GraphicsContext graphicsContext;
    private Canvas canvas;

    private double screenWidth;
    private double screenHeight;

    public EventHandler(Stage stage_p, Group root_p, Scene scene_p, Canvas canvas_p) {
        this.menuChangeListeners = new ArrayList<>();
        this.gameChangeListeners = new ArrayList<>();
        this.keyEventsListeners = new ArrayList<>();

        Rectangle2D screen = Screen.getPrimary().getBounds();

        screenWidth = screen.getMaxX();
        screenHeight = screen.getMaxY();

        stage = stage_p;
        root = root_p;
        scene = scene_p;
        canvas = canvas_p;


        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode() == KeyCode.W) {
                keyPressed('w');
            } else if (key.getCode() == KeyCode.A) {
                keyPressed('a');
            } else if (key.getCode() == KeyCode.S) {
                keyPressed('s');
            } else if (key.getCode() == KeyCode.D) {
                keyPressed('d');
            }

        });

        gameLoop();
    }

    public void gameLoop() {

        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Task Timer on Fixed Rate");
            }


        };

        t.scheduleAtFixedRate(tt, new Date(), 1000);

    }

    public void startGameButton() {

        for (ChangeGameEvent listener: gameChangeListeners) {

        }
    }

    public void startMenuButton() {
        for (ChangeMenuEvent listener: menuChangeListeners) {

        }
    }

    public void keyPressed(char keycode) {
        for (KeyEvents listener: keyEventsListeners) {
            listener.onKeyPress(keycode);
        }
    }

    public void addGameListener(ChangeGameEvent gameEvent) {
        gameChangeListeners.add(gameEvent);
    }

    public void addMenuListener(ChangeMenuEvent menuEvent) {
        menuChangeListeners.add(menuEvent);
    }

    public void addKeyListener(KeyEvents keyEvent) {keyEventsListeners.add(keyEvent); }

}
