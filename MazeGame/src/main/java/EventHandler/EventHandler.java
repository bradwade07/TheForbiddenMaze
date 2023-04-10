package EventHandler;

import State.Game;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static java.util.concurrent.TimeUnit.*;

import java.util.*;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import UI.UI;

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

    private UI ui;
    private Game game;

    /**
     * EventHandler Contructor
     * @param stage_p
     * @param root_p
     * @param scene_p
     * @param canvas_p
     */
    public EventHandler(Stage stage_p, Group root_p, Scene scene_p, Canvas canvas_p) {

        Rectangle2D screen = Screen.getPrimary().getBounds();

        screenWidth = screen.getMaxX();
        screenHeight = screen.getMaxY();

        stage = stage_p;
        root = root_p;
        scene = scene_p;
        canvas = canvas_p;

        game = new Game();


        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode() == KeyCode.W) {
                game.movePlayer('w');
            } else if (key.getCode() == KeyCode.A) {
                game.movePlayer('a');
            } else if (key.getCode() == KeyCode.S) {
                game.movePlayer('s');
            } else if (key.getCode() == KeyCode.D) {
                game.movePlayer('d');
            } else if (key.getCode() == KeyCode.E && game.getGameState().equals(Game.GameState.howToPlay)) {
                game.setGameStateToStart();
                ui.RenderMenu();
            }

            try {
                MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouse) -> {
            if (game.getGameState().equals(Game.GameState.gameStart) && mouse.getScreenX() >= screenWidth* 3/7 && mouse.getScreenX() <= screenWidth* 4/7) {
                if(mouse.getScreenY() >= screenHeight* 3/8 && mouse.getScreenY() <= screenHeight* 4/8){
                    game.setGameToRun();
                    game.generateMap(2,1, 3);
                    ui.RenderGame(game.getMyMaze(),game.getPlayer(),game.getEnemyList(),game.getRewardList(),game.getTrapList(),game.getPlayerScore());
                    gameLoop();
                }
                else if (mouse.getScreenY() >= screenHeight* 5/8 && mouse.getScreenY() <= screenHeight* 6/8) {
                    game.setGameStateToHowToPlay();
                    ui.RenderHowToPlay();
                }
            } else if (game.getGameState().equals(Game.GameState.LOST) && mouse.getScreenX() >= screenWidth / 7 * 3 && mouse.getScreenX() <= screenWidth / 7 * 4) {
                if (mouse.getScreenY() >= screenHeight / 9 * 5 && mouse.getScreenY() <= screenHeight / 9 * 5 + screenHeight / 10) {
                    game.reset(2, 1, 3, true);
                    game.setGameToRun();
                    ui.RenderGame(game.getMyMaze(),game.getPlayer(),game.getEnemyList(),game.getRewardList(),game.getTrapList(),game.getPlayerScore());
                    gameLoop();
                } else if (mouse.getScreenY() >= screenHeight / 9 * 6 && mouse.getScreenY() <= screenHeight / 9 * 6 + screenHeight / 10) {
                    game.setGameStateToStart();
                    game.reset(2, 1, 3, true);
                    ui.RenderMenu();
                }
            }else if (game.getGameState().equals(Game.GameState.WIN) && mouse.getScreenX() >= screenWidth / 7 * 3 && mouse.getScreenX() <= screenWidth / 7 * 4) {
                if (mouse.getScreenY() >= screenHeight / 9 * 5 && mouse.getScreenY() <= screenHeight / 9 * 5 + screenHeight / 10) {
                    game.reset(game.getInitialEnemyListSize() + 1, game.getInitialRewardListSize() + 1, game.getInitialTrapListSize() + 1, true);
                    game.setGameToRun();
                    ui.RenderGame(game.getMyMaze(),game.getPlayer(),game.getEnemyList(),game.getRewardList(),game.getTrapList(),game.getPlayerScore());
                    gameLoop();
                } else if (mouse.getScreenY() >= screenHeight / 9 * 6 && mouse.getScreenY() <= screenHeight / 9 * 6 + screenHeight / 10) {
                    game.setGameStateToStart();
                    game.reset(2, 1, 3, false);
                    ui.RenderMenu();
                }
            }
        });

        ui = new UI(stage_p, scene_p, root_p, canvas_p);
    }

    /**
     * Starts the Game Loop. Every Loop it calls game tick and updates game UI[
     */
    public void gameLoop() {

        Timer t = new Timer();

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                game.runOneTick();
                ui.RenderGame(game.getMyMaze(),game.getPlayer(),game.getEnemyList(),game.getRewardList(),game.getTrapList(),game.getPlayerScore());
                if (game.isGameLost()) {
                    ui.RenderGameOver();
                    t.cancel();
                }
                else if(game.isGameWon()){
                    ui.RenderGameWon();
                    t.cancel();
                }
            }


        };

        t.scheduleAtFixedRate(tt, new Date(), 200);

    }

}
