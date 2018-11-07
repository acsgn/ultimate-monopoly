package main;

import game.FrontController;
import game.MonopolyGame;
import ui.UIController;
import ui.UICreator;
import ui.UIScreen;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MonopolyGame game = new MonopolyGame();
		FrontController domainController = new FrontController(game);
		UICreator gameCreator = new UICreator();
		//gameCreator.setVisible(true);
		UIScreen screen = new UIScreen(domainController);
		screen.setVisible(true);
		UIController uiController = new UIController(screen);
		game.setObserver(uiController);
	}

}