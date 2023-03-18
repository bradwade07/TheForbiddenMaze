package State;
/**
 * HowToPlay State class that shows the screen on how to play the game
 */
public class HowToPlay extends State{
	public HowToPlay() {
		super();
		setNexState(GameState.howToPlay);
	}

}
