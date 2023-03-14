package State;

import Map.Maze;

/**
 * Master State class that handles the current state of gameplay
 */

public abstract class State {


//    protected UI ui;

    public GameState nexState;

    public State() {

    }

    public void renderState() {

    }

    public void handleInput() {

    }


    public void setNexState(GameState nexState) {
        this.nexState = nexState;
    }
}
