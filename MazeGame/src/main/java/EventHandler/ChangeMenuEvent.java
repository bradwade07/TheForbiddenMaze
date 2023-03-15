package EventHandler;

import UI.GameRenderer;
import UI.SceneManager;
import javafx.event.ActionEvent;

public interface ChangeMenuEvent {
    void onMenuChange(GameRenderer gameRenderer);
}
