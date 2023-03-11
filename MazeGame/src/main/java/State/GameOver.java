package State;

/**
 * GameOver State class that handles game over scenario
 */
public class GameOver extends State{
    public GameOver() {
        setNexState(NextState.gameOver);
    }

    @Override
    public void renderState() {

    }

    public void changeNextState(){

    }
}
