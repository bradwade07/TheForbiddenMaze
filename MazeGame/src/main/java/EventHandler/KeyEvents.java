package EventHandler;

import State.Game;

public interface KeyEvents {
    // This is based on Ascii Keycode
    void onKeyPress(char keycode, Game myGame);
}

