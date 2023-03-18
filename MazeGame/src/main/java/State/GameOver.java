package State;

/**
 * GameOver State class that handles game over scenario
 */
public class GameOver extends State{

    public GameOver() {
        super();
        setNexState(GameState.gameOver);
    }
}

