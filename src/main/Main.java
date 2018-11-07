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
		UICreator gameCreator = new UICreator(domainController);
		gameCreator.setVisible(true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Autapo-generated catch block
			e.printStackTrace();
		}
		gameCreator.dispose();
		UIScreen screen = new UIScreen(domainController);
		screen.setVisible(true);
		UIController uiController = new UIController(screen);
		game.setObserver(uiController);
	}

}