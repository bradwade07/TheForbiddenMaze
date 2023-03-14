package EventHandler;

import UI.GameRenderer;
import UI.SceneManager;
import javafx.event.ActionEvent;

public interface ChangeGameEvent {
    public void onGameChange(GameRenderer gameRenderer);
}

