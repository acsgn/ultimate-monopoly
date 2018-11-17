package game;

import ui.UICreator;
import ui.UILinker;
import ui.UIScreen;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MonopolyGame game = new MonopolyGame();
		Controller gameController = new Controller(game);
		UIScreen screen = new UIScreen(gameController);
		game.getPlayers().get(0).addGamelistener(screen);
		UICreator gameCreator = new UICreator(gameController);
		UILinker.getInstance().initalize(gameCreator, screen);
		gameCreator.setVisible(true);
	}

}