package game;

import ui.UICreator;
import ui.UIScreen;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MonopolyGame game = new MonopolyGame();
		Controller.getInstance().initialize(game);
		UIScreen screen = new UIScreen();
		Controller.getInstance().addGamelistener(screen);
		UICreator gameCreator = new UICreator();
		Controller.getInstance().addGamelistener(gameCreator);
		gameCreator.setVisible(true);
	}

}