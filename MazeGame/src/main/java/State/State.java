package State;

/**
 * Master State class that handles the current state of gameplay
 */

public abstract class State {


//    protected UI ui;

    public GameState nextState;

    public State() {
    }

    public void renderState() {

    }

    public void handleInput() {

    }


    public void setNexState(GameState state) {
        this.nextState = state;
    }
    public GameState getNextState(){
        return this.nextState;
    }

}
