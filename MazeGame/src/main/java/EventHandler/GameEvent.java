package EventHandler;

import UI.GameRenderer;
import UI.SceneManager;
import javafx.event.ActionEvent;

public class GameEvent implements  ChangeGameEvent {
    public void onGameChange(GameRenderer gameRenderer) {
        System.out.println("Bedwoman?!?!?");
    }
}
