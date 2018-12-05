package game;

import java.io.File;
import java.util.ArrayList;

public class Controller {
	
	private static Controller self;
	private ArrayList<GameListener> listeners;
	
	private MonopolyGame monopolyGame;

	private Controller() {
		listeners = new ArrayList<GameListener>();
	}

	public void dispatchMessage(String message) {
		monopolyGame.executeMessage(message);
	}

	public void initialize(MonopolyGame monopolyGame) {
		this.monopolyGame = monopolyGame;
	}	

	public void addGamelistener(GameListener lis) {
		listeners.add(lis);
	}

	public void publishGameEvent(String message) {
		for (GameListener l : listeners) {
			l.onGameEvent(message);
		}
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
