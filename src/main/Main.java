package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import game.FrontController;
import game.MonopolyGame;
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
		gameCreator.setVisible(true);
		UIScreen screen = new UIScreen(domainController);
		gameCreator.addWindowStateListener(new WindowStateListener() {	
			@Override
			public void windowStateChanged(WindowEvent e) {
				if (gameCreator.isVisible()) screen.setVisible(true);
			}
		});
	}

}