package game;

public class Controller {
	
	private static Controller self;
	
	private MonopolyGame monopolyGame;

	private Controller() {
	}

	public void dispatchMessage(String message) {
		monopolyGame.executeMessage(message);
	}

	public void initialize(MonopolyGame monopolyGame) {
		this.monopolyGame = monopolyGame;
	}

	public MonopolyGame getMonopolyGame() {
		return monopolyGame;
	}

	public static synchronized Controller getInstance() {
		if (self == null)
			self = new Controller();
		return self;
	}

	// Who should control the game (the controller or MonopolyGame).
	// I mean who sould call the player funcitons to ececute based on the message
	// received from UI.
}
