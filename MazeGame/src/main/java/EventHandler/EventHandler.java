package EventHandler;

import javafx.geometry.Rectangle2D;
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

import java.util.ArrayList;
import java.util.List;
import State.Game;

public class EventHandler {
    public List<ChangeMenuEvent> menuChangeListeners;
    public List<ChangeGameEvent> gameChangeListeners;
    public List<KeyEvents> keyEventsListeners;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Image edge;
    private GraphicsContext graphicsContext;
    private Canvas canvas;

    private double screenWidth;
    private double screenHeight;
    private final Game myGame;

    public EventHandler(Stage stage_p) {
        this.menuChangeListeners = new ArrayList<>();
        this.gameChangeListeners = new ArrayList<>();
        this.keyEventsListeners = new ArrayList<>();
        myGame = new Game();

        Rectangle2D screen = Screen.getPrimary().getBounds();

        screenWidth = screen.getMaxX();
        screenHeight = screen.getMaxY();

        stage = stage_p;

        edge = new Image("/pit.png", 120, 120, false, false);

        RenderMenu();
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
            listener.onKeyPress(keycode, myGame);
        }
    }

    // Sets new Canvas and new scene up for a different scene
    // Also prepares listener for input
    public void newScene() {

        canvas = new Canvas(screenWidth, screenHeight);
        scene = new Scene(new StackPane(canvas), screenWidth, screenHeight);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            Render_Game();
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

    // Graphics Methods
    public void RenderMenu() {
        newScene();
        graphicsContext = canvas.getGraphicsContext2D();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public void Render_Game() {
        newScene();
        graphicsContext = canvas.getGraphicsContext2D();
        stage.setScene(scene);
        stage.setFullScreen(true);
        Render_Terrain();
        stage.show();
    }
    public void Render_Terrain() {

        double cellWidth = screenHeight / 18;

        // Set top and bottom edge
        for (int i = 0; i < 32; i++) {
            // Top Edge
            graphicsContext.drawImage(edge, i * cellWidth, 0);

            // Bottom Edge
            // graphicsContext.drawImage(edge, i * cellWidth, cellWidth * 17);
            //}

            // Set left and right edges
            // Start at 1 and end at 15 to avoid redrawing corners
            //for (int i = 1; i < 15; i++) {
            //graphicsContext.drawImage(edge, 0 ,cellWidth * i);
            //  graphicsContext.drawImage(edge, cellWidth * 15 ,cellWidth * i);
            //}

        }
    }

    public void gameInstance(){
        myGame.generateMap(1,1,1); //TODO based on levels
    }
}
