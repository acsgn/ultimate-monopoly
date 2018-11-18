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
		Controller.getInstance().initialize(game);
		UIScreen screen = new UIScreen();
		game.addGamelistener(screen);
		UICreator gameCreator = new UICreator();
		UILinker.getInstance().initalize(gameCreator, screen);
		new Thread(game,"Game").start();
		gameCreator.setVisible(true);
	}

}