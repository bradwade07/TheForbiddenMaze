package State;

/**
 * Master State class that handles the current state of gameplay
 */

public abstract class State {


//    protected UI ui;

    public MenuState nextState;

    public State() {
    }

    public void renderState() {

    }

    public void handleInput() {

    }


    public void setNexState(MenuState state) {
        this.nextState = state;
    }
    public MenuState getNextState(){
        return this.nextState;
    }

}
