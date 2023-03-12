package State;

/**
 * GameStart State class that handles the game start
 */
public class GameStart extends State{

    public GameStart() {
        super();
        setNexState(NextState.gameStart);
    }

    @Override
    public void renderState() {

    }
}

