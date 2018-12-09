package game;

import ui.UICreator;
import ui.UIScreen;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		UIScreen screen = new UIScreen();
		Controller.getInstance().addGamelistener(screen);
		UICreator gameCreator = new UICreator();
		Controller.getInstance().addGamelistener(gameCreator);
		MonopolyGame game = new MonopolyGame();
		Controller.getInstance().setGame(game);
		gameCreator.setVisible(true);
	}

}